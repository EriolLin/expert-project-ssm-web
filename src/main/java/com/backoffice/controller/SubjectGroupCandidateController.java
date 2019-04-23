package com.backoffice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.sql.parser.Lexer.SavePoint;
import com.backoffice.model.Subject;
import com.backoffice.model.SubjectGroupCandidate;
import com.backoffice.service.ISubjectGroupCandidateService;
import com.backoffice.service.ISubjectService;
import com.mysql.fabric.xmlrpc.base.Array;

@Controller
@RequestMapping("subjectGroupCandidate")
public class SubjectGroupCandidateController {
	@Autowired
	ISubjectGroupCandidateService iSubjectGroupCandidateService;
	@Autowired
	ISubjectService iSubjectService;

	@RequestMapping("list")
	public List list() {

		List<SubjectGroupCandidate> subjectGroupCandidates = iSubjectGroupCandidateService
				.findAllSubjectGroupCandidate();
		return subjectGroupCandidates;
	}

	@RequestMapping("save")
	@ResponseBody
	public String Save(@RequestParam("data") String subjectGroupCandidateDemo) {
		System.out.println("subjectGroupCandidateDemo=" + subjectGroupCandidateDemo);
		String[] subjectGroupCandidateDemoSplit = subjectGroupCandidateDemo.split(":");
		List<Subject> subjects = iSubjectService.findAllSubject();
		List<SubjectGroupCandidate> subjectGroupCandidates = iSubjectGroupCandidateService
				.findAllSubjectGroupCandidate();
		SubjectGroupCandidate ResultSubjectGroupCandidate = new SubjectGroupCandidate();

		for (Subject subject : subjects) {
			if (subject.getSubjectName().equals(subjectGroupCandidateDemoSplit[0])) {
				ResultSubjectGroupCandidate.setSubjectid(subject.getId());
				for (SubjectGroupCandidate subjectGroupCandidate : subjectGroupCandidates) {
					ResultSubjectGroupCandidate.setId(null);
					if (subjectGroupCandidate.getSubjectid() == subject.getId()) {
						ResultSubjectGroupCandidate.setId(subjectGroupCandidate.getId());
						break;
					}
				}

				break;
			}
		}
		ResultSubjectGroupCandidate.setCandidateid(subjectGroupCandidateDemoSplit[1]);

		System.out.println("ResultSubjectGroupCandidate=" + ResultSubjectGroupCandidate);

		iSubjectGroupCandidateService.saveOrUpdate(ResultSubjectGroupCandidate);

		return null;
	}
}
