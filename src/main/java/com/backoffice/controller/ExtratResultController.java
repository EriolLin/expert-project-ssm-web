package com.backoffice.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backoffice.model.Expert;
import com.backoffice.model.ExtratResult;
import com.backoffice.model.SubjectGroup;
import com.backoffice.service.IAdminsetService;
import com.backoffice.service.IExpertService;
import com.backoffice.service.IExtratResultService;
import com.backoffice.service.ISubjectGroupCandidateService;
import com.backoffice.service.ISubjectGroupService;
import com.backoffice.service.ISubjectService;
import com.google.gson.Gson;

@Controller
@RequestMapping("extratResult")
public class ExtratResultController {

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

	@RequestMapping("downloadExcel")
	@ResponseBody
	public String downloadExcel(HttpServletRequest request, @RequestParam("year") String yearString) {

		System.out.println("yearString=" + yearString);

		String uploadFolder = request.getServletContext().getRealPath("/excel");
		System.out.println("uploadFolder:" + uploadFolder);
		File uploadFolderFile = new File(uploadFolder);
		if (!uploadFolderFile.exists()) {
			uploadFolderFile.mkdir();
		}

		System.out.println("downloadExcel进入成功");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		// 所有抽取结果
		List<ExtratResult> extratResults = iExtratResultService.findAllResult();
		// 指定年份的结果
		List<ExtratResult> currentYearExtratResults = new ArrayList<>();
		// 所有学科组的名单
		List<SubjectGroup> subjectGroups = iSubjectGroupService.findAllGroup();
		// 所有专家的集合
		List<Expert> experts = iExpertService.findAllExpert();

		for (ExtratResult extratResult : extratResults) {
			if (dateFormat.format(extratResult.getYear()).equals(yearString)) {
				currentYearExtratResults.add(extratResult);
			}

		}

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("图书档案表");
		HSSFRow hssfRow = sheet.createRow(0);
		HSSFCell headCell = hssfRow.createCell(0);
		headCell.setCellValue(yearString+"年学科组人员名单");
		// 设置单元格格式居中
		HSSFCellStyle cellStyle = workbook.createCellStyle();
//       cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 添加表头行
		hssfRow = sheet.createRow(1);
		// 添加表头内容
		headCell = hssfRow.createCell(0);
		headCell.setCellValue("组名");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(1);
		headCell.setCellValue("组长");
		headCell.setCellStyle(cellStyle);
		
		headCell = hssfRow.createCell(2);
		headCell.setCellValue("电话");
		headCell.setCellStyle(cellStyle);

		headCell = hssfRow.createCell(3);
		headCell.setCellValue("副组长");
		headCell.setCellStyle(cellStyle);
		
		headCell = hssfRow.createCell(4);
		headCell.setCellValue("电话");
		headCell.setCellStyle(cellStyle);
		
		for (int i = 5; i < 25; i++) {
			if(i%2 == 1) {
				headCell = hssfRow.createCell(i);
				headCell.setCellValue("组员");
				headCell.setCellStyle(cellStyle);
			}else {
				headCell = hssfRow.createCell(i);
				headCell.setCellValue("电话");
				headCell.setCellStyle(cellStyle);
			}
			
		}

		// 添加数据内容
		for (int i = 0; i < currentYearExtratResults.size(); i++) {
			hssfRow = sheet.createRow((int) i + 2);
			ExtratResult extratResult = currentYearExtratResults.get(i);

			// 创建单元格，并设置值
			HSSFCell cell = hssfRow.createCell(0);
			for (SubjectGroup subjectGroup : subjectGroups) {
				if (subjectGroup.getId() == extratResult.getSubjectId()) {
					cell.setCellValue(subjectGroup.getName());
					cell.setCellStyle(cellStyle);
					break;
				}
			}

			for (Expert expert : experts) {
				if (expert.getId() == Integer.parseInt(extratResult.getLeader())) {
					cell = hssfRow.createCell(1);
					cell.setCellValue(expert.getName());
					cell.setCellStyle(cellStyle);
					cell = hssfRow.createCell(2);
					cell.setCellValue(expert.getPhone());
					cell.setCellStyle(cellStyle);
				}
			}

			for (Expert expert : experts) {
				if (expert.getId() == Integer.parseInt(extratResult.getDeputyLeader())) {
					cell = hssfRow.createCell(3);
					cell.setCellValue(expert.getName());
					cell.setCellStyle(cellStyle);
					cell = hssfRow.createCell(4);
					cell.setCellValue(expert.getPhone());
					cell.setCellStyle(cellStyle);
				}
			}

			for (int j = 0; j < extratResult.getMember().split(",").length; j++) {
				

//    		   System.out.println("extratResult.getMember().split(\",\")[j-3]="+extratResult.getMember().split(",")[j-3]);
				for (Expert expert : experts) {
					if (expert.getId() == Integer.parseInt(extratResult.getMember().split(",")[j])) {
						cell = hssfRow.createCell(j + 5 + 2*j);
						cell.setCellValue(expert.getName());
						cell.setCellStyle(cellStyle);
						cell = hssfRow.createCell(j + 6 + 2*j);
						cell.setCellValue(expert.getPhone());
						cell.setCellStyle(cellStyle);
						break;
					}
				}
				

			}

		}
		// 保存Excel文件
		try {
			OutputStream outputStream = new FileOutputStream(uploadFolder + "\\bookinfo.xls");
			workbook.write(outputStream);
			outputStream.close();
			String excelURL = "/excel/" + "bookinfo.xls";
			String responseJson = "{\"excelURL\":\"" + excelURL + "\"}";
			return responseJson;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "导出失败";

	}

	@RequestMapping("findbyid")
	public String findbyid(Model model) {
		// ������
		ExtratResult extratResult = iExtratResultService.findByid(1);
		System.out.println("extratResult:" + extratResult);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		String str = sdf.format((Date)extratResult.getBirth());
//		System.out.println("birth:"+str);
		// ������
		model.addAttribute("extratResult", extratResult);
		return "index";
	}

	@RequestMapping("list")
	@ResponseBody
	public List list() {
		List<ExtratResult> extratResults = iExtratResultService.findAllResult();
		for (ExtratResult extratResult : extratResults) {
//			System.out.println(extratResult);
		}
		return extratResults;
	}

	@RequestMapping("listByYear")
	@ResponseBody
	public Map listByYear() {
		// 所有结果合集
		List<ExtratResult> extratResults = iExtratResultService.findAllResult();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		// map 年份-当年的结果
		Map<Integer, List<ExtratResult>> listByYearMap = new HashMap<Integer, List<ExtratResult>>();
		List<Integer> yearInts = new ArrayList<>();

		for (ExtratResult extratResult : extratResults) {
			String sqlYear = dateFormat.format(extratResult.getYear());
			yearInts.add(Integer.parseInt(sqlYear));
		}

		HashSet hs = new HashSet(yearInts);
		yearInts.clear();
		yearInts.addAll(hs);

		Collections.sort(yearInts);
		Collections.reverse(yearInts);

		for (Integer integer : yearInts) {
			System.out.println("yearInt=" + integer);
			List<ExtratResult> thisYearExtratResult = new ArrayList<ExtratResult>();
			for (ExtratResult extratResult : extratResults) {
				String sqlYear = dateFormat.format(extratResult.getYear());
				if (integer == Integer.parseInt(sqlYear)) {
					thisYearExtratResult.add(extratResult);
				}
			}
			if (thisYearExtratResult != null) {
				listByYearMap.put(integer, thisYearExtratResult);
			}
		}

		for (Integer key : listByYearMap.keySet()) {

			System.out.println("key=" + key);

		}
		return listByYearMap;
	}

	@RequestMapping("currentYearList")
	@ResponseBody
	public List currentYearList() {
		Date date = new Date();
//		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		String currentYear = dateFormat.format(date);
		System.out.println("currentYear=" + currentYear);

		List<ExtratResult> extratResults = iExtratResultService.findAllResult();

		for (int i = 0; i < extratResults.size(); i++) {
			String sqlyear = dateFormat.format(extratResults.get(i).getYear());
//			System.out.println("sqlyear="+sqlyear);
			if (!sqlyear.equals(currentYear)) {
				extratResults.remove(i);
				i--;
			}
		}

//		for (ExtratResult extratResult : extratResults) {
//			String sqlyear = dateFormat.format(extratResult.getYear());
////			System.out.println("sqlyear="+sqlyear);
//			if(!sqlyear.equals(currentYear)) {
//				extratResults.remove(extratResult);
//			}
//		}

		return extratResults;
	}

	@RequestMapping("save")
	@ResponseBody
	public String save(@RequestBody ExtratResult extratResult) {
		System.out.println("extratResult==" + extratResult);
//		Gson gson = new Gson();
//		ExtratResult extratResult2 = new ExtratResult();

//		Date date = new Date();
//		Timestamp timeStamp = new Timestamp(date.getTime());

//		extratResult2.setYear(timeStamp);

		iExtratResultService.saveOrUpdate(extratResult);
		return null;
	}

	@RequestMapping("update")
	public String update(ExtratResult extratResult) {
		iExtratResultService.saveOrUpdate(extratResult);
		return null;
	}

	@RequestMapping("delete")
	@ResponseBody
	public String delete(@RequestBody String id) {

		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(id);
		Integer SGid = Integer.parseInt(m.replaceAll("").trim());

		System.out.println("id====" + SGid);
//		iExtratResultService.deleteByid(id);
		return "success";
	}

	@RequestMapping("deleteLot")
	@ResponseBody
	public String deleteLot(@RequestBody String id) {

		String a = id;
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher a1 = pattern.matcher(a);
		// System.out.println(a1.replaceAll(" "));
		String[] dis = a1.replaceAll(" ").split("\\s+");
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < dis.length; i++) {
			// System.out.println(dis[i]);
			list.add(Integer.parseInt(dis[i]));
		}
		for (Integer list2 : list) {
			System.out.println(list2);
		}

//		System.out.println("deleteLot==="+id);

		return "success";
	}

}
