package com.backoffice.controller;

import java.io.Console;
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.backoffice.model.Adminset;
import com.backoffice.model.Expert;
import com.backoffice.model.ExtratResult;
import com.backoffice.model.Subject;
import com.backoffice.model.SubjectGroup;
import com.backoffice.model.SubjectGroupCandidate;
import com.backoffice.model.SubjectGroupExpertOperate;
import com.backoffice.service.IAdminsetService;
import com.backoffice.service.IExpertService;
import com.backoffice.service.IExtratResultService;
import com.backoffice.service.ISubjectGroupCandidateService;
import com.backoffice.service.ISubjectGroupExpertOperateService;
import com.backoffice.service.ISubjectGroupService;
import com.backoffice.service.ISubjectService;
import com.google.gson.Gson;

@Controller
@RequestMapping("lottery")
public class LotteryController {

	@Autowired
	IExpertService iExpertService;
	@Autowired
	ISubjectService iSubjectService;
	@Autowired
	ISubjectGroupService iSubjectGroupService;
	@Autowired
	IExtratResultService iExtratResultService;
	@Autowired
	ISubjectGroupCandidateService iSubjectGroupCandidateService;
	@Autowired
	IAdminsetService iAdminsetService;
	@Autowired
	ISubjectGroupExpertOperateService ISubjectGroupExpertOperateService;
	
