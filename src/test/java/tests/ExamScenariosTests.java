package tests;

import com.google.common.collect.Ordering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.CheckoutPages;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductDetailPage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ExamScenariosTests extends BaseTest {

    public void loginStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserNameTextBox("standard_user");
        loginPage.setPasswordTextBox("secret_sauce");
        loginPage.clickOnLoginButton();
    }

    @Test
    public void verifyPostalCodeErrorMarksAllFieldsAsIncorrect() {
        loginStandardUser();

        HomePage homePage = new HomePage(driver);
        homePage.clickShoppingCart();

        CheckoutPages checkout = new CheckoutPages(driver);
        checkout.clickCheckout();
        checkout.fillCheckoutInformation("Shantal", "Espinoza", "");
        checkout.clickContinue();

        Assertions.assertEquals("Error: Postal Code is required", checkout.getErrorMessage());
        Assertions.assertTrue(checkout.doAllFieldsHaveErrorClass(), "Los campos no marcaron la clase CSS de error.");
    }

    @Test
    public void verifyResetAppStateClearsTheShoppingCart() {
        loginStandardUser();

        HomePage homePage = new HomePage(driver);
        homePage.addProductToCart("Sauce Labs Backpack");
        Assertions.assertEquals("1", homePage.getShoppingCartBadgeText());

        homePage.resetAppState();
        driver.navigate().refresh();

        Assertions.assertEquals("", homePage.getShoppingCartBadgeText(), "El contador del carrito no se limpió.");
    }

    @Test
    public void verifyItemRemainsInCartWhenNavigatingBackFromDetailsPage() {
        loginStandardUser();

        HomePage homePage = new HomePage(driver);
        homePage.clickProductImageOrName("Sauce Labs Fleece Jacket");

        ProductDetailPage detailPage = new ProductDetailPage(driver);
        detailPage.clickAddToCart();
        detailPage.clickBackButton();

        Assertions.assertEquals("1", homePage.getShoppingCartBadgeText());
        Assertions.assertTrue(homePage.getProductButtonText("Sauce Labs Fleece Jacket").equalsIgnoreCase("REMOVE"),
                "El botón no actualizó su estado a 'REMOVE'.");
    }

    @Test
    public void verifyProductsCanBeSortedFromPriceHighToLow() {
        loginStandardUser();

        HomePage homePage = new HomePage(driver);
        homePage.selectSortComboBox("Price (high to low)");

        List<Double> actualPrices = homePage.getProductPrices();
        boolean isSorted = Ordering.natural().reverse().isOrdered(actualPrices);

        Assertions.assertTrue(isSorted, "La lista de precios no se ordenó de mayor a menor.");
    }

    @Test
    public void verifyTotalCalculatedPriceIsCorrectAtCheckout() {
        loginStandardUser();

        HomePage homePage = new HomePage(driver);
        homePage.addProductToCart("Sauce Labs Backpack");
        homePage.addProductToCart("Sauce Labs Bike Light");
        homePage.clickShoppingCart();

        CheckoutPages checkout = new CheckoutPages(driver);
        checkout.clickCheckout();
        checkout.fillCheckoutInformation("Juan", "Perez", "0000");
        checkout.clickContinue();

        BigDecimal subtotal = BigDecimal.valueOf(checkout.getSubtotal());
        BigDecimal tax = BigDecimal.valueOf(checkout.getTax());
        BigDecimal expectedTotal = subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);
        BigDecimal actualTotalDisplayed = BigDecimal.valueOf(checkout.getTotal());

        Assertions.assertEquals(expectedTotal, actualTotalDisplayed, "Discrepancia en el cálculo del total.");
    }
}
