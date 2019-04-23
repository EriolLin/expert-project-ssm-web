package com.backoffice.controller;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backoffice.model.Subject;
import com.backoffice.service.ISubjectService;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("subject")
public class SubjectController {
	@Autowired
	ISubjectService iSubjectService;

//	@RequestMapping("findbyid")
//	public String findbyid(Model model) {
//		// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
//		Subject Subject = iSubjectService.findByid(1);
//		System.out.println("Subject:" + Subject);
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
////		String str = sdf.format((Date)Subject.getBirth());
////		System.out.println("birth:"+str);
//		// ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½
//		model.addAttribute("Subject", Subject);
//		return "index";
//	}

	@RequestMapping("findbyid")
	@ResponseBody
	public String findbyid() {

		Subject subject = iSubjectService.findByid(1);
		System.out.println("Subject:" + subject);
		String sjson = "{\"id\":\"" + subject.getId() + "\"," + "\"remarks\":\"" + subject.getRemarks() + "\","
				+ "\"subjectName\":\"" + subject.getSubjectName() + "\"}";
		System.out.println("sjson" + sjson);

		return sjson;
	}

//	@RequestMapping("list")
//	public String list() {
//		List<Subject> Subjects = iSubjectService.findAllSubject();
//		for (Subject Subject : Subjects) {
//			System.out.println(Subject);
//		}
//		return null;
//	}

	@RequestMapping("list")
	@ResponseBody
	public List list(@RequestBody Subject subStr) {
		System.out.println(subStr);

		List<Subject> subjects = iSubjectService.findAllSubject();

		for (Subject subject : subjects) {
			System.out.println(subject);
			String sjson = "{\"id\":\"" + subject.getId() + "\"," + "\"remarks\":\"" + subject.getRemarks() + "\","
					+ "\"subjectName\":\"" + subject.getSubjectName() + "\"}";
		}
		return subjects;
	}
	
	@RequestMapping("subjectNameList")
	@ResponseBody
	public List subjectNameList() {
//		System.out.println("进入成功");
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
		return subjectStringList;
	}

//	@RequestMapping("save")
//	@ResponseBody
//	public String save(@RequestBody Subject subject) {
//		if(subject.getId() == -1) {
//			subject.setId(null);
//		}
//		System.out.println(subject);
//		
//		iSubjectService.saveOrUpdate(subject);
////		String sjson = "{\"id\":\"" + "success" + "\"}";
//		String jsonString = "{\"flag\":\"success\"}";
//		System.out.println(jsonString);
//		return jsonString;
//	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(@RequestBody String subject) {
		Gson gson = new Gson();
		Subject subject2 = gson.fromJson(subject, Subject.class);
		if(subject2.getId() == -1) {
			subject2.setId(null);
		}
		System.out.println(subject2);
		
		iSubjectService.saveOrUpdate(subject2);
//		String sjson = "{\"id\":\"" + "success" + "\"}";
		String jsonString = "{\"flag\":\"success\"}";
		System.out.println(jsonString);
		return "success";
	}
	

	@RequestMapping("update")
	public String update(Subject Subject) {
		iSubjectService.saveOrUpdate(Subject);
		return null;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestBody String id) {
		System.out.println(id);
		
		String regEx="[^0-9]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(id); 
		id = m.replaceAll("").trim();
		int intid = Integer.parseInt(id);
		System.out.println(intid);
//		iSubjectService.deleteByid(id);
		return "success";
	}
	
	@RequestMapping("deleteLot")
	@ResponseBody
	public String deleteLot(@RequestBody String id) {
		System.out.println(id);
		
//		String id="{\"deleteLotId\":\"4+5+2+\"}";
		Pattern pattern = Pattern.compile("[^0-9]");
        Matcher a1 = pattern.matcher(id);
        //System.out.println(a1.replaceAll(" "));
        String[] dis = a1.replaceAll(" ").split("\\s+");
        List<Integer> list = new ArrayList<Integer>();
        for(int i=1;i<dis.length;i++) {
        	//System.out.println(dis[i]);
        	list.add(Integer.parseInt(dis[i]));
        }
		for(Integer list2:list) {
			System.out.println(list2);
		}

//		iSubjectService.deleteByid(id);
		return "success";
	}
	
}
