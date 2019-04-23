package com.backoffice.service;

import java.util.List;

import com.backoffice.model.Expert;

public interface IExpertService {
	public Expert findByid(int id);
	public List<Expert> findAllExpert();
	public void saveOrUpdate(Expert expert);
	public void deleteByid(int id);
	public int getPriority(int id);
	public int getPriorityNor(int id);
}
