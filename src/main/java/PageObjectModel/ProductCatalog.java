package PageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponent.AbstractComponent;

public class ProductCatalog extends AbstractComponent{
	WebDriver driver;
	String productTitle;
	
	public ProductCatalog(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".mb-3") List<WebElement> products;
	@FindBy(css="#toast-container") WebElement toastMessage;
	@FindBy(css=".ng-animating") WebElement loadingAnimator;
	By productsBy = By.cssSelector(".mb-3");
	By productTitleBy = By.tagName("h5");
	By addToCart = By.className("w-10");
	By toastMessageBy = By.cssSelector("#toast-container");
	
	
	public List<WebElement> getProductList() {
		waitForElementAppear(productsBy);
		return products;
	}
	
	
	public WebElement getProductByName(String productName) {
		WebElement getProduct = getProductList().stream().filter(product -> product.findElement(productTitleBy).getText().equals(productName))
				  .findFirst().orElse(null);
		return getProduct;
	}
	
	
	public void addProductToCart(String productName) {
		WebElement getProduct = getProductByName(productName);
		productTitle = getProduct.findElement(productTitleBy).getText();
		System.out.println(productTitle);
		getProduct.findElement(addToCart).click();
		waitForElementAppear(toastMessageBy);
		waitForElementDisappear(loadingAnimator);
	}
	
}
