package com.backoffice.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.index.CandidateComponentsIndex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import com.backoffice.model.Expert;
import com.backoffice.model.Subject;
import com.backoffice.model.SubjectGroup;
import com.backoffice.service.IExpertService;
import com.backoffice.service.ISubjectGroupService;
import com.backoffice.service.ISubjectService;
import com.google.gson.Gson;

@Controller
@RequestMapping("expert")
public class ExpertController {

	@Autowired
	IExpertService iExpertService;
	@Autowired
	ISubjectGroupService iSubjectGroupService;
	@Autowired
	ISubjectService iSubjectService;
	

	@RequestMapping("subjectCandidate")
	@ResponseBody
	public Map subjectCandidate(@RequestBody String subjectList) {
		System.out.println("subjectList=" + subjectList);
		String[] subjectListsplit = subjectList.split("\"");
		// 当前学科组学科
		List<String> subjectStrings = new ArrayList<>();
		// 存放结果 学科-
		Map<String, List<Expert>> resultMap = new HashMap<String, List<Expert>>();
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
				if (expert.getSubject().contains(subjectString) && expert.getCandidate().equals("1")) {
					currentSubjectCandidate.add(expert);
				}
			}
			if (currentSubjectCandidate != null) {
				resultMap.put(subjectString, currentSubjectCandidate);
			}

		}

		for (String key : resultMap.keySet()) {
			System.out.println(key + ":" + resultMap.get(key));
		}

		return resultMap;
	}

	@RequestMapping("/upload")
	// @RequestBody
	// @RequestParam("picture")
	public void uploadlogo(HttpServletRequest request, PrintWriter writer) throws Exception {

		MultipartHttpServletRequest mutliRequest = (MultipartHttpServletRequest) request;
		MultipartFile mfile = mutliRequest.getFile("picture");

		String uploadFolder = request.getServletContext().getRealPath("/upload");
		System.out.println("uploadFolder:" + uploadFolder);
		File uploadFolderFile = new File(uploadFolder);
		if (!uploadFolderFile.exists()) {
			uploadFolderFile.mkdir();
		}

		// 2.文件
		String nameString = mfile.getOriginalFilename();
		String suffix = mfile.getOriginalFilename().split("\\.")[1];
		String fileName = UUID.randomUUID().toString() + "." + suffix;
		String totalPath = uploadFolder + "\\" + fileName;
		System.out.println("totalPath:" + totalPath);
		FileCopyUtils.copy(mfile.getInputStream(), new FileOutputStream(new File(totalPath)));

		String imgURL = "/upload/" + fileName;
		String responseJson = "{\"imUrl\":\"" + imgURL + "\"}";
		writer.write(responseJson);

//		return null;
	}

	@RequestMapping("findbyid")
//	@RequestMapping(value="findbyid", method = RequestMethod.POST)
//	@GetMapping(value= {"findbyid"})
	@ResponseBody
	public String findbyid() {
		// ������
		Expert expert = iExpertService.findByid(1);
		System.out.println("expert:" + expert);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		String str = sdf.format((Date)expert.getBirth());
//		System.out.println("birth:"+str);
		// ������
		String jsonString = "{\"msg\":\"hello world\"}";
		return jsonString;
	}

//	@RequestMapping("list")
//	public String list() {
//		List<Expert> experts = iExpertService.findAllExpert();
//		for (Expert expert : experts) {
//			System.out.println(expert);
//		}
//		return null;
//	}

	@RequestMapping("list")
	@ResponseBody
	public List list() {
		List<Expert> experts = iExpertService.findAllExpert();
		for (Expert expert : experts) {
			System.out.println(expert);
		}

		return experts;
	}

	@RequestMapping("memberLot")
	@ResponseBody
	public List memberlist(@RequestBody String idList) {

//		System.out.println("idList"+idList);

		List<Expert> extratResultInfoList = new ArrayList<>();

		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(idList);
		// System.out.println(a1.replaceAll(" "));
		String[] dis = matcher.replaceAll(" ").split("\\s+");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < dis.length; i++) {
			list.add(Integer.parseInt(dis[i]));
		}
		for (Integer integer : list) {
//			System.out.println("idList"+integer);
			extratResultInfoList.add(iExpertService.findByid(integer));
		}

		for (Expert expert : extratResultInfoList) {
			System.out.println("expert-----" + expert);
		}
		System.out.println("分割-----------");

		return extratResultInfoList;
	}

	@RequestMapping("save")
	@ResponseBody
	public String save(@RequestBody String expert) {
		Gson gson = new Gson();
		Expert expert2 = gson.fromJson(expert, Expert.class);
		if (expert2.getId() == -1) {
			expert2.setId(null);
		}

		System.out.println(expert2);

		iExpertService.saveOrUpdate(expert2);
		return "success";
	}

	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestBody String id) {
		System.out.println(id);

		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(id);
		id = m.replaceAll("").trim();
		int intid = Integer.parseInt(id);
		System.out.println(intid);
//		iSubjectService.deleteByid(id);
		return "success";
	}

	@RequestMapping("update")
	public String update(Expert expert) {
		iExpertService.saveOrUpdate(expert);
		return null;
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
	
	@RequestMapping("lotteryShow")
	@ResponseBody
	public List lotteryShow(@RequestParam("id") String id) {
//		System.out.println("idLot="+id);
		List<String> idLot = Arrays.asList(id.split(","));
		for (String string : idLot) {
			System.out.println("idLot="+string);
		}
		//所有学科组设置
		List<SubjectGroup> subjectGroups = iSubjectGroupService.findAllGroup();
		//所有学科
		List<Subject> subjects = iSubjectService.findAllSubject();
		//所有专家
		List<Expert> experts = iExpertService.findAllExpert();
		
		List<List<List<String>>> expertNameListss = new ArrayList<>();
		//结果
		List<List<String>> resultLists = new ArrayList<>();
		
		for (String idstr : idLot) {
			
		}
		
		return expertNameListss;
	}

}