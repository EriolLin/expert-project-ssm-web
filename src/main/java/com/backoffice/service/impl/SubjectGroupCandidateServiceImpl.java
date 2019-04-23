package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.AdminsetMapper;
import com.backoffice.mapper.SubjectGroupCandidateMapper;
import com.backoffice.mapper.SubjectGroupMapper;
import com.backoffice.model.Adminset;
import com.backoffice.model.AdminsetExample;
import com.backoffice.model.SubjectGroupCandidate;
import com.backoffice.model.SubjectGroupCandidateExample;
import com.backoffice.service.IAdminsetService;
import com.backoffice.service.ISubjectGroupCandidateService;

@Service
@Transactional
public class SubjectGroupCandidateServiceImpl implements ISubjectGroupCandidateService{
	
	@Autowired
	SubjectGroupCandidateMapper subjectGroupCandidateMapper;

	@Override
	public SubjectGroupCandidate findByid(int id) {
		// TODO Auto-generated method stub
		return subjectGroupCandidateMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SubjectGroupCandidate> findAllSubjectGroupCandidate() {
		// TODO Auto-generated method stub
		SubjectGroupCandidateExample subjectGroupCandidateExample = new SubjectGroupCandidateExample();
		return subjectGroupCandidateMapper.selectByExample(subjectGroupCandidateExample);
	}

	@Override
	public void saveOrUpdate(SubjectGroupCandidate subjectGroupCandidate) {
		// TODO Auto-generated method stub
		if (subjectGroupCandidate.getId() == null) {
			subjectGroupCandidateMapper.insert(subjectGroupCandidate);
		} else {
			subjectGroupCandidateMapper.updateByPrimaryKeySelective(subjectGroupCandidate);
		}
	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		subjectGroupCandidateMapper.deleteByPrimaryKey(id);
	}
	
	
}
