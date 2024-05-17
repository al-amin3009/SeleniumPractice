package PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;

public class Checkout extends AbstractComponent{
	WebDriver driver;
	Actions action;
	public Checkout(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.action = new Actions(driver);
	}
	@FindBy(css="input[placeholder='Select Country']") WebElement countryField;
	@FindBy(xpath="(//button[contains(@class,'ta-item')])[1]") WebElement selectCountry;
	@FindBy(className="action__submit") WebElement submit;
	@FindBy(className="hero-primary") WebElement confirmMessage;
	@FindBy(css="tr[class='ng-star-inserted'] td[class='em-spacer-1'] label") WebElement orderID;
	
	By findBy = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName) {
		action.sendKeys(countryField, countryName).build().perform();
		waitForElementAppear(findBy);
		selectCountry.click();
	}
	
	public void ConfirmOrder() {
		submit.click();
	}
	
	public String getConfirmMessage() {
		return confirmMessage.getText();
	}
	
	public String getOrderId() {
		return orderID.getText();
	}
	
	
}
