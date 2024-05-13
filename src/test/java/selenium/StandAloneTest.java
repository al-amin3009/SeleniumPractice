package selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import PageObjectModel.CartPage;
import PageObjectModel.Checkout;
import PageObjectModel.LandingPage;
import PageObjectModel.ProductCatalog;

public class StandAloneTest {
	public static void main(String[] args) throws InterruptedException {
		String HomeURL = "https://rahulshettyacademy.com/client/";
		String userName = "";
		String password = "";
		String productName= "ADIDAS ORIGINAL";
		String expectedAddToCartText = "Product Added To Cart";
		String expectedSubTotal = "$31500";
		String expectedTotal = "$31500";
		String countryName = "bangladesh";
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(HomeURL);
		
		// -------------------------------------------------Login the page---------------------------------------------------------
		LandingPage landingPage = new LandingPage(driver);
		landingPage.login(userName, password);
		Thread.sleep(3000);
		
		// --------------------------------------------------List the all products and add to cart the specific product-------------------------------------------
		ProductCatalog productCatelog = new ProductCatalog(driver);
		productCatelog.addProductToCart(productName);
		// click on the "cart" ion
		productCatelog.goToTheCartPage();
		
		// ---------------------------------------------------list of the cart items and check if the cart items is matching with the specific product------------------------
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.verifyCartItems(productName);
		Assert.assertTrue(match); 
		//check if the total price match with the product price
		Assert.assertEquals(expectedSubTotal, cartPage.verifyCartSubTotal(expectedSubTotal));
		Assert.assertEquals(expectedTotal, cartPage.verifyCartTotal(expectedTotal));
		// click on the "checkout" page
		cartPage.goToCheckoutPage();
		
		// ------------------------------------------Checkout--------------------------------------------------------------------------------------------
		//search and select the country
		Checkout checkout = new Checkout(driver);
		checkout.selectCountry(countryName);
		// click the submit button
		checkout.ConfirmOrder();
		// check the confirmation message
		String confirmMessage = checkout.getConfirmMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		//get order id
		String orderID= checkout.getOrderId();
		System.out.println(orderID);
		
		driver.close();
	}

}
