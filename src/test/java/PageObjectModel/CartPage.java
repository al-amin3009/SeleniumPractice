package PageObjectModel;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import selenium.AbstractComponent;

public class CartPage extends AbstractComponent{
	WebDriver driver;
	
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	@FindBy(css="div[class='cartSection'] h3") List<WebElement> cartItems;
	@FindBy(xpath="(//li[@class='totalRow']/span[@class='value'])[1]") WebElement cartSubTotal;
	@FindBy(xpath="(//li[@class='totalRow']/span[@class='value'])[2]") WebElement cartTotal;
	@FindBy(css=".totalRow .btn") WebElement checkout;
	
	
	public Boolean verifyCartItems(String productName) {
		Boolean match = cartItems.stream().anyMatch(item -> item.getText().equalsIgnoreCase(productName));
		return match; 
	}
	
	public String verifyCartSubTotal (String expectedSubTotal) {
		String subTotal= cartSubTotal.getText();
		return subTotal;
	}
	
	public String verifyCartTotal (String expectedTotal) {
		String total = cartTotal.getText();
		return total;
	}
	
	public void goToCheckoutPage() {
		checkout.click();
	}
}
