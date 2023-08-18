package sheets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Sheet {
	
	protected String filename;
	protected Workbook workbook;
	protected org.apache.poi.ss.usermodel.Sheet currentSheet;
	protected int rowNum = 0;
	
	public Sheet(String fileLocation) {
		this.filename = fileLocation;
		try {
			FileInputStream file = new FileInputStream(new File(fileLocation));
			workbook = new XSSFWorkbook(file);
			changeSheet(0);
			try {
				Row row = currentSheet.getRow(rowNum);
				while(!row.getCell(0).getRichStringCellValue().equals(new XSSFRichTextString(""))) {
					rowNum++;
					row = currentSheet.getRow(rowNum);
				}
				rowNum++;
			}catch(NullPointerException e) {
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void changeSheet(int sheetNum) {
		currentSheet = workbook.getSheetAt(sheetNum);
	}
	
	public Cell get(int xLoc, int yLoc) {
		return currentSheet.getRow(yLoc).getCell(xLoc);
	}

}
