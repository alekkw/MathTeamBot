package sheets;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

public class PointsSheet extends Sheet{
	
	public PointsSheet(String fileName) {
		super(fileName);
	}
	
	public int getPointsByName(String user) {
		for(int i = 0; i < rowNum; i++) {
			Row r = currentSheet.getRow(i);
			if(r.getCell(0).getRichStringCellValue().toString().equals(user)) return (int) r.getCell(3).getNumericCellValue();
		}
		return -1;
	}
	
	public int getPointsByDiscordId(String id) {
		for(int i = 0; i < rowNum; i++) {
			Row r = currentSheet.getRow(i);
			if(r.getCell(1).getRichStringCellValue().toString().equals(id)) return (int) r.getCell(3).getNumericCellValue();
		}
		return -1;
	}
	
	public void addUser(String name, String id, String division, int points) {
		Row newRow = currentSheet.createRow(rowNum);
		rowNum++;
		Cell nameCell = newRow.createCell(0);
		nameCell.setCellValue(name);
		Cell idCell = newRow.createCell(1);
		idCell.setCellValue(id);
		Cell divisionCell = newRow.createCell(2);
		divisionCell.setCellValue(division);
		Cell pointsCell = newRow.createCell(3);
		pointsCell.setCellValue(points);
		update();
	}
	
	public void changePointsByName(String name, int newPoints) {
		for(int i = 0; i < rowNum; i++) {
			Row r = currentSheet.getRow(i);
			if(r.getCell(0).getRichStringCellValue().toString().equals(name)) {
				r.getCell(3).setCellValue(newPoints);
				update();
				return;
			}
		}
	}
	
	public void changePointsByDiscordId(String id, int newPoints) {
		for(int i = 0; i < rowNum; i++) {
			Row r = currentSheet.getRow(i);
			if(r.getCell(1).getRichStringCellValue().toString().equals(id)) {
				r.getCell(3).setCellValue(newPoints);
				update();
				return;
			}
		}
	}
	
	public void update() {
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(filename);
			workbook.write(outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getDivisionNames(String division){
		ArrayList<String> divisionList = new ArrayList<String>();
		for(int i = 0; i < rowNum; i++) {
			if(currentSheet.getRow(i).getCell(2).getRichStringCellValue().toString().equals(division)) {
				divisionList.add(currentSheet.getRow(i).getCell(0).getRichStringCellValue().toString());
			}
		}
		return divisionList;
	}

}
