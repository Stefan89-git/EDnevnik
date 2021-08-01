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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.controllers.util.RESTError;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherDto;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.services.TeacherService;
import com.iktpreobuka.eDnevnik.validation.TeacherCustomValidator;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping(path = "/dnevnik/teacher")
public class TeacherController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TeacherCustomValidator teacherCustomValidator;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(teacherCustomValidator);
	}
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;;
	
	@Secured("ROLE_ADMIN")
	@PostMapping(path = "/createTeacher")
	public ResponseEntity<?> crateTeacher(@Valid @RequestBody TeacherDto newTeacher, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		logger.info("Created teacher");
		return new ResponseEntity<>(teacherService.createTeacher(newTeacher), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(path = "/changeTeacher/{teacherId}")
	public ResponseEntity<?> changeTeacher(@PathVariable Integer teacherId, @RequestBody TeacherDto newTeacher){
		if(!teacherRepo.existsById(teacherId)) {
			return new ResponseEntity<>(new RESTError(1, "Teacher with id: " + teacherId + " doesn't exist"), HttpStatus.NOT_FOUND);
		}
		logger.info("Changed teacher with id" + teacherId);
		return new ResponseEntity<>(teacherService.changeTeacher(teacherId, newTeacher), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping(path = "/deleteTeacher/{teacherId}")
	public ResponseEntity<?> deleteTeacher(@PathVariable Integer teacherId){
		if(!teacherRepo.existsById(teacherId)) {
			return new ResponseEntity<>(new RESTError(1, "Teacher with id: " + teacherId + " doesn't exist"), HttpStatus.NOT_FOUND);
		}
		logger.info("Deleted teacher with id" + teacherId);
		return new ResponseEntity<>(teacherService.deleteTeacher(teacherId), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PostMapping(path = "/addSubjectToTeacher")
	public ResponseEntity<?> addSubjectToTeacher(@RequestParam Integer teacherId, @RequestParam Integer subjectId){
		if(!teacherRepo.existsById(teacherId)) {
			return new ResponseEntity<>(new RESTError(1, "Teacher with id: " + teacherId + " doesn't exist"), HttpStatus.NOT_FOUND);
		}
		if(!subjectRepo.existsById(subjectId)) {
			return new ResponseEntity<>(new RESTError(1, "Subject with id: " + subjectId + " doesn't exist"), HttpStatus.NOT_FOUND);
		}
		if(teacherSubjectRepo.existsByTeacherAndSubject(teacherRepo.findById(teacherId).get(), subjectRepo.findById(subjectId).get())) {
			return new ResponseEntity<>(new RESTError(1, "Teacher with id:" + teacherId + " already teaches subject with id:" 
													+ subjectId), HttpStatus.BAD_REQUEST);
		}
		logger.info("Added subject to teacher with id" + teacherId);
		return new ResponseEntity<>(teacherService.addSubjectForTeacher(teacherId, subjectId), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@GetMapping(path = "/getAllSubjetcsByTeacher")
	public ResponseEntity<?> getAllSubjetcsByTeacher(@RequestParam Integer teacherId){
		if(!teacherRepo.existsById(teacherId)) {
			return new ResponseEntity<>(new RESTError(1, "Teacher with id: " + teacherId + " doesn't exist"), HttpStatus.NOT_FOUND);
		}
		logger.info("Get all subject for teacher with id" + teacherId);
		return new ResponseEntity<>(teacherService.getAllSubjetcsByTeacher(teacherId), HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@GetMapping(path = "/getTeacherSubjects")
	public ResponseEntity<?> getAllSubjectsByTeacher(){
		logger.info("Get all subjects by teacher");
		return new ResponseEntity<>(teacherService.getAllSubjetcsByTeacher(), HttpStatus.OK); 
	}
	
}
