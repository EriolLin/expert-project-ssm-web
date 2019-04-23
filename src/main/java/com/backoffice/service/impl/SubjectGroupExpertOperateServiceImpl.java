package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.SubjectGroupExpertOperateMapper;
import com.backoffice.model.SubjectGroupExpertOperate;
import com.backoffice.model.SubjectGroupExpertOperateExample;
import com.backoffice.service.ISubjectGroupExpertOperateService;

@Service
@Transactional
public class SubjectGroupExpertOperateServiceImpl implements ISubjectGroupExpertOperateService{
	
	@Autowired
	SubjectGroupExpertOperateMapper subjectGroupExpertOperateMapper;
	
	@Override
	public SubjectGroupExpertOperate findByid(int id) {
		// TODO Auto-generated method stub
		return subjectGroupExpertOperateMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<SubjectGroupExpertOperate> findAllSubjectGroupExpertOperate() {
		// TODO Auto-generated method stub
		SubjectGroupExpertOperateExample subjectGroupControllerExample = new SubjectGroupExpertOperateExample();
		return subjectGroupExpertOperateMapper.selectByExample(subjectGroupControllerExample);
	}

	@Override
	public void saveOrUpdate(SubjectGroupExpertOperate subjectGroupExpertOperate) {
		// TODO Auto-generated method stub
		if(subjectGroupExpertOperate.getId() == null) {
			subjectGroupExpertOperateMapper.insert(subjectGroupExpertOperate);
		}else {
			subjectGroupExpertOperateMapper.updateByPrimaryKey(subjectGroupExpertOperate);
		}
	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		subjectGroupExpertOperateMapper.deleteByPrimaryKey(id);
	}

}
