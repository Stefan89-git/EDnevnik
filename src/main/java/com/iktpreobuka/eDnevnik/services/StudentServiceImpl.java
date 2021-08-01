package com.iktpreobuka.eDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.ClassEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDTO;
import com.iktpreobuka.eDnevnik.repositories.ClassRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentTeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.utils.Encryption;
import com.iktpreobuka.eDnevnik.validation.Validation;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentTeacherSubjectRepository stsRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private ParentRepository parentRepo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Override
	public StudentEntity createStudent(StudentDTO newStudent, Integer parentId, Integer classId) {
		if(!parentRepo.existsById(parentId)) {
			return null;
		}
		ParentEntity parent = parentRepo.findById(parentId).get();
		
		if(!classRepo.existsById(classId)) {
			return null;
		}
		ClassEntity classroom = classRepo.findById(classId).get();
		
		StudentEntity student = new StudentEntity();
		if(newStudent.getPassword().equals(newStudent.getRepetedPass())) {
			student.setName(newStudent.getName());
			student.setLastName(newStudent.getLastName());
			student.setUsername(newStudent.getUsername());
			student.setPassword(Encryption.getPassEncoded(newStudent.getPassword()));
			student.setActive(true);
			student.setRole(roleRepo.findByName("ROLE_STUDENT"));
			student.setParent(parent);
			student.setAttendingClass(classroom);
			return studentRepo.save(student);
		}
		return null;
	}

	@Override
	public StudentEntity changeStudent(Integer studentId, StudentDTO newStudent) {
		if(!studentRepo.existsById(studentId)) {
			return null;
		}
		StudentEntity student = studentRepo.findById(studentId).get();
		if(newStudent.getPassword() == null){
			student.setName(Validation.setIfNotNull(student.getName(), newStudent.getName()));
			student.setLastName(Validation.setIfNotNull(student.getLastName(), newStudent.getLastName()));
			student.setUsername(Validation.setIfNotNull(student.getUsername(), newStudent.getUsername()));
			return studentRepo.save(student);
		}else if(newStudent.getPassword().equals(newStudent.getRepetedPass())) {
			student.setName(Validation.setIfNotNull(student.getName(), newStudent.getName()));
			student.setLastName(Validation.setIfNotNull(student.getLastName(), newStudent.getLastName()));
			student.setUsername(Validation.setIfNotNull(student.getUsername(), newStudent.getUsername()));
			student.setPassword(Encryption.getPassEncoded(newStudent.getPassword()));
			return studentRepo.save(student);
		}
		return null;
	}
	
	@Override
	public StudentEntity changeClassroomForStudent(Integer studentId, Integer classId) {
		if(!studentRepo.existsById(studentId)) {
			return null;
		}
		StudentEntity student = studentRepo.findById(studentId).get();
		
		if(!classRepo.existsById(classId)) {
			return null;
		}
		ClassEntity classroom = classRepo.findById(classId).get();
		
		student.setAttendingClass(classroom);
		return studentRepo.save(student);
	}

	@Override
	public StudentEntity deleteStudent(Integer studentId) {
		if(!studentRepo.existsById(studentId)) {
			return null;
		}
		StudentEntity student = studentRepo.findById(studentId).get();
		if(student.isActive()) {
			student.setActive(false);
			return studentRepo.save(student);
		}
		return null;
	}

	@Override
	public StudentTeacherSubjectEntity addTeacherAndSubjectToStudent(Integer studentId, Integer teacherId,
																	 Integer subjectId) {
		if(!studentRepo.existsById(studentId)) {
			return null;
		}
		StudentEntity student = studentRepo.findById(studentId).get();
		
		if(!teacherRepo.existsById(teacherId)) {
			return null;
		}
		TeacherEntity teacher = teacherRepo.findById(teacherId).get();
		
		if(!subjectRepo.existsById(subjectId)) {
			return null;
		}
		SubjectEntity subject = subjectRepo.findById(subjectId).get();
		
		if(!teacherSubjectRepo.existsByTeacherAndSubject(teacher, subject)) {
			return null;
		}
		TeacherSubjectEntity teacherSubject = teacherSubjectRepo.findByTeacherAndSubject(teacher, subject);
		StudentTeacherSubjectEntity sts = new StudentTeacherSubjectEntity();
		sts.setStudent(student);
		sts.setTeacherSubject(teacherSubject);
		return stsRepo.save(sts);
	}

	

}
