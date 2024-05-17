package TestComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import PageObjectModel.LandingPage;

public class BaseTests {
	public WebDriver driver;
	
	public WebDriver initializeDriver() throws IOException {
		
		Properties properties = new Properties();
		FileInputStream fis = new FileInputStream("C:\\Users\\Al-Amin\\eclipse-workspace\\seleniumFramework\\src\\main\\java\\resourses\\GlobalData.properties");
		properties.load(fis);
		
		String browserName = properties.getProperty("browser");
		
		if(browserName.contains("chrome")) {
			driver = new ChromeDriver();
			System.out.println("got chrome");
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public void launchApplication(String URL) throws IOException {
		driver = initializeDriver();
		LandingPage landingPage = new LandingPage(driver);
		landingPage.gotoHome(URL);
	}
}
