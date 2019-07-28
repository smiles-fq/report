package com.fang.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fang.tools.DataUtils;
import com.fang.tools.ExcelUtils;

@Controller
@RequestMapping("/report")
public class ReportController {

	@RequestMapping("/select")
	public String select() {
		return "report";
	}

	@RequestMapping("/selectData")
	@ResponseBody
	public Map<String, Object> selectData(String sql) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String[][] data = DataUtils.getData(sql);

			map.put("info", data);

			String[] dataColumn = DataUtils.getDataColumn(sql);
			for (int i = 0; i < data.length; i++) {
				System.out.println(data[i][0]);
			}
			map.put("title", dataColumn);
			String fileName = "excel";
			String filePath = ExcelUtils.createExcel(fileName, dataColumn, data);
			map.put("filePath", filePath);
			map.put("status", "ok");
		} catch (Exception e) {
			map.put("status", "error");
		}

		return map;
	}

	@RequestMapping("/downLoad")
	public void downLoad(String filePath, HttpServletRequest request, HttpServletResponse response) {
		ExcelUtils.downLoad(filePath, response, true);
	}

}
