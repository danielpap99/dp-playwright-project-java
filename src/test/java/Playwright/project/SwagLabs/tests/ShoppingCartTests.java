package Playwright.project.SwagLabs.tests;

import Playwright.project.SwagLabs.base.TestBase;
import Playwright.project.SwagLabs.pages.ProductPage;
import Playwright.project.SwagLabs.pages.CartPage;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTests extends TestBase {

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

        mainPage.productCardComponent.addProductToCart(backpack);
        mainPage.productCardComponent.addProductToCart(bikeLight);
        mainPage.openCart();

        assertAll(
                () -> assertThat(cartPage.productCardComponent.itemQuantityText(backpack)).isEqualTo("1"),
                () -> assertThat(cartPage.productCardComponent.productNameText(backpack)).isEqualTo(backpack),
                () -> assertThat(cartPage.productCardComponent.itemDescriptionText(backpack)).contains("carry.allTheThings()"),
                () -> assertThat(cartPage.productCardComponent.productPriceText(backpack)).isEqualTo("$29.99"),
                () -> assertThat(cartPage.productCardComponent.removeButtonVisible(backpack)).isTrue()
        );

        assertAll(
                () -> assertThat(cartPage.productCardComponent.itemQuantityText(bikeLight)).isEqualTo("1"),
                () -> assertThat(cartPage.productCardComponent.productNameText(bikeLight)).isEqualTo(bikeLight),
                () -> assertThat(cartPage.productCardComponent.itemDescriptionText(bikeLight)).contains("Water-resistant"),
                () -> assertThat(cartPage.productCardComponent.productPriceText(bikeLight)).isEqualTo("$9.99"),
                () -> assertThat(cartPage.productCardComponent.removeButtonVisible(bikeLight)).isTrue()
        );
    }

    @Test
    @Tag("Stage2")
    void itemCanBeRemovedFromCart() {
        goToMainPage();

        String backpack = "Sauce Labs Backpack";
        String bikeLight = "Sauce Labs Bike Light";

        mainPage.productCardComponent.addProductToCart(backpack);
        mainPage.productCardComponent.addProductToCart(bikeLight);
        mainPage.openCart();

        cartPage.productCardComponent.removeProductFromCart(backpack);

        assertThat(cartPage.productCardComponent.productCardIsVisible(backpack)).isFalse();
    }

    @Test
    @Tag("Stage2")
    void clickingOnItemNameTakesUserToProductPage() {
        goToMainPage();

        String backpack = "Sauce Labs Backpack";

        mainPage.productCardComponent.addProductToCart(backpack);
        mainPage.openCart();

        cartPage.productCardComponent.clickProductLink(backpack);

        assertThat(productPage.productPageUrl()).contains("inventory-item.html");
        assertThat(productPage.itemNameText()).isEqualTo(backpack);
    }
}