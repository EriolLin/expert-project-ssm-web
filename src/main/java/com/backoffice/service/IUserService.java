package com.backoffice.service;

import java.util.List;

import com.backoffice.model.User;

public interface IUserService {
	public User findByid(int id);
	public List<User> findAllUser();
	public void saveOrUpdate(User User);
	public void deleteByid(int id);
}
