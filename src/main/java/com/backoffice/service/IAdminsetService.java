package com.backoffice.service;

import java.util.List;

import com.backoffice.model.Adminset;

public interface IAdminsetService {
	public Adminset findByid(int id);
	public List<Adminset> findAllAdminset();
	public void saveOrUpdate(Adminset adminset);
	public void deleteByid(int id);
}
