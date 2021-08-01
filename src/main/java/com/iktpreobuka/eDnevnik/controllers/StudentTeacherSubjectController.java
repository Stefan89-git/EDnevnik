package com.iktpreobuka.eDnevnik.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.controllers.util.RESTError;
import com.iktpreobuka.eDnevnik.entities.StudentEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentTeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;

@RestController
@RequestMapping(path = "/api/v1/studentTeacherSubject")
public class StudentTeacherSubjectController {

		@Autowired
		private StudentRepository studentRepo;
		
		@Autowired
		private TeacherRepository teacherRepo;
		
		@Autowired
		private SubjectRepository subjectRepo;
		
		@Autowired
		private TeacherSubjectRepository teacherSubjectRepo;
		
		@Autowired
		private StudentTeacherSubjectRepository studentTeacherSubjectRepo;
		
		@Secured("ROLE_ADMIN")
		@RequestMapping(method = RequestMethod.POST, path = "/addSTS/student/{studentId}/teacher/{teacherId}/subject/{subjectId}")
		public ResponseEntity<?> createSTS(@PathVariable Integer studentId, @PathVariable Integer teacherId,
				@PathVariable Integer subjectId){
			if(!studentRepo.existsById(studentId)) {
				return new ResponseEntity<>(new RESTError(1, "Student with id: " + studentId + " doesn't exist"), HttpStatus.NOT_FOUND);
			}
			Optional<StudentEntity> optionalStudent = studentRepo.findById(studentId);
			StudentEntity student = optionalStudent.get();
			
			if(!teacherRepo.existsById(teacherId)) {
				return new ResponseEntity<>(new RESTError(1, "Teacher with id: " + teacherId + " doesn't exist"), HttpStatus.NOT_FOUND);
			}
			Optional<TeacherEntity> optionalTeacher = teacherRepo.findById(teacherId);
			TeacherEntity teacher = optionalTeacher.get();
			
			if(!subjectRepo.existsById(subjectId)) {
				return new ResponseEntity<>(new RESTError(1, "Subject with id: " + subjectId + " doesn't exist"), HttpStatus.NOT_FOUND);
			}
			Optional<SubjectEntity> optionalSubject = subjectRepo.findById(subjectId);
			SubjectEntity subject = optionalSubject.get();
			
			if(!teacherSubjectRepo.existsByTeacherAndSubject(teacher, subject)) {
				return new ResponseEntity<>(new RESTError(1, "TeacherSubject doesn't exist"), HttpStatus.NOT_FOUND);
			}
			TeacherSubjectEntity teacherSubject = teacherSubjectRepo.findByTeacherAndSubject(teacher, subject);
			
			StudentTeacherSubjectEntity sTs = new StudentTeacherSubjectEntity();
			sTs.setStudent(student);
			sTs.setTeacherSubject(teacherSubject);
			studentTeacherSubjectRepo.save(sTs);
			
			return new ResponseEntity<>(sTs, HttpStatus.OK);
		}
}