	@RequestMapping("getResultByid")
	@ResponseBody
	public List resultListByid(@RequestBody String id) {
		//管理员设置
		Adminset adminset = iAdminsetService.findByid(1);
		boolean priorityFlag = true;
		//判断是否开启优先级
		if(!adminset.getPriority().equals("1")) {
			priorityFlag = false;
		}
		
		System.out.println("priorityFlag========="+priorityFlag);

		System.out.println("id=="+id);
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher a1 = pattern.matcher(id);
		// System.out.println(a1.replaceAll(" "));
		String[] dis = a1.replaceAll(" ").split("\\s+");
		List<Integer> idLot = new ArrayList<Integer>();
		for (int i = 1; i < dis.length; i++) {
			// System.out.println(dis[i]);
			idLot.add(Integer.parseInt(dis[i]));
		}
		List<Map<Integer, List<Expert>>> expertMemberLot = new ArrayList<>();
		
		//取年份
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		
		List<String> expertMemberLotStr = new ArrayList<>();
		
		//学科组人员操控
		List<SubjectGroupExpertOperate> subjectGroupExpertOperates = ISubjectGroupExpertOperateService.findAllSubjectGroupExpertOperate();
		
		for (SubjectGroupExpertOperate subjectGroupExpertOperate2 : subjectGroupExpertOperates) {
			System.out.println("subjectGroupExpertOperate2="+subjectGroupExpertOperate2);
		}
		
		// 所有专家列表 外
		List<Expert> experts = iExpertService.findAllExpert();
		List<Expert> expertAll = iExpertService.findAllExpert();
		
		//去除状态设置未禁用，组长，组员的专家
		Iterator<Expert> expertsIterator = experts.iterator();
		while(expertsIterator.hasNext()){
		    Expert x = expertsIterator.next();
		    
		    for (SubjectGroupExpertOperate subjectGroupExpertOperate : subjectGroupExpertOperates) {
				if(x.getId() == subjectGroupExpertOperate.getExpertid()) {
					expertsIterator.remove();
					break;
				}
			}
		}
		
		for (Expert expert : experts) {
			System.out.println("expertListDemo===" + expert);
		}
		//所有学科
		List<Subject> subjects = iSubjectService.findAllSubject();
		//每个学科的组长候选人
		List<SubjectGroupCandidate> subjectGroupCandidates = iSubjectGroupCandidateService.findAllSubjectGroupCandidate();
		//结果集--得到被选的专家--得到已抽取的学科组id
		List<ExtratResult> extratResults = iExtratResultService.findAllResult();
		//已抽取的学科组id
		List<Integer> ided = new ArrayList<>();
		
		for (ExtratResult extratResult : extratResults) {
			if(dateFormat.format(extratResult.getYear()).equals(dateFormat.format(new Date()))) {
				ided.add(extratResult.getSubjectId());
			}
		}
		
		{
			HashSet h1 = new HashSet(idLot);
			HashSet h2 = new HashSet(ided);
			h1.removeAll(h2);
			idLot.clear();
			idLot.addAll(h1);
			for (Integer integer : idLot) {
				System.out.println("integeridLot="+integer);
			}
			for (Integer integer : ided) {
				System.out.println("integerided="+integer);
			}
		}
		
		
		
		
		List<Integer> extratResultsids = new ArrayList<Integer>();
		for (ExtratResult extratResult : extratResults) {
			for (String string : extratResult.getMember().split(",")) {
				extratResultsids.add(Integer.parseInt(string));
			}
		}
		
		for (int i = 0; i < experts.size(); i++) {
			for (Integer integer : extratResultsids) {
				if(experts.get(i).getId() == integer) {
					experts.remove(i);
				}
			}
		}
		
		

		// 组长候选人 外
		List<Expert> expertCandidates = new ArrayList<>();
		for (Expert expert : experts) {
			if (expert.getCandidate().equals("1")) {
				expertCandidates.add(expert);
			}
		}
		
		
		for (Integer idOne : idLot) {
			System.out.println("idOne--" + idOne);
		
			

//        int SGid = Integer.parseInt(id); 
			// 学科组成员
			List<Expert> expertMember = new ArrayList<>();

			// 通过id查找一组学科组的学科
			List<String> subjectById = new ArrayList<>();
			// 通过id查找一组学科组的人数
			List<Integer> expertNumById = new ArrayList<>();
//        List<SubjectGroup> subjectGroups = iSubjectGroupService.findAllGroup();

			// 学科组列表
			SubjectGroup subjectGroup = iSubjectGroupService.findByid(idOne);
			subjectById = Arrays.asList(subjectGroup.getSubSubject().split(","));

			String[] expertNumByIdString = subjectGroup.getExpertNum().split(",");
			for (String string : expertNumByIdString) {
				expertNumById.add(Integer.parseInt(string));
			}

			

			for (Expert expert : expertCandidates) {
				System.out.println("expert==" + expert);
			}

			for (String subject : subjectById) {
				System.out.println("subjectById===" + subject);
			}

			// 符合该学科组条件的组长候选人
			List<Expert> expertCandidatesAtSubject = new ArrayList<>();
//			for (String subjectString : subjectById) {
//				for (Expert expert : expertCandidates) {
////        		System.out.println("expert.getSubject() ==========="+expert.getSubject()+"=========subjectString=========="+subjectString);
//					if (expert.getSubject().contains(subjectString) == true) {
//						expertCandidatesAtSubject.add(expert);
//					}
//				}
//			}
			
			List<String> expertCandidateIds = new ArrayList<>();
			for (String subjectString : subjectById) {
				for (Subject subject : subjects) {
					if(subject.getSubjectName().equals(subjectString)) {
						for (SubjectGroupCandidate subjectGroupCandidate : subjectGroupCandidates) {
							if(subjectGroupCandidate.getSubjectid() == subject.getId()) {
								expertCandidateIds.addAll(Arrays.asList(subjectGroupCandidate.getCandidateid().split(",")));
							}
							
						}
						
					}
				}
			}
			
			HashSet h = new HashSet(expertCandidateIds);   
			expertCandidateIds.clear();   
			expertCandidateIds.addAll(h);
			
			for (Expert expert : experts) {
				for (String idStr : expertCandidateIds) {
					if(expert.getId() == Integer.parseInt(idStr)) {
						expertCandidatesAtSubject.add(expert);
						break;
					}
				}
			}
			
			
			
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			for (String string : expertCandidateIds) {
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!expertCandidateIds="+string);
			}
			
			

			int totalNum = 0;
			for (Expert expert : expertCandidatesAtSubject) {
//			System.out.println(expert);
//			System.out.println("expertPriority" + iExpertService.getPriority(expert.getId()));
				if(priorityFlag) {
					totalNum += iExpertService.getPriority(expert.getId());					
				}else {
					totalNum += iExpertService.getPriorityNor(expert.getId());	
				}
			}

			double flagNum = Math.random() * totalNum;
			// 抽取的队长
			for (SubjectGroupExpertOperate subjectGroupExpertOperate : subjectGroupExpertOperates) {
				if(idOne == subjectGroupExpertOperate.getSubjectgroupid()) {
					if(subjectGroupExpertOperate.getStatus().equals("2")) {
						for (Expert expert : expertAll) {
							if(subjectGroupExpertOperate.getExpertid() == expert.getId()) {
								expertMember.add(expert);
								break;
							}
						}
						
					}
					break;
				}
				
			}
			if(expertMember!=null && expertMember.isEmpty()) {
				System.out.println("从subjectGroupExpertOperate中取得了队长");
			}else {
				for (Expert expert : expertCandidatesAtSubject) {
					if(priorityFlag) {
						flagNum -= iExpertService.getPriority(expert.getId());
					}else {
						flagNum -= iExpertService.getPriorityNor(expert.getId());
					}
					
					if (flagNum <= 0.0) {
						expertMember.add(expert);
						break;
					}
				}
			}

			// 找出组长所占哪一个学科名额
			String subjectLeader = "";
			for (String subjectString : subjectById) {
				if (expertMember.get(0).getSubject().contains(subjectString)) {
					subjectLeader = subjectString;
					break;
				}
			}

			System.out.println("组长所占学科的名额" + subjectLeader);

			// 减少队长所占学科名额
			for (int i = 0; i < subjectById.size(); i++) {
				if (subjectById.get(i).equals(subjectLeader)) {
					expertNumById.set(i, expertNumById.get(i) - 1);
				}
			}

			for (int subjectNum : expertNumById) {
				System.out.println("subjectNum==" + subjectNum);
			}

			System.out.println("resultExpertLeader===" + expertMember.get(0));

			// 移除专家中的组长，以免妨碍之后的抽取
			for (int i = 0; i < experts.size(); i++) {
				if (expertMember.get(0).getId() == experts.get(i).getId()) {
					experts.remove(i);
					break;
				}
			}

//		for (Expert expert : experts) {
//			System.out.println("experts========="+expert);
//		}

			// 抽取符合学科组要求的组员
			List<Expert> subjectGroupteamateDemo = new ArrayList<>();
			for (String subjectString : subjectById) {
				for (Expert expert : experts) {
//        		System.out.println("expert的id====="+expert.getId()+"=====expert.getSubject() ==========="+expert.getSubject()+"=========subjectString=========="+subjectString);
					if (expert.getSubject().contains(subjectString) == true) {
//					System.out.println("成功匹配");
						subjectGroupteamateDemo.add(expert);
					}
				}
			}

//		for (Expert expert : subjectGroupteamateDemo) {
//			System.out.println("subjectGroupteamateDemo==="+expert);
//		}
			
			for (SubjectGroupExpertOperate subjectGroupExpertOperate : subjectGroupExpertOperates) {
				if(idOne == subjectGroupExpertOperate.getSubjectgroupid()) {
					if(subjectGroupExpertOperate.getStatus().equals("3")) {
						for (Expert expert : expertAll) {
							if(subjectGroupExpertOperate.getExpertid() == expert.getId()) {
								expertMember.add(expert);
								break;
							}
						}
					}
					break;
				}
				
			}
			
			for (int i = 1; i < expertMember.size(); i++) {
				// 找出队员所占哪一个学科名额
				String subjectMember = "";
				for (String subjectString : subjectById) {
					if (expertMember.get(i).getSubject().contains(subjectString)) {
						subjectMember = subjectString;
						break;
					}
				}
				
				// 减少队员所占学科名额
				for (int j = 0; j < subjectById.size(); j++) {
					if (subjectById.get(j).equals(subjectMember)) {
						expertNumById.set(j, expertNumById.get(j) - 1);
					}
				}
			}
			
			// 抽取正式的组员
			System.out.println("expertNumById.size()=======" + expertNumById.size());

			for (int i = 0; i < expertNumById.size(); i++) {
				for (int j = 0; j < expertNumById.get(i); j++) {
					totalNum = 0;
					System.out.println("expertNumById.get(i)-------------" + expertNumById.get(i));

					List<Expert> expertTeamate = new ArrayList<>();

					// 满足第i个学科的专家
					for (Expert expert : subjectGroupteamateDemo) {
//					System.out.println("expert.getSubject()----------"+expert.getSubject()+"--------subjectById.get(i)--------"+subjectById.get(i));

						if (expert.getSubject().contains(subjectById.get(i))) {
//						System.out.println("添加成功");
							expertTeamate.add(expert);
//						totalNum += iExpertService.getPriority(expert.getId());
						}
					}

					HashSet h1 = new HashSet(expertTeamate);
					expertTeamate.clear();
					expertTeamate.addAll(h1);

					System.out.println("====================================================");
					System.out.println("expertTeamate-----" + expertTeamate);

					double flagNum2 = Math.random() * totalNum;

					for (Expert expert : expertTeamate) {
						if(priorityFlag) {
							flagNum2 -= iExpertService.getPriority(expert.getId());
						}else {
							flagNum2 -= iExpertService.getPriorityNor(expert.getId());
						}
						
						if (flagNum2 <= 0.0) {
							expertMember.add(expert);

							// 移除符合学科组的专家组中的此成员，以免妨碍之后的抽取
							for (int k = 0; k < subjectGroupteamateDemo.size(); k++) {
								if (expert.getId() == subjectGroupteamateDemo.get(k).getId()) {
									subjectGroupteamateDemo.remove(k);
								}
							}
							
							//	移除专家组中的此成员，以免妨碍之后的抽取
							for (int k = 0; k < experts.size(); k++) {
								if (expert.getId() == experts.get(k).getId()) {
									experts.remove(k);
								}
							}

							break;
						}
					}

				}
			}

			for (Expert expert : expertMember) {
				System.out.println("expertMember" + expert);
			}
			
			Map<Integer, List<Expert>> map = new HashMap<Integer, List<Expert>>();
			map.put(idOne, expertMember);
			Gson gson = new Gson();
			String json=gson.toJson(map);
			System.out.println("mapkey~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"key:"+idOne+","+"value:"+gson.toJson(expertMember));
			System.out.println("jsonMap~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+json);
			expertMemberLot.add(map);
			expertMemberLotStr.add("key:"+idOne+","+"value:"+gson.toJson(expertMember));
		}
		
		System.out.println("expertMemberLot.size"+expertMemberLot.size());
//		System.out.println(expertMemberLot.get(0).get(8).get(0));
		
//		return expertMemberLot;
		
		return expertMemberLot;
	
	}
	
	
	
	
	
	
	
//	@RequestMapping("getResultByid")
//	@ResponseBody
//	public List resultListByid(@RequestBody String id) {
//
//		System.out.println("id=="+id);
//		Pattern pattern = Pattern.compile("[^0-9]");
//		Matcher a1 = pattern.matcher(id);
//		// System.out.println(a1.replaceAll(" "));
//		String[] dis = a1.replaceAll(" ").split("\\s+");
//		List<Integer> idLot = new ArrayList<Integer>();
//		for (int i = 1; i < dis.length; i++) {
//			// System.out.println(dis[i]);
//			idLot.add(Integer.parseInt(dis[i]));
//		}
//		List<Map<Integer, List<Expert>>> expertMemberLot = new ArrayList<>();
//		
//		List<String> expertMemberLotStr = new ArrayList<>();
//		// 所有专家列表 外
////      List<Expert> expertListDemo = iExpertService.findAllExpert();
//		List<Expert> experts = iExpertService.findAllExpert();
//		for (Expert expert : experts) {
//			System.out.println("expertListDemo===" + expert);
//		}
//		
//		//结果集--得到被选的专家
//		List<ExtratResult> extratResults = new ArrayList<>();
//		List<Integer> extratResultsids = new ArrayList<Integer>();
//		for (ExtratResult extratResult : extratResults) {
//			for (String string : extratResult.getMember().split(",")) {
//				extratResultsids.add(Integer.parseInt(string));
//			}
//		}
//		
//		for (int i = 0; i < experts.size(); i++) {
//			for (Integer integer : extratResultsids) {
//				if(experts.get(i).getId() == integer) {
//					experts.remove(i);
//				}
//			}
//		}
//
//		// 组长候选人 外
//		List<Expert> expertCandidates = new ArrayList<>();
//		for (Expert expert : experts) {
//			if (expert.getCandidate().equals("1")) {
//				expertCandidates.add(expert);
//			}
//		}
//		
//		
//		for (Integer idOne : idLot) {
//			System.out.println("idOne--" + idOne);
//		
//			
//
////        int SGid = Integer.parseInt(id); 
//			// 学科组成员
//			List<Expert> expertMember = new ArrayList<>();
//
//			// 通过id查找一组学科组的学科
//			List<String> subjectById = new ArrayList<>();
//			// 通过id查找一组学科组的人数
//			List<Integer> expertNumById = new ArrayList<>();
////        List<SubjectGroup> subjectGroups = iSubjectGroupService.findAllGroup();
//
//			// 学科组列表
//			SubjectGroup subjectGroup = iSubjectGroupService.findByid(idOne);
//			subjectById = Arrays.asList(subjectGroup.getSubSubject().split(","));
//
//			String[] expertNumByIdString = subjectGroup.getExpertNum().split(",");
//			for (String string : expertNumByIdString) {
//				expertNumById.add(Integer.parseInt(string));
//			}
//
//			
//
//			for (Expert expert : expertCandidates) {
//				System.out.println("expert==" + expert);
//			}
//
//			for (String subject : subjectById) {
//				System.out.println("subjectById===" + subject);
//			}
//
//			// 符合该学科组条件的组长候选人
//			List<Expert> expertCandidatesAtSubject = new ArrayList<>();
//			for (String subjectString : subjectById) {
//				for (Expert expert : expertCandidates) {
////        		System.out.println("expert.getSubject() ==========="+expert.getSubject()+"=========subjectString=========="+subjectString);
//					if (expert.getSubject().contains(subjectString) == true) {
//						expertCandidatesAtSubject.add(expert);
//					}
//				}
//			}
//
//			int totalNum = 0;
//			for (Expert expert : expertCandidatesAtSubject) {
////			System.out.println(expert);
////			System.out.println("expertPriority" + iExpertService.getPriority(expert.getId()));
//				totalNum += iExpertService.getPriority(expert.getId());
//			}
//
//			double flagNum = Math.random() * totalNum;
//			// 抽取的队长
//
//			for (Expert expert : expertCandidatesAtSubject) {
//				flagNum -= iExpertService.getPriority(expert.getId());
//				if (flagNum <= 0.0) {
//					expertMember.add(expert);
//					break;
//				}
//			}
//
//			// 找出组长所占哪一个学科名额
//			String subjectLeader = "";
//			for (String subjectString : subjectById) {
//				if (expertMember.get(0).getSubject().contains(subjectString)) {
//					subjectLeader = subjectString;
//					break;
//				}
//			}
//
//			System.out.println("组长所占学科的名额" + subjectLeader);
//
//			// 减少队长所占学科名额
//			for (int i = 0; i < subjectById.size(); i++) {
//				if (subjectById.get(i).equals(subjectLeader)) {
//					expertNumById.set(i, expertNumById.get(i) - 1);
//				}
//			}
//
//			for (int subjectNum : expertNumById) {
//				System.out.println("subjectNum==" + subjectNum);
//			}
//
//			System.out.println("resultExpertLeader===" + expertMember.get(0));
//
//			// 移除专家中的组长，以免妨碍之后的抽取
//			for (int i = 0; i < experts.size(); i++) {
//				if (expertMember.get(0).getId() == experts.get(i).getId()) {
//					experts.remove(i);
//				}
//			}
//
////		for (Expert expert : experts) {
////			System.out.println("experts========="+expert);
////		}
//
//			// 抽取符合学科组要求的组员
//			List<Expert> subjectGroupteamateDemo = new ArrayList<>();
//			for (String subjectString : subjectById) {
//				for (Expert expert : experts) {
////        		System.out.println("expert的id====="+expert.getId()+"=====expert.getSubject() ==========="+expert.getSubject()+"=========subjectString=========="+subjectString);
//					if (expert.getSubject().contains(subjectString) == true) {
////					System.out.println("成功匹配");
//						subjectGroupteamateDemo.add(expert);
//					}
//				}
//			}
//
////		for (Expert expert : subjectGroupteamateDemo) {
////			System.out.println("subjectGroupteamateDemo==="+expert);
////		}
//
//			// 抽取正式的组员
//
//			System.out.println("expertNumById.size()=======" + expertNumById.size());
//
//			for (int i = 0; i < expertNumById.size(); i++) {
//				for (int j = 0; j < expertNumById.get(i); j++) {
//					totalNum = 0;
//					System.out.println("expertNumById.get(i)-------------" + expertNumById.get(i));
//
//					List<Expert> expertTeamate = new ArrayList<>();
//
//					// 满足第i个学科的专家
//					for (Expert expert : subjectGroupteamateDemo) {
////					System.out.println("expert.getSubject()----------"+expert.getSubject()+"--------subjectById.get(i)--------"+subjectById.get(i));
//
//						if (expert.getSubject().contains(subjectById.get(i))) {
////						System.out.println("添加成功");
//							expertTeamate.add(expert);
////						totalNum += iExpertService.getPriority(expert.getId());
//						}
//					}
//
//					HashSet h = new HashSet(expertTeamate);
//					expertTeamate.clear();
//					expertTeamate.addAll(h);
//
//					System.out.println("====================================================");
//					System.out.println("expertTeamate-----" + expertTeamate);
//
//					double flagNum2 = Math.random() * totalNum;
//
//					for (Expert expert : expertTeamate) {
//						flagNum2 -= iExpertService.getPriority(expert.getId());
//						if (flagNum2 <= 0.0) {
//							expertMember.add(expert);
//
//							// 移除符合学科组的专家组中的此成员，以免妨碍之后的抽取
//							for (int k = 0; k < subjectGroupteamateDemo.size(); k++) {
//								if (expert.getId() == subjectGroupteamateDemo.get(k).getId()) {
//									subjectGroupteamateDemo.remove(k);
//								}
//							}
//							
//							//	移除专家组中的此成员，以免妨碍之后的抽取
//							for (int k = 0; k < experts.size(); k++) {
//								if (expert.getId() == experts.get(k).getId()) {
//									experts.remove(k);
//								}
//							}
//
//							break;
//						}
//					}
//
//				}
//			}
//
//			for (Expert expert : expertMember) {
//				System.out.println("expertMember" + expert);
//			}
//			
//			Map<Integer, List<Expert>> map = new HashMap<Integer, List<Expert>>();
//			map.put(idOne, expertMember);
//			Gson gson = new Gson();
//			String json=gson.toJson(map);
//			System.out.println("mapkey~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"key:"+idOne+","+"value:"+gson.toJson(expertMember));
//			System.out.println("jsonMap~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+json);
//			expertMemberLot.add(map);
//			expertMemberLotStr.add("key:"+idOne+","+"value:"+gson.toJson(expertMember));
//		}
//		
//		System.out.println("expertMemberLot.size"+expertMemberLot.size());
////		System.out.println(expertMemberLot.get(0).get(8).get(0));
//		
////		return expertMemberLot;
//			return expertMemberLot;
//	}
}