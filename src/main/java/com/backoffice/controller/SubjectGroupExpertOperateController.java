package com.backoffice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backoffice.model.Expert;
import com.backoffice.model.SubjectGroupExpertOperate;
import com.backoffice.service.IExpertService;
import com.backoffice.service.ISubjectGroupExpertOperateService;
import com.backoffice.service.impl.SubjectGroupExpertOperateServiceImpl;

@Controller
@RequestMapping("subjectGroupController")
public class SubjectGroupExpertOperateController {
	
	@Autowired
	IExpertService iExpertService;
	@Autowired
	ISubjectGroupExpertOperateService iSubjectGroupExpertOperateService;
	
	@RequestMapping("expert")
	@ResponseBody
	public List subjectCandidate(@RequestBody String subjectList) {
		System.out.println("subjectList=" + subjectList);
		String[] subjectListsplit = subjectList.split("\"");
		// 当前学科组学科
		List<String> subjectStrings = new ArrayList<>();
		// 存放结果 学科-
		Map<String, List<Expert>> resultMap = new HashMap<String, List<Expert>>();
		List<Expert> resultList = new ArrayList<>();
		// 所有专家
		List<Expert> expertall = iExpertService.findAllExpert();
		
		for (int i = 0; i < subjectListsplit.length; i++) {
			if (i % 2 == 1) {
				subjectStrings.add(subjectListsplit[i]);
			}
		}
		for (String subjectString : subjectStrings) {
			// 当前学科候选人
			List<Expert> currentSubjectCandidate = new ArrayList<>();
			for (Expert expert : expertall) {
				if (expert.getSubject().contains(subjectString)) {
					currentSubjectCandidate.add(expert);
					resultList.add(expert);
				}
			}
			if (currentSubjectCandidate != null) {
				resultMap.put(subjectString, currentSubjectCandidate);
				
			}
		}

		for (String key : resultMap.keySet()) {
			System.out.println(key + ":" + resultMap.get(key));
		}
		HashSet h = new HashSet(resultList);   
		resultList.clear();   
		resultList.addAll(h);  

		return resultList;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String expertStatusSave(@RequestParam("expertId") String expertId,@RequestParam("status") String status,@RequestParam("subjectGroupId") String subjectGroupId) {
		System.out.println("expertId="+expertId+"  status="+status+"  subjectGroupId="+subjectGroupId);
		
		SubjectGroupExpertOperate subjectGroupExpertOperate = new SubjectGroupExpertOperate();
		subjectGroupExpertOperate.setExpertid(Integer.parseInt(expertId));
		subjectGroupExpertOperate.setSubjectgroupid(Integer.parseInt(subjectGroupId));
		subjectGroupExpertOperate.setStatus(status);
		
		iSubjectGroupExpertOperateService.saveOrUpdate(subjectGroupExpertOperate);
		return "success";
	}
	
}
