import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestGrid 
{
	public static void main(String[] args) throws MalformedURLException 
	{
	
	WebDriver driver;
	
	DesiredCapabilities capabilities = DesiredCapabilities.chrome();

	//capabilities.setCapability(FirefoxDriver.BINARY,new File("C:\\Program Files\\Mozilla Firefox\\firefox.exe").getAbsolutePath());
	  
	driver = new RemoteWebDriver(new URL("http://192.168.1.6:5566/wd/hub"), capabilities);
	     
	driver.get("http://google.com");
	System.out.println(driver.getTitle());
	    
	}
	}


