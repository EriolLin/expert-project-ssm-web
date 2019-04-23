package com.backoffice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backoffice.mapper.UserMapper;
import com.backoffice.model.User;
import com.backoffice.model.UserExample;
import com.backoffice.service.IUserService;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	@Autowired
	UserMapper userMapper;
	@Override
	public User findByid(int id) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		UserExample userExample = new UserExample();
		return userMapper.selectByExample(userExample);
	}

	@Override
	public void saveOrUpdate(User user) {
		// TODO Auto-generated method stub
		if(user.getId() == null) {
			userMapper.insert(user);
		}else {
			userMapper.updateByPrimaryKeySelective(user);
		}
	}

	@Override
	public void deleteByid(int id) {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(id);

	}

}
