package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductDetailPage {
    private final WebDriver driver;

    @FindBy(className = "btn_primary")
    private WebElement addToCartButton;

    @FindBy(className = "inventory_details_back_button")
    private WebElement backButton;

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAddToCart() {
        addToCartButton.click();
    }

    public void clickBackButton() {
        backButton.click();
    }
}
