package com.backoffice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backoffice.model.User;
import com.backoffice.service.IUserService;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	IUserService iUserService;

	@RequestMapping("findbyid")
	public String findbyid(Model model) {
		// ������
		User user = iUserService.findByid(1);
		System.out.println("user:" + user);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		String str = sdf.format((Date)user.getBirth());
//		System.out.println("birth:"+str);
		// ������
		model.addAttribute("user:", user);
		return "index";
	}
	
	@RequestMapping("list")
	public String list() {
		List<User> users = iUserService.findAllUser();
		for (User user : users) {
			System.out.println(user);
		}
		return null;
	}
	
//	@RequestMapping("save")
//	public String save(User users) {
//		iUserService.saveOrUpdate(users);
//		return null;
//	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save(@RequestBody User user) {
		List<User> users = iUserService.findAllUser();
		if(users.size() == 0) {
			user.setLevel(0);
		}

		System.out.println("user=="+user);
		
		iUserService.saveOrUpdate(user);
		return "success";
	}
	
	@RequestMapping("changePassword")
	@ResponseBody
	public String changPassword(@RequestParam("password") String password ,@RequestParam("email") String email) {
		System.out.println("password="+password);
		System.out.println("email="+email);
		
		List<User> users = iUserService.findAllUser();
		for (User user : users) {
			if(user.getEmail().equals(email)) {
				user.setPassword(password);
				iUserService.saveOrUpdate(user);
				return "success";
			}
		}
		
		return "fail";
	}
	
	@RequestMapping("check")
	@ResponseBody
	public String check(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		System.out.println("username=" + username);
		System.out.println("password=" + password);
		
		List<User> users = iUserService.findAllUser();
		
		for (User user : users) {
			System.out.println("user.getUsername()=="+user.getUsername()+"====user.getPassword()==="+user.getPassword());
			System.out.println("username=="+username+"====password==="+password);
			System.out.println("----------------------------------");
			if(user.getUsername().equals(username)&&user.getPassword().equals(password) || user.getEmail().equals(username)&&user.getPassword().equals(password)) {
				System.out.println("进入成功");
				return user.getLevel()+"";
			}
		}
		
		return "fail";
	}
	
	
	@RequestMapping("update")
	public String update(User users) {
		iUserService.saveOrUpdate(users);
		return null;
	}
	
	public String delete(int id) {
		iUserService.deleteByid(id);
		return null;
	}
}
