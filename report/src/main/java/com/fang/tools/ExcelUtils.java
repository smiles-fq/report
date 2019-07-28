package com.fang.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	public static String createExcel(String fileName, Object[] title, Object[][] data) {
		try {
			String projectPath = System.getProperty("user.dir");
			String file = projectPath + "/uploadFile/" + fileName + System.currentTimeMillis() + ".csv";
			FileWriter writer = new FileWriter(file);
			CSVPrinter printer = CSVFormat.EXCEL.print(writer);
			printer.printRecord(title);
			if (data != null && data.length > 0) {
				for (int i = 0; i < data.length; i++) {
					printer.printRecord(data[i]);
				}
			}
			printer.flush();
			printer.close();
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();
			int index = 0;
			if (title != null && title.length > 0) {
				XSSFRow row = sheet.createRow(index++);
				for (int i = 0; i < title.length; i++) {
					XSSFCell cell = row.createCell(i);
					cell.setCellValue(String.valueOf(title[i]));
				}
			}

			if (data != null && data.length > 0) {
				for (int i = 0; i < data.length; i++) {
					XSSFRow row = sheet.createRow(index++);
					if (title != null && title.length > 0) {
						for (int j = 0; j < title.length; j++) {
							XSSFCell cell = row.createCell(j);
							cell.setCellValue(String.valueOf(data[i][j]));
						}
					}
				}
			}
			FileOutputStream fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.flush();
			fos.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void downLoad(String filePath, HttpServletResponse response, boolean isOnLine) {
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				response.sendError(404, "File not found!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
			byte[] buf = new byte[1024];
			int len = 0;

			response.reset(); // 非常重要
			if (isOnLine) { // 在线打开方式
				URL u = new URL("file:///" + filePath);
				response.setContentType(u.openConnection().getContentType());
				response.setHeader("Content-Disposition", "inline; filename=" + f.getName());
				// 文件名应该编码成UTF-8
			} else { // 纯下载方式
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
			}
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
