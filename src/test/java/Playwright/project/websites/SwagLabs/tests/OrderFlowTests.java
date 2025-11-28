package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.pages.CheckoutPage;
import Playwright.project.websites.SwagLabs.utilities.Base;
import Playwright.project.websites.SwagLabs.pages.CartPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class OrderFlowTests extends Base {

    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeEach
    void setUpOrderFlowPages() {
        cartPage = new CartPage(page);
        checkoutPage = new CheckoutPage(page);
    }

    @Test
    @Tag("Stage1")
    void checkoutPageLoadsCorrectly() {
        goToMainPage();

        mainPage.productCardComponent.addToCartButton("Sauce Labs Backpack");
        mainPage.openCart();
        mainPage.clickCheckoutButton();

        assertThat(checkoutPage.checkoutPageLoads()).isTrue();
    }

    @Test
    @Tag("Stage1")
    void orderOverviewDisplaysProductsCorrectly() {
        goToMainPage();

        String backpack = "Sauce Labs Backpack";
        String onesie = "Sauce Labs Onesie";
        String backpackPrice = mainPage.productCardComponent.productPriceText(backpack);
        String onesiePrice = mainPage.productCardComponent.productPriceText(onesie);


        mainPage.productCardComponent.addProductToCart(backpack);
        mainPage.productCardComponent.addProductToCart(onesie);
        mainPage.openCart();
        cartPage.clickCheckoutButton();
        checkoutPage.enterPersonalDetails("Daniel", "Papadopoulous", "M20");
        checkoutPage.clickContinueButton();

        assertAll(
                () -> assertThat(checkoutPage.productCardComponent.productNameText(backpack)).isEqualTo(backpack),
                () -> assertThat(checkoutPage.productCardComponent.productNameText(onesie)).isEqualTo(onesie),
                () -> assertThat(checkoutPage.productCardComponent.productPriceText(backpack)).isEqualTo(backpackPrice),
                () -> assertThat(checkoutPage.productCardComponent.productPriceText(onesie)).isEqualTo(onesiePrice)
        );
    }

    @Test
    @Tag("Stage1")
    void orderOverviewCalculatesTotalPriceCorrectly () {
        goToMainPage();

        String backpack = "Sauce Labs Backpack";
        String onesie = "Sauce Labs Onesie";
        double backpackPrice = Double.parseDouble(mainPage.productCardComponent.productPriceText(backpack).replace("$", "").trim());
        double onesiePrice = Double.parseDouble(mainPage.productCardComponent.productPriceText(onesie).replace("$", "").trim());
        double subtotal = backpackPrice + onesiePrice;


        mainPage.productCardComponent.addProductToCart(backpack);
        mainPage.productCardComponent.addProductToCart(onesie);
        mainPage.openCart();
        cartPage.clickCheckoutButton();
        checkoutPage.enterPersonalDetails("Daniel", "Papadopoulous", "M20");
        checkoutPage.clickContinueButton();

        assertThat(checkoutPage.subtotalText()).isEqualTo("Item total: $" + subtotal);
    }

    @Test
    @Tag("Stage1")
    void orderCanBePlaced() {
        goToMainPage();

        mainPage.productCardComponent.addProductToCart("Sauce Labs Backpack");
        mainPage.openCart();
        mainPage.clickCheckoutButton();
        checkoutPage.enterPersonalDetails("Daniel", "Papadopoulous", "M20");
        checkoutPage.clickContinueButton();
        checkoutPage.clickFinishButton();

        assertAll(
                () -> assertThat(checkoutPage.orderPlacedTitleText()).isEqualTo("Thank you for your order!"),
                () -> assertThat(checkoutPage.orderPlacedMessageText()).isEqualTo("Your order has been dispatched, and will arrive just as fast as the pony can get there!")
        );

        checkoutPage.clickBackHomeButton();

        assertThat(mainPage.isOnInventoryPage()).isTrue();
    }
}
