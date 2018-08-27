package com.together.knowledge.base.drools.excel;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 操作Excel 工具类
 * 
 * @author hanyd
 *
 */
public class ExcelUtils {
	/**
	 * 根据fileType不同读取excel文件
	 *
	 * @param path
	 * @param path
	 * @throws IOException
	 */
	public static List<Map<String,String>> readExcel(String path) {
		String fileType = path.substring(path.lastIndexOf(".") + 1);
		// return a list contains many list
		List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		//读取excel文件
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			//获取工作薄
			Workbook wb = null;
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook(is);
			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook(is);
			} else {
				return null;
			}
			//读取第一个工作页sheet
			Sheet sheet = wb.getSheetAt(0);
			//第一行为标题
			int i = 0;
			List<String> header = new ArrayList<>();
			for (Row row : sheet) {
				ArrayList<String> list = new ArrayList<String>();
				Map<String,String> map = new HashMap<String,String>();
				for (Cell cell : row) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(i == 0){//标题
						header.add(cell.getStringCellValue());
					}else{
						//根据不同类型转化成字符串
						list.add(cell.getStringCellValue());
						map.put(header.get(cell.getColumnIndex()),cell.getStringCellValue());
					}
				}
				lists.add(map);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lists;
	}
}
