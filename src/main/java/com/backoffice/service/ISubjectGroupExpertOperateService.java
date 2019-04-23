package com.backoffice.service;

import java.util.List;

import com.backoffice.model.SubjectGroupExpertOperate;

public interface ISubjectGroupExpertOperateService {
	public SubjectGroupExpertOperate findByid(int id);
	public List<SubjectGroupExpertOperate> findAllSubjectGroupExpertOperate();
	public void saveOrUpdate(SubjectGroupExpertOperate subjectGroupExpertOperate);
	public void deleteByid(int id);
}
