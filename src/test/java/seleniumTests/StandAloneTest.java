package seleniumTests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjectModel.CartPage;
import PageObjectModel.Checkout;
import PageObjectModel.ProductCatalog;
import TestComponents.BaseTests;

public class StandAloneTest extends BaseTests{
	@Test
	public void submitOrder() throws IOException, InterruptedException {
		String userName = "testaccounts@test.com";
		String password = "123456Aa#";
		String productName= "ADIDAS ORIGINAL";
		String expectedSubTotal = "$31500";
		String expectedTotal = "$31500";
		String countryName = "bangladesh";
		
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
		String orderID= checkout.getOrderId();
		System.out.println(orderID);	
	}

}
