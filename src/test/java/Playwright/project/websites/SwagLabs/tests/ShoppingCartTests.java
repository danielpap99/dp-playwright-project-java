package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.pages.ProductPage;
import Playwright.project.websites.SwagLabs.utilities.Base;
import Playwright.project.websites.SwagLabs.pages.CartPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTests extends Base {

    private CartPage cartPage;
    private ProductPage productPage;

    @BeforeEach
    void setUpOrderFlowPages() {
        cartPage = new CartPage(page);
        productPage = new ProductPage(page);
    }

    @Test
    @Tag("Stage2")
    void cartDisplaysCorrectDetailsForAddedItems() {
        goToMainPage();

        String backpack = "Sauce Labs Backpack";
        String bikeLight = "Sauce Labs Bike Light";

        mainPage.addProductToCart(backpack);
        mainPage.addProductToCart(bikeLight);
        mainPage.openCart();

        assertAll(
                () -> assertThat(cartPage.itemQuantityText(backpack)).isEqualTo("1"),
                () -> assertThat(cartPage.itemNameText(backpack)).isEqualTo(backpack),
                () -> assertThat(cartPage.itemDescriptionText(backpack)).contains("carry.allTheThings()"),
                () -> assertThat(cartPage.itemPriceText(backpack)).isEqualTo("$29.99"),
                () -> assertThat(cartPage.removeButtonVisible(backpack)).isTrue()
        );

        assertAll(
                () -> assertThat(cartPage.itemQuantityText(bikeLight)).isEqualTo("1"),
                () -> assertThat(cartPage.itemNameText(bikeLight)).isEqualTo(bikeLight),
                () -> assertThat(cartPage.itemDescriptionText(bikeLight)).contains("Water-resistant"),
                () -> assertThat(cartPage.itemPriceText(bikeLight)).isEqualTo("$9.99"),
                () -> assertThat(cartPage.removeButtonVisible(bikeLight)).isTrue()
        );
    }

    @Test
    @Tag("Stage2")
    void itemCanBeRemovedFromCart() {
        goToMainPage();

        String backpack = "Sauce Labs Backpack";
        String bikeLight = "Sauce Labs Bike Light";

        mainPage.addProductToCart(backpack);
        mainPage.addProductToCart(bikeLight);
        mainPage.openCart();

        cartPage.removeProductFromCart(backpack);

        assertThat(cartPage.productCardIsVisible(backpack)).isFalse();
    }

    @Test
    @Tag("Stage2")
    void clickingOnItemNameTakesUserToProductPage() {
        goToMainPage();

        String backpack = "Sauce Labs Backpack";

        mainPage.addProductToCart(backpack);
        mainPage.openCart();

        cartPage.clickProductLink(backpack);

        assertThat(productPage.productPageUrl()).contains("inventory-item.html");
        assertThat(productPage.itemNameText()).isEqualTo(backpack);
    }
}