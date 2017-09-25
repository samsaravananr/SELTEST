import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class Demo1
{

	WebDriver myD;
	String vKEY,vCHAN,s1,s2,s3;
	public int xTD_Rows,xTD_Cols;
	String xTDData[][];
	
	
	@BeforeTest
	public void SetUP() throws Exception
	{
		xlRead_TD("C:\\Selenium Drivers\\YT_Keyword.xls");
		//System.setProperty("webdriver.chrome.driver", "C:\\Selenium Drivers\\chromedriver.exe");
		//myD=new ChromeDriver();
		DesiredCapabilities capabilities=DesiredCapabilities.chrome();
		myD=new RemoteWebDriver(new URL("http://192.168.1.6:5566/wd/hub"),capabilities);
	

	}
	
	
	@org.testng.annotations.Test
	public void Test() throws Exception
	{
		
		
		myD.get("https://www.youtube.com/");
		
		
		for (int i=1;i<xTD_Rows;i++)
		{
			if(xTDData[i][0].equals("YT1"))
			{
			Thread.sleep(2000);
			myD.findElement(By.xpath("//input[@name='search_query']")).clear();
			vKEY=xTDData[i][1];
			vCHAN=xTDData[i][2];
			//1. Go to Youtube.com
			
			System.out.println("Test");
			
		//	myD.findElement(By.xpath("//div[@id='search-input']")).clear();
			//2. Type the Search Keyword
			Thread.sleep(2000);
			myD.findElement(By.xpath("//input[@name='search_query']")).sendKeys(vKEY);
			//myD.findElement(By.xpath("//div[@id='search-input']")).sendKeys(vKEY);
			
			// 3. Click Search Button
			myD.findElement(By.xpath(".//*[@id='search-icon-legacy']")).click();
			// 4. Wait for Search Results
			
			Thread.sleep(5000);
			
			
			// 5. Capture the Channel name on Top 3 search results
			s1=myD.findElement(By.xpath("(//div[@id='byline-inner-container']/yt-formatted-string/a)[1]")).getText();
			s2=myD.findElement(By.xpath("(//div[@id='byline-inner-container']/yt-formatted-string/a)[2]")).getText();
			s3=myD.findElement(By.xpath("(//div[@id='byline-inner-container']/yt-formatted-string/a)[3]")).getText();
			System.out.println("Channel 1 is "+s1);
			System.out.println("Channel 2 is "+s2);
			System.out.println("Channel 3 is "+s3);
			
			
			// 6. Compare with our expected Channel Name
			// 7. Pass if atleast one of them is a pass.
			if (s1.equals(vCHAN) || s2.equals(vCHAN) || s3.equals(vCHAN))
			{
				System.out.println("PASS: Channel Present");
				xTDData[i][4]="PASS";
			}
			else
			{
				System.out.println("FAIL: Channel Does Not Present");
				xTDData[i][4]="FAIL";
			}
			
			
			}
			// 8. Repeat above for different Search Keywords
		}
	
	
	}
	
	
	
	
	@AfterTest
	public void EndTest() throws Exception
	{
		xlwrite("C:\\\\Selenium Drivers\\\\YT_Results1.xls", xTDData);
		myD.quit();
	}
	
	
	public void xlRead_TD(String sPath) throws Exception
	{
		System.out.println("Printed");
		File myxl=new File(sPath);
		FileInputStream myStream=new FileInputStream(myxl);
		HSSFWorkbook myWB=new HSSFWorkbook(myStream);
		HSSFSheet mySheet=myWB.getSheetAt(1);
		xTD_Rows=mySheet.getLastRowNum()+1; //made it global
		xTD_Cols=mySheet.getRow(0).getLastCellNum(); // made it global
		//myprint("Rows are " + xTC_Rows);
		//myprint("Cols are " + xTC_Cols);
		xTDData = new String[xTD_Rows][xTD_Cols]; // made it global
	    for (int i = 0; i < xTD_Rows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (short j = 0; j < xTD_Cols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xTDData[i][j] = value;
	               //System.out.print(value); //not required
	               //System.out.print("   "); //not required
	               }
	            System.out.println("");
	            
	        }	
		
	}
	
	public static String cellToString(HSSFCell cell) {

		// This function will convert an object of type excel cell to a string value
		    int type = cell.getCellType();
		    Object result;
		    switch (type) {
		        case HSSFCell.CELL_TYPE_NUMERIC: //0
		            result = cell.getNumericCellValue();
		            break;
		        case HSSFCell.CELL_TYPE_STRING: //1
		            result = cell.getStringCellValue();
		            break;
		        case HSSFCell.CELL_TYPE_FORMULA: //2
		            throw new RuntimeException("We can't evaluate formulas in Java");
		        //case HSSFCell.CELL_TYPE_BLANK: //3
		         //   result = "-";
		          //  break;
		        case HSSFCell.CELL_TYPE_BOOLEAN: //4
		            result = cell.getBooleanCellValue();
		            break;
		        case HSSFCell.CELL_TYPE_ERROR: //5
		            throw new RuntimeException ("This cell has an error");
		        default:
		            throw new RuntimeException("We don't support this cell type: " + type);
		    }
		    return result.toString();
		}
	
	public void xlwrite(String xlPath, String[][] xldata) throws Exception {
		System.out.println("Inside XL Write");
    	File outFile = new File(xlPath);
        HSSFWorkbook wb = new HSSFWorkbook();
           // Make a worksheet in the XL document created
        /*HSSFSheet osheet = wb.setSheetName(1,"TEST");*/
        HSSFSheet osheet = wb.createSheet("TESTRESULTS");
        // Create row at index zero ( Top Row)
    	for (int myrow = 0; myrow < xTD_Rows; myrow++) {
    		//System.out.println("Inside XL Write");
	        HSSFRow row = osheet.createRow(myrow);
	        // Create a cell at index zero ( Top Left)
	        for (short mycol = 0; mycol < xTD_Cols; mycol++) {
	        	HSSFCell cell = row.createCell(mycol);
	        	// Lets make the cell a string type
	        	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	        	// Type some content
	        	cell.setCellValue(xldata[myrow][mycol]);
	        	//System.out.print("..." + xldata[myrow][mycol]);
	        }
	        //System.out.println("..................");
	        // The Output file is where the xls will be created
	        FileOutputStream fOut = new FileOutputStream(outFile);
	        // Write the XL sheet
	        wb.write(fOut);
	        fOut.flush();
//		    // Done Deal..
	        fOut.close();
    	}
	}
	
	
	
}
