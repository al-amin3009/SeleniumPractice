package PageObjectModel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;

public class OrderHistoryPage extends AbstractComponent{
	WebDriver driver;
	public OrderHistoryPage(WebDriver driver)
	{
		super(driver);
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="tbody tr:nth-child(1) td:nth-child(3)") WebElement products;
	@FindBy(css="tbody tr:nth-child(1) th") WebElement orderID;
	
	public String getOrderHistory() {
		String name = products.getText();
		return name;
	}
	
	public String getOrderHistoryID() {
		String id = orderID.getText();
		return id;
	}
}
