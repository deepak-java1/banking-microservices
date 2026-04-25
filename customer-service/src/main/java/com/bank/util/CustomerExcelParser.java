package com.bank.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bank.entity.Customer;
@Component
public class CustomerExcelParser {


	public List<Customer> parseExcel(MultipartFile file) throws IOException{
		
		List<Customer> list = new ArrayList<>();
		
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		Sheet sheet = workbook.getSheetAt(0);
		
		for(Row row : sheet) {
			if(row.getRowNum() == 0) 
				continue;
			
			Customer c = new Customer();
			c.setFirstName(row.getCell(0).getStringCellValue());
			c.setLastName(row.getCell(1).getStringCellValue());
			c.setEmail(row.getCell(2).getStringCellValue());
			c.setMobile(row.getCell(3).getStringCellValue());
			c.setAddress(row.getCell(4).getStringCellValue());
			c.setCity(row.getCell(5).getStringCellValue());
			c.setStatus(row.getCell(6).getStringCellValue());
			
			list.add(c);
		}
		return list;
		
	}
}
