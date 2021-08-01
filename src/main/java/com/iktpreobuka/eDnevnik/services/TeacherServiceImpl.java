package com.iktpreobuka.eDnevnik.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.eDnevnik.entities.SubjectEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherEntity;
import com.iktpreobuka.eDnevnik.entities.TeacherSubjectEntity;
import com.iktpreobuka.eDnevnik.entities.dto.TeacherDto;
import com.iktpreobuka.eDnevnik.repositories.RoleRepository;
import com.iktpreobuka.eDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.eDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.eDnevnik.utils.Encryption;
import com.iktpreobuka.eDnevnik.validation.Validation;

@Service
public class TeacherServiceImpl implements TeacherService{

	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private TeacherRepository teacherRepo;
	
	@Autowired
	private SubjectRepository subjectRepo;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepo;
	
	@Override
	public TeacherEntity createTeacher(TeacherDto newTeacher) {
		TeacherEntity teacher = new TeacherEntity();
		if(newTeacher.getPassword().equals(newTeacher.getRepetedPass())) {
			teacher.setName(newTeacher.getName());
			teacher.setLastName(newTeacher.getLastName());
			teacher.setUsername(newTeacher.getUsername());
			teacher.setPassword(Encryption.getPassEncoded(newTeacher.getPassword()));
			teacher.setActive(true);
			teacher.setRole(roleRepo.findByName("ROLE_TEACHER"));
			return teacherRepo.save(teacher);
		}
		return null;
	}

	@Override
	public TeacherEntity changeTeacher(Integer teacherId, TeacherDto newTeacher) {
		if(!teacherRepo.existsById(teacherId)) {
			return null;
		}
		TeacherEntity teacher = teacherRepo.findById(teacherId).get();
		if(newTeacher.getPassword() == null) {
			teacher.setName(Validation.setIfNotNull(teacher.getName(), newTeacher.getName()));
			teacher.setLastName(Validation.setIfNotNull(teacher.getLastName(), newTeacher.getLastName()));
			teacher.setUsername(Validation.setIfNotNull(teacher.getUsername(), newTeacher.getUsername()));
			return teacherRepo.save(teacher);
		}else if(newTeacher.getPassword().equals(newTeacher.getRepetedPass())) {
			teacher.setName(Validation.setIfNotNull(teacher.getName(), newTeacher.getName()));
			teacher.setLastName(Validation.setIfNotNull(teacher.getLastName(), newTeacher.getLastName()));
			teacher.setUsername(Validation.setIfNotNull(teacher.getUsername(), newTeacher.getUsername()));
			teacher.setPassword(Encryption.getPassEncoded(newTeacher.getPassword()));
			return teacherRepo.save(teacher);
		}
		return null;
	}

	@Override
	public TeacherEntity deleteTeacher(Integer teacherId) {
		if(!teacherRepo.existsById(teacherId)) {
			return null;
		}
		TeacherEntity teacher = teacherRepo.findById(teacherId).get();
		if(teacher.isActive()) {
			teacher.setActive(false);
			return teacherRepo.save(teacher);
		}
		return null;
	}

	@Override
	public TeacherSubjectEntity addSubjectForTeacher(Integer teacherId, Integer subjectId) {
		if(!teacherRepo.existsById(teacherId)) {
			return null;
		}
		TeacherEntity teacher = teacherRepo.findById(teacherId).get();
		
		if(!subjectRepo.existsById(subjectId)) {
			return null;
		}
		SubjectEntity subject = subjectRepo.findById(subjectId).get();
		if(teacher.isActive() && subject.isActive()) {
			if(!teacherSubjectRepo.existsByTeacherAndSubject(teacher, subject)) {
			TeacherSubjectEntity teacherSubject = new TeacherSubjectEntity();
			teacherSubject.setTeacher(teacher);
			teacherSubject.setSubject(subject);
			return teacherSubjectRepo.save(teacherSubject);
			}
		}
		return null;
	}

	@Override
	public List<SubjectEntity> getAllSubjetcsByTeacher(Integer teacherId) {
		if(!teacherRepo.existsById(teacherId)) {
			return null;
		}
		TeacherEntity teacher = teacherRepo.findById(teacherId).get();
		List<TeacherSubjectEntity> lista = teacherSubjectRepo.findByTeacher(teacher);
		List<SubjectEntity> subjects = new ArrayList<>();
		for(TeacherSubjectEntity ts : lista) {
			subjects.add(ts.getSubject());
		}
		return subjects;
	}

	@Override
	public List<SubjectEntity> getAllSubjetcsByTeacher() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = (String)auth.getPrincipal();
		TeacherEntity teacher = teacherRepo.findByUsername(username);
		List<TeacherSubjectEntity> lista = teacherSubjectRepo.findByTeacher(teacher);
		List<SubjectEntity> subjects = new ArrayList<>();
		for(TeacherSubjectEntity ts : lista) {
			subjects.add(ts.getSubject());
		}
		return subjects;
	}

}
