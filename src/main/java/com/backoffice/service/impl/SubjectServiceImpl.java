package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.SubjectMapper;
import com.backoffice.model.Subject;
import com.backoffice.model.SubjectExample;
import com.backoffice.service.ISubjectService;

@Service
@Transactional
public class SubjectServiceImpl implements ISubjectService {
	@Autowired
	SubjectMapper subjectMapper;

	@Override
	public Subject findByid(int id) {
		// TODO Auto-generated method stub
		return subjectMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Subject> findAllSubject() {
		// TODO Auto-generated method stub
		SubjectExample subjectExample = new SubjectExample();
		return subjectMapper.selectByExample(subjectExample);
	}

	@Override
	public void saveOrUpdate(Subject subject) {
		// TODO Auto-generated method stub
		if (subject.getId() == null) {
			subjectMapper.insert(subject);
		} else {
			subjectMapper.updateByPrimaryKeySelective(subject);
		}

	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		subjectMapper.deleteByPrimaryKey(id);
	}

}
