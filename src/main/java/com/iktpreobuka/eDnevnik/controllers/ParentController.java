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
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.eDnevnik.controllers.util.RESTError;
import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.services.ParentService;
import com.iktpreobuka.eDnevnik.validation.ParentCustomValidator;
import com.iktpreobuka.eDnevnik.validation.Validation;

@RestController
@RequestMapping(path = "/dnevnik/parent")
public class ParentController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ParentCustomValidator parentCustomerValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(parentCustomerValidator);
	}
	
	@Autowired
	private ParentService parentService;
	
	@Autowired
	private ParentRepository parentRepo;
	
	@Secured("ROLE_ADMIN")
	@PostMapping(path = "/createParent")
	public ResponseEntity<?> createParent (@Valid @RequestBody ParentDTO newParent, BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		logger.info("Created parent");
		return new ResponseEntity<>(parentService.createParent(newParent), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping(path = "/changeParent/{parentId}")
	public ResponseEntity<?> changeParent (@PathVariable Integer parentId, @RequestBody ParentDTO changeParent,
			BindingResult result){
		if(result.hasErrors()) {
			return new ResponseEntity<>(Validation.createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		logger.info("Changed parent with id:" + parentId);
		return new ResponseEntity<>(parentService.changeParent(parentId, changeParent), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping(path = "/deleteParent/{parentId}")
	public ResponseEntity<?> deleteParent(@PathVariable Integer parentId){
		if(!parentRepo.existsById(parentId)) {
			return new ResponseEntity<>(new RESTError(1, "Parent with id:" + parentId + " doesn't exists."), HttpStatus.NOT_FOUND);
		}
		logger.info("Deleted parent with id:" + parentId);
		return new ResponseEntity<>(parentService.deleteParent(parentId), HttpStatus.OK);
	}
}
