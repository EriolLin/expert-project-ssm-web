package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.SubjectGroupMapper;
import com.backoffice.model.SubjectGroup;
import com.backoffice.model.SubjectGroupExample;
import com.backoffice.service.ISubjectGroupService;

@Service
@Transactional
public class SubjectGroupServiceImpl implements ISubjectGroupService {
	@Autowired
	SubjectGroupMapper subjectgroupMapper;
	@Override
	public SubjectGroup findByid(int id) {
		// TODO Auto-generated method stub
		return subjectgroupMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SubjectGroup> findAllGroup() {
		// TODO Auto-generated method stub
		SubjectGroupExample sujectGroupExample= new SubjectGroupExample();
		
		return subjectgroupMapper.selectByExample(sujectGroupExample);
	}

	@Override
	public void saveOrUpdate(SubjectGroup subjectGroup) {
		// TODO Auto-generated method stub
		if(subjectGroup.getId() == null) {
			subjectgroupMapper.insert(subjectGroup);
		}else {
			subjectgroupMapper.updateByPrimaryKeySelective(subjectGroup);
		}
		
	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		subjectgroupMapper.deleteByPrimaryKey(id);
		
	}

}
