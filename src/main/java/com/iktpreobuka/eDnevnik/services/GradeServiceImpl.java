package com.iktpreobuka.eDnevnik.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentTeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.utils.EmailObject;

@Service
public class GradeServiceImpl implements GradeService{

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;
	
	@Autowired
	private StudentTeacherSubjectRepository stsRepo;
	
	@Autowired
	private GradeRepository gradeRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private ParentRepository parentRepo;
	
	@Override
	public GradeEntity crateGradeByAdmin(Integer studentId, Integer teacherSubjectId, GradeEntity newGrade) {
		if(!studentRepo.existsById(studentId)) {
			return null;
		}
		StudentEntity student = studentRepo.findById(studentId).get();
		
		if(!teacherSubjectRepo.existsById(teacherSubjectId)) {
			return null;
		}
		TeacherSubjectEntity teacherSubject = teacherSubjectRepo.findById(teacherSubjectId).get();
		
		StudentTeacherSubjectEntity sts = stsRepo.findByStudentAndTeacherSubject(student, teacherSubject);
		GradeEntity grade = new GradeEntity();
		grade.setActive(true);
		grade.setDate(LocalDate.now());
		grade.setStudentTeacherSubject(sts);
		grade.setValue(newGrade.getValue());
//		EmailObject email = new EmailObject();
//		email.setSubject("Grade given");
//		email.setText(String.format("Your kid %s %s has been given a grade %d from teacher %s %s for subject %s",
//				student.getName(), student.getLastName(), grade.getValue(),
//				teacherSubject.getTeacher().getName(), teacherSubject.getTeacher().getLastName(),
//				teacherSubject.getSubject().getName()));
//		email.setTo(student.getParent().getEmail());
//		emailService.sendSimpleMessage(email);
		return gradeRepo.save(grade);
	}

	@Override
	public GradeEntity createGradeByTeacher(Integer studentId, Integer subjectId, GradeEntity newGrade) {
		if(!studentRepo.existsById(studentId)) {
			return null;
		}
		StudentEntity student = studentRepo.findById(studentId).get();
		
		if(!subjectRepo.existsById(subjectId)) {
			return null;
		}
		SubjectEntity subject = subjectRepo.findById(subjectId).get();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String)auth.getPrincipal();
		TeacherEntity teacher = teacherRepo.findByUsername(username);
		
		if(!teacherSubjectRepo.existsByTeacherAndSubject(teacher, subject)) {
			return null;
		}
		TeacherSubjectEntity teacherSubject = teacherSubjectRepo.findByTeacherAndSubject(teacher, subject);
		
		StudentTeacherSubjectEntity sts = stsRepo.findByStudentAndTeacherSubject(student, teacherSubject);
		GradeEntity grade = new GradeEntity();
		grade.setActive(true);
		grade.setDate(LocalDate.now());
		grade.setStudentTeacherSubject(sts);
		grade.setValue(newGrade.getValue());
//		EmailObject email = new EmailObject();
//		email.setSubject("Grade given");
//		email.setText(String.format("Your kid %s %s has been given a grade %d from teacher %s %s for subject %s",
//				student.getName(), student.getLastName(), grade.getValue(),
//				teacher.getName(), teacher.getLastName(), subject.getName()));
//		email.setTo(student.getParent().getEmail());
//		emailService.sendSimpleMessage(email);
		return gradeRepo.save(grade);
	}

	@Override
	public GradeEntity changeGrade(Integer gradeId, GradeEntity newGrade) {
		if(!gradeRepo.existsById(gradeId)) {
			return null;
		}
		GradeEntity grade = gradeRepo.findById(gradeId).get();
		grade.setDate(LocalDate.now());
		grade.setValue(newGrade.getValue());
		return gradeRepo.save(grade);
	}

	@Override
	public GradeEntity deleteGrade(Integer gradeId) {
		if(!gradeRepo.existsById(gradeId)) {
			return null;
		}
		GradeEntity grade = gradeRepo.findById(gradeId).get();
		if(grade.isActive()) {
			grade.setActive(false);
			return gradeRepo.save(grade);
		}
		return null;
	}

	@Override
	public List<GradeEntity> getAllGradesByAdmin() {
		return (List<GradeEntity>) gradeRepo.findAll();
	}

	@Override
	public List<GradeEntity> getAllGradesByTeacher() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String)auth.getPrincipal();
		TeacherEntity teacher = teacherRepo.findByUsername(username);
		List<GradeEntity> grades = new ArrayList<>();
		List<StudentEntity> students = (List<StudentEntity>) studentRepo.findAll();
		List<TeacherSubjectEntity> teacherSubject = (List<TeacherSubjectEntity>) teacherSubjectRepo.findByTeacher(teacher);
		List<StudentTeacherSubjectEntity> studentTeacherSubject = new ArrayList<>();
		for(TeacherSubjectEntity a : teacherSubject) {
			for(StudentEntity b : students) {
				studentTeacherSubject.add(stsRepo.findByStudentAndTeacherSubject(b, a));
			}
		}
		for(StudentTeacherSubjectEntity a : studentTeacherSubject) {
			List<GradeEntity> lista = gradeRepo.findByStudentTeacherSubject(a);
			for(GradeEntity b : lista) {
				grades.add(b);
			}
		}
		
		return grades;
	}

	@Override
	public List<GradeEntity> getAllGradesByStudent() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String)auth.getPrincipal();
		StudentEntity student = studentRepo.findByUsername(username);
		List<GradeEntity> studentGrades = new ArrayList<>();
		List<StudentTeacherSubjectEntity> studentTeacherSubjectsList = (List<StudentTeacherSubjectEntity>) stsRepo.findByStudent(student);
		for(StudentTeacherSubjectEntity a : studentTeacherSubjectsList) {
			List<GradeEntity> lista = (List<GradeEntity>) gradeRepo.findByStudentTeacherSubject(a);
			for(GradeEntity b : lista) {
				studentGrades.add(b);
			}
		}
		return studentGrades;
	}

	@Override
	public List<GradeEntity> getAllGradesByParent() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String)auth.getPrincipal();
		ParentEntity parent = parentRepo.findByUsername(username);
		List<GradeEntity> grades = new ArrayList<>();
		List<StudentEntity> students = (List<StudentEntity>) studentRepo.findByParent(parent);
		for(StudentEntity student : students) {
			List<StudentTeacherSubjectEntity> studentTeacherSubjectsList = (List<StudentTeacherSubjectEntity>) stsRepo.findByStudent(student);
			for(StudentTeacherSubjectEntity a : studentTeacherSubjectsList) {
				List<GradeEntity> lista = (List<GradeEntity>) gradeRepo.findByStudentTeacherSubject(a);
				for(GradeEntity b : lista) {
					grades.add(b);
				}
			}
		}
		return grades;
	}


}
