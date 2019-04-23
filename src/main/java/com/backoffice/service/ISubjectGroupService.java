package com.backoffice.service;

import java.util.List;

import com.backoffice.model.SubjectGroup;

public interface ISubjectGroupService {
	public SubjectGroup findByid(int id);
	public List<SubjectGroup> findAllGroup();
	public void saveOrUpdate(SubjectGroup subject_group);
	public void deleteByid(int id);
}
