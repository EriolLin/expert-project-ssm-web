package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.AdminsetMapper;
import com.backoffice.model.Adminset;
import com.backoffice.model.AdminsetExample;
import com.backoffice.service.IAdminsetService;

@Service
@Transactional
public class AdminsetServiceImpl implements IAdminsetService{
	
	@Autowired
	AdminsetMapper adminsetMapper;

	@Override
	public Adminset findByid(int id) {
		// TODO Auto-generated method stub
		return adminsetMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Adminset> findAllAdminset() {
		// TODO Auto-generated method stub
		AdminsetExample adminsetExample = new AdminsetExample();
		return adminsetMapper.selectByExample(adminsetExample);
	}

	@Override
	public void saveOrUpdate(Adminset adminset) {
		// TODO Auto-generated method stub
		if (adminset.getId() == null) {
			adminsetMapper.insert(adminset);
		} else {
			adminsetMapper.updateByPrimaryKeySelective(adminset);
		}
	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		adminsetMapper.selectByPrimaryKey(id);
	}


}
