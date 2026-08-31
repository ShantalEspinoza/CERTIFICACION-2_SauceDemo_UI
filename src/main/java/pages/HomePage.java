package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private final WebDriver driver;

    @FindBy(className = "shopping_cart_link")
    private WebElement shoppingCartIcon;

    @FindBy(className = "bm-burger-button")
    private WebElement burgerMenuButton;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;

    @FindBy(className = "product_sort_container")
    private WebElement sortComboBox;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addProductToCart(String productName) {
        String xpath = "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button";
        driver.findElement(By.xpath(xpath)).click();
    }

    public String getProductButtonText(String productName) {
        String xpath = "//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button";
        return driver.findElement(By.xpath(xpath)).getText();
    }

    public void clickProductImageOrName(String productName) {
        driver.findElement(By.xpath("//div[text()='" + productName + "']")).click();
    }

    public void clickShoppingCart() {
        shoppingCartIcon.click();
    }

    public String getShoppingCartBadgeText() {
        try {
            return driver.findElement(By.className("shopping_cart_badge")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // ALGORITMO DE SINCRONIZACIÓN
    public void resetAppState() {
        burgerMenuButton.click();
        // Espera inteligente de hasta 3 segundos exclusivamente para que el link sea clickeable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(resetAppStateLink)).click();
    }

    public void selectSortComboBox(String option) {
        Select select = new Select(sortComboBox);
        select.selectByVisibleText(option);
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement priceText : productPrices) {
            prices.add(Double.parseDouble(priceText.getText().replace("$", "")));
        }
        return prices;
    }
}
