package com.iktpreobuka.eDnevnik.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.controllers.util.RESTError;
import com.iktpreobuka.eDnevnik.entities.dto.StudentDTO;
import com.iktpreobuka.eDnevnik.repositories.ClassRepository;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.services.StudentService;
import com.iktpreobuka.eDnevnik.validation.StudentCustomValidator;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping(path = "/dnevnik/student")
public class StudentController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StudentCustomValidator studentCustomValidator;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(studentCustomValidator);
	}
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ParentRepository parentRepo;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	
	@Secured("ROLE_ADMIN")
	@PostMapping(path = "/createStudent/{parentId}/classroom/{classId}")
	public ResponseEntity<?> createStudent(@PathVariable Integer parentId, @PathVariable Integer classId,
			@Valid @RequestBody StudentDTO newStudent, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if(!parentRepo.existsById(parentId)) {
			return new ResponseEntity<>(new RESTError(1, "Parent with id:" + parentId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		if(!classRepo.existsById(classId)) {
			return new ResponseEntity<>(new RESTError(2, "Classroom with id:" + classId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Created student");
		return new ResponseEntity<>(studentService.createStudent(newStudent, parentId, classId), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(path = "/changeStudent/{studentId}")
	public ResponseEntity<?> changeStudent (@PathVariable Integer studentId, @RequestBody StudentDTO newStudent,
			BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		logger.info("Changed student with id:" + studentId);
		return new ResponseEntity<>(studentService.changeStudent(studentId, newStudent), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(path = "/changeClassroomForStudent/{studentId}/classroom/{classId}")
	public ResponseEntity<?> changeClassroomForStudent(@PathVariable Integer studentId, @PathVariable Integer classId){
		if(!studentRepo.existsById(studentId)) {
			return new ResponseEntity<>(new RESTError(1, "Student with id:" + studentId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		if(!classRepo.existsById(classId)) {
			return new ResponseEntity<>(new RESTError(2, "Classroom with id:" + classId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Changed classroom for student with id:" + studentId);
		return new ResponseEntity<>(studentService.changeClassroomForStudent(studentId, classId), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping(path = "/deleteStudent/{studentId}")
	public ResponseEntity<?> deleteStudent(@PathVariable Integer studentId){
		if(!studentRepo.existsById(studentId)) {
			return new ResponseEntity<>(new RESTError(1, "Student with id:" + studentId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Deleted student with id:" + studentId);
		return new ResponseEntity<>(studentService.deleteStudent(studentId), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(path = "/addTeacherAndSubjectToStudent")
	public ResponseEntity<?> addTeacherAndSubjectToStudent(@RequestParam Integer studentId, @RequestParam Integer teacherId,
						@RequestParam Integer subjectId){
		if(!studentRepo.existsById(studentId)) {
			return new ResponseEntity<>(new RESTError(1, "Student with id:" + studentId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		if(!teacherRepo.existsById(teacherId)) {
			return new ResponseEntity<>(new RESTError(1, "Teacher with id:" + teacherId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		if(!subjectRepo.existsById(subjectId)) {
			return new ResponseEntity<>(new RESTError(1, "Subject with id:" + subjectId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		if(!teacherSubjectRepo.existsByTeacherAndSubject(teacherRepo.findById(teacherId).get(), subjectRepo.findById(subjectId).get())) {
			return new ResponseEntity<>(new RESTError(2, "Teacher with id:" + teacherId + " doesn't teaches subject with id:" 
													+ subjectId), HttpStatus.BAD_REQUEST);
		}
		logger.info("Added teacher and subject to student with id:" + studentId);
		return new ResponseEntity<>(studentService.addTeacherAndSubjectToStudent(studentId, teacherId, subjectId),
				HttpStatus.OK);
	}
}
