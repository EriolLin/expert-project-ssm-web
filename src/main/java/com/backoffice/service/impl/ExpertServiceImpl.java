package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.ExpertMapper;
import com.backoffice.model.Expert;
import com.backoffice.model.ExpertExample;
import com.backoffice.service.IExpertService;

@Service
@Transactional
public class ExpertServiceImpl implements IExpertService{
	
	@Autowired
	ExpertMapper expertMapper;
	
	@Override
	public Expert findByid(int id) {
		return expertMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Expert> findAllExpert() {
		ExpertExample expertExample = new ExpertExample();
		return expertMapper.selectByExample(expertExample);
	}

	@Override
	public void saveOrUpdate(Expert expert) {
		if(expert.getId() == null) {
			expertMapper.insert(expert);
		}else {
			expertMapper.updateByPrimaryKeySelective(expert);
		}
	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		expertMapper.deleteByPrimaryKey(id);
	}

	//计算抽取优先级
	@Override
	public int getPriority(int id) {
		int priorityNum = 0;
		Expert expert = expertMapper.selectByPrimaryKey(id);
		
		if(expert.getEducation().equals("本科以下")) {
			priorityNum += 1;
		}else if(expert.getEducation().equals("本科")) {
			priorityNum += 2;
		}else if(expert.getEducation().equals("研究生")) {
			priorityNum += 3;
		}else if(expert.getEducation().equals("博士")) {
			priorityNum += 4;
		}else if(expert.getEducation().equals("博士后")) {
			priorityNum += 5;
		}
		
		if(expert.getTitleLevel().equals("副教授级")) {
			priorityNum += 1;
		}else if(expert.getTitleLevel().equals("教授级")) {
			priorityNum += 3;
		}else if(expert.getTitleLevel().equals("特级教授级")) {
			priorityNum += 5;
		}
		
		if(expert.getPostLevel().equals("副处级")) {
			priorityNum += 1;
		}else if(expert.getPostLevel().equals("处级")) {
			priorityNum += 2;
		}else if(expert.getPostLevel().equals("副校级")) {
			priorityNum += 3;
		}else if(expert.getPostLevel().equals("校级")) {
			priorityNum += 4;
		}
			
		return priorityNum;
	}

	@Override
	public int getPriorityNor(int id) {
		// TODO Auto-generated method stub
		return 1;
	}
	
}
