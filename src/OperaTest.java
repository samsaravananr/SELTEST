import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.opera.OperaDriver;

public class OperaTest {

	
		@Test
		public void t001() throws Exception
		{
		//System.setProperty("webdriver.chrome.driver", "C:\\Selenium Drivers\\chromedriver.exe");
		WebDriver myD=new HtmlUnitDriver();
		myD.get("https://www.google.com/");
		System.out.println(myD.getTitle());
		myD.close();
		}
	

}


// This is Test God is Good