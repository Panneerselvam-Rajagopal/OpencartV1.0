package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	
	//Dataprovider 1
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException
	{
		String path = ".\\testData\\Opencart_LoginData.xlsx";		// take xl file from testData
		
		ExcelUtility xlutil = new ExcelUtility(path);				// creating an object for XLutility
		
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1",1);
		
		String logindata[][] = new String[totalrows][totalcols];	// Created for two dimensional array which can store
		
		for(int i=1; i<=totalrows;i++)								// read the data from xl storing in two dimensional array
		{
			for(int j=0; j<totalcols;j++)
			{
				logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j);
			}
		}
		
		return logindata;											// returning two dimensional array
		
		
	}
	
	// Dataprovider 2
	
	// Dataprovider 3
	
	// Dataprovider 4

}
