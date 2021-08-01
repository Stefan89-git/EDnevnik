package com.iktpreobuka.eDnevnik.services;

import com.iktpreobuka.eDnevnik.entities.ParentEntity;
import com.iktpreobuka.eDnevnik.entities.dto.ParentDTO;

public interface ParentService {

	public ParentEntity createParent(ParentDTO newParent);
	
	public ParentEntity changeParent(Integer parentId, ParentDTO changeParent);
	
	public ParentEntity deleteParent(Integer parentId);
}
