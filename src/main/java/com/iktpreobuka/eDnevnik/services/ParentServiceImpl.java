package com.iktpreobuka.eDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;
import com.iktpreobuka.eDnevnik.repositories.ParentRepository;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.utils.Encryption;
import com.iktpreobuka.eDnevnik.validation.Validation;

@Service
public class ParentServiceImpl implements ParentService{

	@Autowired
	private ParentRepository parentRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Override
	public ParentEntity createParent(ParentDTO newParent) {
		ParentEntity parent = new ParentEntity();
		if(newParent.getPassword().equals(newParent.getRepetedPass())) {
			parent.setName(newParent.getName());
			parent.setLastName(newParent.getLastName());
			parent.setUsername(newParent.getUsername());
			parent.setPassword(Encryption.getPassEncoded(newParent.getPassword()));
			parent.setEmail(newParent.getEmail());
			parent.setRole(roleRepo.findByName("ROLE_PARENT"));
			parent.setActive(true);
			return parentRepo.save(parent);
		}
		return null;
	}

	@Override
	public ParentEntity changeParent(Integer parentId, ParentDTO changeParent) {
		if(!parentRepo.existsById(parentId)) {
			return null;
		}
		ParentEntity parent = parentRepo.findById(parentId).get();
		if(changeParent.getPassword() == null) {
			parent.setName(Validation.setIfNotNull(parent.getName(), changeParent.getName()));
			parent.setLastName(Validation.setIfNotNull(parent.getLastName(), changeParent.getLastName()));
			parent.setUsername(Validation.setIfNotNull(parent.getUsername(), changeParent.getUsername()));
			parent.setEmail(Validation.setIfNotNull(parent.getEmail(), changeParent.getEmail()));
			return parentRepo.save(parent);
		}else if(changeParent.getPassword().equals(changeParent.getRepetedPass())) {
			parent.setName(Validation.setIfNotNull(parent.getName(), changeParent.getName()));
			parent.setLastName(Validation.setIfNotNull(parent.getLastName(), changeParent.getLastName()));
			parent.setUsername(Validation.setIfNotNull(parent.getUsername(), changeParent.getUsername()));
			parent.setPassword(Encryption.getPassEncoded(changeParent.getPassword()));
			parent.setEmail(Validation.setIfNotNull(parent.getEmail(), changeParent.getEmail()));
			return parentRepo.save(parent);
		}
		return null;
	}

	@Override
	public ParentEntity deleteParent(Integer parentId) {
		if(!parentRepo.existsById(parentId)) {
			return null;
		}
		ParentEntity parent = parentRepo.findById(parentId).get();
		if(parent.isActive()) {
			parent.setActive(false);
			return parentRepo.save(parent);
		}
		return null;
	}

}
