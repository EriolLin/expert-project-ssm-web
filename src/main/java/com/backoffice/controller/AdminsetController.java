package com.backoffice.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.backoffice.model.Adminset;
import com.backoffice.service.IAdminsetService;
import com.google.gson.Gson;

@Controller
@RequestMapping("adminset")
public class AdminsetController {
	
	@Autowired
	IAdminsetService iAdminsetService;

	@RequestMapping("findbyid")
	@ResponseBody
	public String findbyid() {

		Adminset adminset = iAdminsetService.findByid(1);
		
		Gson gson = new Gson();
		String adminStr = gson.toJson(adminset);
		return adminStr;
	}
	
	@RequestMapping("logimg")
	@ResponseBody
	public String logimg() {

		Adminset adminset = iAdminsetService.findByid(1);
		
		
		
		return adminset.getLogimg();
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String Save(@RequestBody Adminset adminset) {
		Adminset adminsetnow = iAdminsetService.findByid(1);
		System.out.println("adminsetnow="+adminsetnow);
		if(adminsetnow == null) {
			iAdminsetService.saveOrUpdate(adminset);
		}else {
			adminset.setId(1);
			System.out.println(adminset);
			iAdminsetService.saveOrUpdate(adminset);
		}

		return null;
	}
	
	@RequestMapping("/upload")
	public void uploadlogo(HttpServletRequest request, PrintWriter writer) throws Exception {
		
		MultipartHttpServletRequest mutliRequest = (MultipartHttpServletRequest) request;
		MultipartFile mfile = mutliRequest.getFile("picture");
		
		String uploadFolder = request.getServletContext().getRealPath("/logo");	
		System.out.println("uploadFolder:"+uploadFolder);
		File uploadFolderFile = new File(uploadFolder);
		if(!uploadFolderFile.exists()) {
			uploadFolderFile.mkdir();
		}
		
		//2.文件
		String nameString = mfile.getOriginalFilename();
		String suffix = mfile.getOriginalFilename().split("\\.")[1];
		String fileName = UUID.randomUUID().toString()+"."+suffix;
		String totalPath = uploadFolder + "\\" +fileName;
		System.out.println("totalPath:"+totalPath);
		FileCopyUtils.copy(mfile.getInputStream(), new FileOutputStream(new File(totalPath)));
		
		String imgURL = "/logo/"+fileName;
		String responseJson = "{\"imUrl\":\""+imgURL+"\"}";
		writer.write(responseJson);
		
//		return null;
	}

//	@RequestMapping("save")
//	@ResponseBody
//	public String save(@RequestBody Adminset adminset) {
//		return null;
//		
//	}
	
	
}
