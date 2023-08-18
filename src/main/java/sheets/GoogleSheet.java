package sheets;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSheet {
	
	protected String applicationName;
	protected String spreadsheetId;
	protected Sheets sheet;
	
	protected final String[] alphabet = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
	public GoogleSheet(String applicationName, String spreadsheetId) {
		this.applicationName = applicationName;
		this.spreadsheetId = spreadsheetId;
		this.sheet = SheetService.getSheet(applicationName);
	}
	
	/**
	 * Returns a String representation of the specified Cell (0,0) refers to the cell at the top left (A1 in Excel notation)
	 * @param xCoord
	 * @param yCoord
	 * @return
	 */
	public String get(int xCoord, int yCoord) {
		yCoord++;
		String xAlphabetConversion = "";
		while(xCoord >= 26) {
			xAlphabetConversion += "A";
			xCoord -= 26;
		}
		xAlphabetConversion += alphabet[xCoord];
		try {
			ValueRange data = sheet.spreadsheets().values().get(spreadsheetId, xAlphabetConversion + yCoord).execute();
			List<List<Object>> value = data.getValues();
			if(value != null && value.size() != 0) {
				Object cellValue = value.get(0).get(0);
				if(cellValue != null) {
					return cellValue.toString();
				}else {
					//Cell was empty
					return "";
				}
			}else {
				//No data was found in cell
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns a String representation of the specified Cell
	 * @param col: Alphabetical notation of the Column (A,B,C,...)
	 * @param yCoord: Number of the row (0,1,2,...)
	 * @return
	 */
	public String get(String col, int yCoord) {
		yCoord++;
		try {
			ValueRange data = sheet.spreadsheets().values().get(spreadsheetId, col + yCoord).execute();
			List<List<Object>> value = data.getValues();
			if(value != null && value.size() != 0) {
				Object cellValue = value.get(0).get(0);
				if(cellValue != null) {
					return cellValue.toString();
				}else {
					//Cell was empty
					return "";
				}
			}else {
				//No data was found in cell
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Returns the length of a column
	 * @param colCoord
	 * @return
	 */
	public int getColumnLength(int colCoord) {
		int length = 0;
		String cellValue = get(colCoord, length);
		while(cellValue != "" && cellValue != null) {
			length++;
			cellValue = get(colCoord, length);
		}
		return length;
	}
	
	public int getColumnLength(String col) {
		int length = 0;
		String cellValue = get(col, length);
		while(cellValue != "" && cellValue != null) {
			length++;
			cellValue = get(col, length);
		}
		return length;
	}
	
	public int getRowLength(int rowIndex) {
		int length = 0;
		String cellValue = get(length, rowIndex);
		while(cellValue != "" && cellValue != null) {
			length++;
			cellValue = get(length, rowIndex);
		}
		return length;
	}
	
	public void change(int xCoord, int yCoord, Object replacementObject) {
		yCoord++;
		String xAlphabetConversion = "";
		while(xCoord > 26) {
			xAlphabetConversion += "A";
			xCoord -= 26;
		}
		xAlphabetConversion += alphabet[xCoord];
		ValueRange updatedValue = new ValueRange();
		updatedValue.setValues(Arrays.asList(Arrays.asList(replacementObject)));
		try {
			UpdateValuesResponse response = sheet.spreadsheets().values()
					.update(spreadsheetId, xAlphabetConversion + yCoord, updatedValue)
					.setValueInputOption("RAW")
					.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void change(String col, int yCoord, Object replacementObject) {
		yCoord++;
		ValueRange updatedValue = new ValueRange();
		updatedValue.setValues(Arrays.asList(Arrays.asList(replacementObject)));
		try {
			UpdateValuesResponse response = sheet.spreadsheets().values()
					.update(spreadsheetId, col + yCoord, updatedValue)
					.setValueInputOption("RAW")
					.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
