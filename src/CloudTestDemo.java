import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class CloudTestDemo 
{
	public WebDriver myD;
	
	@Test
	public void t001() throws MalformedURLException, Exception
	{
		DesiredCapabilities DC=DesiredCapabilities.firefox();
		DC.setCapability("version", "50.0");
		DC.setCapability("platform", "Windows 7");
		String vURL="http://samsaravananr:423d6cc8-42a6-4d91-8509-a9d140d31b0b@ondemand.saucelabs.com:80/wd/hub";
		myD=new RemoteWebDriver(new URL(vURL), DC);
		myD.get("https://www.google.co.in/?gfe_rd=cr&dcr=0&ei=1QrEWb2hEMry8Afmx7OYAQ");
		Thread.sleep(10000);
		System.out.println(myD.getTitle());
		
	}

}
