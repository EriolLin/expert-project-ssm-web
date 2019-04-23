package com.backoffice.service;

import java.util.List;

import com.backoffice.model.SubjectGroupCandidate;

public interface ISubjectGroupCandidateService {
	public SubjectGroupCandidate findByid(int id);
	public List<SubjectGroupCandidate> findAllSubjectGroupCandidate();
	public void saveOrUpdate(SubjectGroupCandidate subjectGroupCandidate);
	public void deleteByid(int id);
}
