package TestComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import PageObjectModel.LandingPage;

public class BaseTests {
	public WebDriver driver;
	public LandingPage landingPage;
	String HomeURL = "https://rahulshettyacademy.com/client/";
	public WebDriver initializeDriver() throws IOException {
		
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resourses\\GlobalData.properties");
		properties.load(fis);
		
		String browserName = properties.getProperty("browser");
		
		if(browserName.contains("chrome")) {
			driver = new ChromeDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	} 
	
	@BeforeClass(alwaysRun=true)
	public void launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.gotoHome(HomeURL);
	}
	
	@AfterClass(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
}
