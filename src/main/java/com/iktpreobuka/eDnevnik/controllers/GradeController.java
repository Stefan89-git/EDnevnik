package com.iktpreobuka.eDnevnik.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.controllers.util.RESTError;
import com.iktpreobuka.eDnevnik.entities.GradeEntity;
import com.iktpreobuka.eDnevnik.entities.StudentTeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.repositories.GradeRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentRepository;
import com.iktpreobuka.eDnevnik.repositories.StudentTeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.services.GradeService;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping(path = "/dnevnik/grade")
public class GradeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GradeService gradeSevice;
	
	@Autowired
	private GradeRepository gradeRepo;
	
	@Autowired
	private StudentRepository studentRepo;
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;
	
	@Autowired
	private StudentTeacherSubjectRepository stsRepo;
	
	
	@Secured("ROLE_ADMIN")
	@PostMapping(path = "/crateGradeByAdmin")
	public ResponseEntity<?> crateGradeByAdmin(@RequestParam Integer studentId, @RequestParam Integer teacherSubjectId,
			@Valid @RequestBody GradeEntity newGrade, BindingResult result ){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if(!studentRepo.existsById(studentId)) {
			return new ResponseEntity<>(new RESTError(1, "Student with id:" + studentId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		if(!teacherSubjectRepo.existsById(teacherSubjectId)) {
			return new ResponseEntity<>(new RESTError(2, "TeacherSubject with id:" + teacherSubjectId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Created grade by Admin");
		return new ResponseEntity<>(gradeSevice.crateGradeByAdmin(studentId, teacherSubjectId, newGrade), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(path = "/changeGrade")
	public ResponseEntity<?> changeGrade (@RequestParam Integer gradeId, @Valid @RequestBody GradeEntity newGrade,
			BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if(!gradeRepo.existsById(gradeId)) {
			return new ResponseEntity<>(new RESTError(1, "Grade with id:" + gradeId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Changed grade with id:" + gradeId);
		return new ResponseEntity<>(gradeSevice.changeGrade(gradeId, newGrade), HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@PostMapping(path = "/createGradeByTeacher")
	public ResponseEntity<?> createGradeByTeacher(@RequestParam Integer studentId, @RequestParam Integer subjectId ,
			@Valid @RequestBody GradeEntity newGrade, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		if(!studentRepo.existsById(studentId)) {
			return new ResponseEntity<>(new RESTError(1, "Student with id:" + studentId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		if(!subjectRepo.existsById(subjectId)) {
			return new ResponseEntity<>(new RESTError(1, "Subject with id:" + subjectId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Created grade by Teacher");
		return new ResponseEntity<>(gradeSevice.createGradeByTeacher(studentId, subjectId, newGrade), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping(path = "/deleteGrade")
	public ResponseEntity<?> deleteGrade (@RequestParam Integer gradeId){
		if(!gradeRepo.existsById(gradeId)) {
			return new ResponseEntity<>(new RESTError(1, "Grade with id:" + gradeId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Deleted grade with id:" + gradeId);
		return new ResponseEntity<>(gradeSevice.deleteGrade(gradeId), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(path = "/getAllGradesByAdmin")
	public ResponseEntity<?> getAllGradesByAdmin(){
		logger.info("Get all grades by admin");
		return new ResponseEntity<>(gradeSevice.getAllGradesByAdmin(), HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@GetMapping(path = "/getAllGradesByTeacher")
	public ResponseEntity<?> getAllGradesByTeacher(){
		logger.info("Get all grades by teacher");
		return new ResponseEntity<>(gradeSevice.getAllGradesByTeacher(), HttpStatus.OK);
	}
	
	@Secured("ROLE_STUDENT")
	@GetMapping(path = "/getAllGradesByStudent")
	public ResponseEntity<?> getAllGradesByStudent(){
		logger.info("Get all grades by student");
		return new ResponseEntity<>(gradeSevice.getAllGradesByStudent(), HttpStatus.OK);
	}
	
	@Secured("ROLE_PARENT")
	@GetMapping(path = "/getAllGradesByParent")
	public ResponseEntity<?> getAllGradesByParent(){
		logger.info("Get all grades by parent");
		return new ResponseEntity<>(gradeSevice.getAllGradesByParent(), HttpStatus.OK);
	}
	
}
