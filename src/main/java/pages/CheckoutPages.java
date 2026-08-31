package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CheckoutPages {
    private final WebDriver driver;

    @FindBy(className = "checkout_button")
    private WebElement checkoutButton;

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(css = "input[type='submit']")
    private WebElement continueButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    public CheckoutPages(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCheckout() {
        checkoutButton.click();
    }

    public void fillCheckoutInformation(String name, String last, String postal) {
        firstNameInput.sendKeys(name);
        lastNameInput.sendKeys(last);
        postalCodeInput.sendKeys(postal);
    }

    public void clickContinue() {
        continueButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean doAllFieldsHaveErrorClass() {
        List<WebElement> errorFields = driver.findElements(By.cssSelector("input.error"));
        return errorFields.size() == 3;
    }

    public double getSubtotal() {
        String text = subtotalLabel.getText().replaceAll("[^0-9.]", "");
        return Double.parseDouble(text);
    }

    public double getTax() {
        String text = taxLabel.getText().replaceAll("[^0-9.]", "");
        return Double.parseDouble(text);
    }

    public double getTotal() {
        String text = totalLabel.getText().replaceAll("[^0-9.]", "");
        return Double.parseDouble(text);
    }
}
