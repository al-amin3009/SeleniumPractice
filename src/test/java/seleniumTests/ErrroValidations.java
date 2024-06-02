package seleniumTests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import PageObjectModel.CartPage;
import PageObjectModel.ProductCatalog;
import TestComponents.BaseTests;

public class ErrroValidations extends BaseTests{
	@Test
	public void loginErrorValidation() throws IOException, InterruptedException {
		String userName = "testaccount@test.com";
		String password = "123456Aa";
		landingPage.login(userName, password);
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
		//document.querySelector(".ng-tns-c4-13.ng-star-inserted.ng-trigger.cc.ngx-toastr.toast-error")
	}
	
	@Test
	public void productErrorValidation() {
		String userName = "anshika@gmail.com";
		String password = "Iamking@000";
		String productName= "ADIDAS ORIGINAL";
		
		landingPage.login(userName, password);
		
		ProductCatalog productCatelog = new ProductCatalog(driver);
		productCatelog.addProductToCart(productName);
		// click on the "cart" ion
		productCatelog.goToTheCartPage();
		
		CartPage cartPage = new CartPage(driver);
		Boolean match = cartPage.verifyCartItems("ZARA COAT 3");
		Assert.assertFalse(match); 
	}

}
