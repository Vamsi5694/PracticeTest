
package com.execution;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AmazonTest {

	WebDriver driver = null;
	@BeforeSuite
	@Parameters({"browserName"})
	public void setup(String browser) throws MalformedURLException {
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			DesiredCapabilities dec = DesiredCapabilities.chrome();
			dec.setCapability("version", "");
			dec.setPlatform(Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), dec);
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			DesiredCapabilities dec = DesiredCapabilities.firefox();
			dec.setCapability("version", "");
			dec.setPlatform(Platform.WINDOWS);
			driver = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), dec);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.amazon.in");
	}
	
	@Test
	public void webScraping() {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println(links.size());
		for(WebElement element: links) {
			System.out.println(element.getAttribute("href"));
		}
	}
	
	@AfterSuite
	public void teardown() {
		driver.quit();
	}
}
