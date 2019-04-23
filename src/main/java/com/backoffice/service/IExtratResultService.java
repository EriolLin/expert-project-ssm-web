package com.backoffice.service;

import java.util.List;

import com.backoffice.model.ExtratResult;

public interface IExtratResultService {
	public ExtratResult findByid(int id);
	public List<ExtratResult> findAllResult();
	public void saveOrUpdate(ExtratResult extratResult);
	public void deleteByid(int id);
}
