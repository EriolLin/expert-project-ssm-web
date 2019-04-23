package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.ExtratResultMapper;
import com.backoffice.model.ExtratResult;
import com.backoffice.model.ExtratResultExample;
import com.backoffice.service.IExtratResultService;

@Service
@Transactional
public class ExtratResultServiceImpl implements IExtratResultService {
	

	@Autowired
	ExtratResultMapper extratResultMapper;
	@Override
	public ExtratResult findByid(int id) {
		// TODO Auto-generated method stub
		return extratResultMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ExtratResult> findAllResult() {
		// TODO Auto-generated method stub
		ExtratResultExample extratResultExample= new ExtratResultExample();
		return extratResultMapper.selectByExample(extratResultExample);
		}

	@Override
	public void saveOrUpdate(ExtratResult extratResult) {
		// TODO Auto-generated method stub
		if(extratResult.getId() == null) {
			extratResultMapper.insert(extratResult);
		}else {
			extratResultMapper.updateByPrimaryKeySelective(extratResult);
		}
		
	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		extratResultMapper.deleteByPrimaryKey(id);
	}

}
