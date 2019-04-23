package com.backoffice.controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.index.CandidateComponentsIndex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backoffice.model.Subject;
import com.backoffice.model.SubjectGroup;
import com.backoffice.service.ISubjectGroupService;
import com.backoffice.service.ISubjectService;

@Controller
@RequestMapping("subjectGroup")
public class SubjectGroupController {
	@Autowired
	ISubjectGroupService iSubjectGroupService;
	@Autowired
	ISubjectService iSubjectService;

	@RequestMapping("findbyid")
	public String findbyid(Model model) {
		// ������
		SubjectGroup subjectGroup = iSubjectGroupService.findByid(1);
		System.out.println("subjectGroup:" + subjectGroup);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		String str = sdf.format((Date)subjectGroup.getBirth());
//		System.out.println("birth:"+str);
		// ������
		model.addAttribute("subjectGroup:", subjectGroup);
		return "index";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public List list() {
		List<SubjectGroup> subjectGroups = iSubjectGroupService.findAllGroup();
		for (SubjectGroup subjectGroup : subjectGroups) {
			System.out.println(subjectGroup);
		}
		return subjectGroups;
	}
	
	@RequestMapping("ownedSubjectList")
	@ResponseBody
	public List ownedSubjectList(@RequestBody String id) {
		
		List<String> resultShow=new ArrayList<String>();
		
		String regEx="[^0-9]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(id); 
		Integer id2 = Integer.parseInt(m.replaceAll("").trim());
		System.out.println("id2========="+id2);
		//总学科
		List<Subject> subjects = iSubjectService.findAllSubject();
		List<String> subjectStringList = new ArrayList<>();
		int i = 0;
		for (Subject subject : subjects) {
			System.out.println(subject);
			String sjson = "{\"id\":\"" + subject.getId() + "\"," + "\"remarks\":\"" + subject.getRemarks() + "\","
					+ "\"subjectName\":\"" + subject.getSubjectName() + "\"}";
			subjectStringList.add(subject.getSubjectName());
			i++;
		}
		
		for (String string : subjectStringList) {
			System.out.println("总学科==="+string);
		}
		
		//总已选学科
		List<SubjectGroup> subjectGroups = iSubjectGroupService.findAllGroup();
		String ownedSubjectListString = "";
		for (SubjectGroup subjectGroup : subjectGroups) {
			ownedSubjectListString += subjectGroup.getSubSubject()+",";
		}
//		System.out.println(ownedSubjectListString);
		List<String> result = Arrays.asList(ownedSubjectListString.split(","));
		
		
		for (String string : result) {
			System.out.println("总已选学科"+string);
		}
		
		
		if(id2>0) {
			//当前学科组已选学科
			SubjectGroup subjectGroupByid = iSubjectGroupService.findByid(id2);
			List<String> subjectStringListByid = Arrays.asList(subjectGroupByid.getSubSubject().split(","));

			for (String string : subjectStringListByid) {
				System.out.println("当前学科组已选学科"+string);
			}
			
			HashSet hs1 = new HashSet(subjectStringList);
			HashSet hs2 = new HashSet(result);
			HashSet hs3 = new HashSet(subjectStringListByid);
			hs2.removeAll(hs3);
			hs1.removeAll(hs2);
			resultShow.addAll(hs1);
		}else {
			HashSet hs1 = new HashSet(subjectStringList);
			HashSet hs2 = new HashSet(result);
			hs1.removeAll(hs2);
			resultShow.addAll(hs1);
			System.out.println(resultShow);
		}
		
		
		

//		return ownedSubjectListString;
		return resultShow;
	}
	
//	@RequestMapping("save")
//	public String save(SubjectGroup subjectGroups) {
//		iSubjectGroupService.saveOrUpdate(subjectGroups);
//		return null;
//	}
	
//	@RequestMapping("save")
//	@ResponseBody
//	public String save(@RequestBody SubjectGroup subjectGroups) {
//		System.out.println(subjectGroups);
//		iSubjectGroupService.saveOrUpdate(subjectGroups);
//		String flag = "{\"flag\":\"success\"}";
//		return flag;
//	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(@RequestBody SubjectGroup subjectGroups) {
		System.out.println(subjectGroups);
		if(subjectGroups.getId() == -1) {
			subjectGroups.setId(null);
		}
		iSubjectGroupService.saveOrUpdate(subjectGroups);

		return "success";
	}
	
	@RequestMapping("update")
	public String update(SubjectGroup subjectGroups) {
		iSubjectGroupService.saveOrUpdate(subjectGroups);
		return null;
	}
	
//	public String delete(int id) {
//		iSubjectGroupService.deleteByid(id);
//		return null;
//	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestBody String id) {
		System.out.println(id);
//		iSubjectGroupService.deleteByid(id);
		return "success";
	}
	
	
	@RequestMapping("deleteLot")
	@ResponseBody
	public String deleteLot(@RequestBody String id) {
		System.out.println(id);
//		iSubjectService.deleteByid(id);
		//{"deleteLotId":"10+9+8+7+6+5+4+3+2+1+"}
		System.out.println(id.substring(id.indexOf("{\"deleteLotId\":\"")+"{\"deleteLotId\":\"".length(),id.indexOf(",\"}")));
		String strId = id.substring(id.indexOf("{\"deleteLotId\":\"")+"{\"deleteLotId\":\"".length(),id.indexOf(",\"}"));
		System.out.println(strId);
		List<String> strIdList = Arrays.asList(strId.split(","));
		for (String string : strIdList) {
			System.out.println("strIdList="+string);
		}
		return "success";
	}
	
}
