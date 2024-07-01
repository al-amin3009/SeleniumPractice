package seleniumTests;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import PageObjectModel.CartPage;
import PageObjectModel.Checkout;
import PageObjectModel.OrderHistoryPage;
import PageObjectModel.ProductCatalog;
import TestComponents.BaseTests;

public class StandAloneTest extends BaseTests{
	String userName;
	String password;
	String productName;
	String orderID;
	@Test(dataProvider="getData")
	public void submitOrder(HashMap<String, String> map) throws IOException, InterruptedException {
		String expectedSubTotal = "$31500";
		String expectedTotal = "$31500";
		String countryName = "bangladesh";
		this.productName = map.get("productName");
		this.userName= map.get("email");
		this.password= map.get("password");
		//launchApplication(HomeURL);
		// -------------------------------------------------Login the page---------------------------------------------------------
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
		orderID= checkout.getOrderId();
		orderID= orderID.split(" | ")[1];
		
		System.out.println(orderID);	
	}
	
	@Test(dependsOnMethods={"submitOrder"})
	public void OrderHistory() {
		landingPage.login(userName, password);
		landingPage.goToTheOrderHistory();
		OrderHistoryPage orderPage = new OrderHistoryPage(driver);
		Assert.assertEquals(orderPage.getOrderHistory(), productName);
		Assert.assertEquals(orderPage.getOrderHistoryID(), orderID);
	}
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\Data\\PurchaseOrder.json");
		return new Object[][] {
			{data.get(0)},
			{data.get(1)}
		};
	}

}
