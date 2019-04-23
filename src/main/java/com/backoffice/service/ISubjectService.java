package com.backoffice.service;

import java.util.List;

import com.backoffice.model.Subject;

public interface ISubjectService {
	public Subject findByid(int id);
	public List<Subject> findAllSubject();
	public void saveOrUpdate(Subject subject);
	public void deleteByid(int id);
}
