package seleniumTests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class copy {
	public static void main(String[] args) throws InterruptedException {

		String HomeURL = "https://rahulshettyacademy.com/client/";
		String userName = "";
		String password = "";
		String productName= "ADIDAS ORIGINAL";
		String expectedAddToCartText = "Product Added To Cart";
		String expectedSubTotal = "$31500";
		String expectedTotal = "$31500";

		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		driver.get(HomeURL);


		// Login the page
		driver.findElement(By.id("userEmail")).sendKeys(userName);
		driver.findElement(By.id("userPassword")).sendKeys(password);
		driver.findElement(By.id("login")).click();
		Thread.sleep(3000);

		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		// List the all products and add to cart the specific product
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement getProduct = products.stream().filter(product -> product.findElement(By.tagName("h5")).getText().equals(productName))
		   .findFirst().orElse(null);
		System.out.println(getProduct.findElement(By.tagName("h5")).getText());
		getProduct.findElement(By.className("w-10")).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		String text = driver.findElement(By.cssSelector("#toast-container")).getText();
		//Assert.assertEquals(text, expectedAddToCartText);
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		// click on the "cart" ion
		driver.findElement(By.cssSelector("[routerlink*='cart'")).click();

		// list of the cart items and check if the cart items is matching with the specific product
		List<WebElement> cartItems = driver.findElements(By.cssSelector("div[class='cartSection'] h3"));
		WebElement cartItem = cartItems.stream().filter(item-> item.getText().equals(productName)).findFirst().orElse(null);
		String cartItemName = cartItem.getText();
		Assert.assertEquals(cartItemName, productName);
		//Boolean match = cartItems.stream().anyMatch(item -> item.getText().equalsIgnoreCase(productName));
		//Assert.assertTrue(match); 


		// check if the total price match with the product price
		String subtotal= driver.findElement(By.xpath("(//li[@class='totalRow']/span[@class='value'])[1]")).getText();
		String total = driver.findElement(By.xpath("(//li[@class='totalRow']/span[@class='value'])[1]")).getText();
		Assert.assertEquals(expectedSubTotal, subtotal);
		Assert.assertEquals(expectedTotal, total);

		// click on the "checkout" page
		driver.findElement(By.cssSelector(".totalRow .btn")).click();

		// search and select the country
		//driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("ba");
		//List<WebElement> countryList = driver.findElements(By.xpath("//section[@class='ta-results list-group ng-star-inserted']/button"));
		////countryList.stream().forEach(countryName-> System.out.println(countryName.getText()));
		//WebElement country = countryList.stream().filter(countries -> countries.getText().equalsIgnoreCase("Bangladesh")).findFirst().orElse(null);
		//country.click();
		// with action class
		Actions action = new Actions(driver);
		action.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")), "bangladesh").build().perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[1]")).click();

		// click the placeholder
		driver.findElement(By.className("action__submit")).click();

		// check the confirmation message
		String confirmMessage = driver.findElement(By.className("hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

		String orderID= driver.findElement(By.cssSelector("tr[class='ng-star-inserted'] td[class='em-spacer-1'] label")).getText();
		System.out.println(orderID);
		driver.close();

		}
}
