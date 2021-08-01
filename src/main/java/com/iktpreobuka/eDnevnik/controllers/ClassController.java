package com.iktpreobuka.eDnevnik.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.controllers.util.RESTError;
import com.iktpreobuka.eDnevnik.entities.ClassEntity;
import com.iktpreobuka.eDnevnik.repositories.ClassRepository;
import com.iktpreobuka.eDnevnik.services.ClassService;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping(path = "/dnevnik/classes")
public class ClassController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ClassRepository classRepo;
	
	@Autowired
	private ClassService classService;
	
	
	@PostMapping( path = "/addNew")
	public ResponseEntity<?> addNewClass(@Valid @RequestBody ClassEntity newClass, BindingResult result) {
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		logger.info("Created classroom");
		return new ResponseEntity<>(classService.createClassroom(newClass), HttpStatus.OK);
	}
	
	
	@PutMapping(path = "/changeClass/{classId}")
	public ResponseEntity<?> changeClassroom(@PathVariable Integer classId, @Valid @RequestBody ClassEntity classr,
			BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		logger.info("Changed classroom with id:" + classId);
		return new ResponseEntity<>(classService.changeClassroom(classId, classr), HttpStatus.OK);
	}
	
	
	@DeleteMapping(path = "/deleteClass/{classId}")
	public ResponseEntity<?> deleteClassroom(@PathVariable Integer classId){
		if(!classRepo.existsById(classId)) {
			return new ResponseEntity<>(new RESTError(1, "Classroom with id:" + classId + " doesn't exist."), HttpStatus.NOT_FOUND);
		}
		logger.info("Delete classroom with id:" + classId);
		return new ResponseEntity<>(classService.deleteClassroom(classId), HttpStatus.OK);
	}
}
