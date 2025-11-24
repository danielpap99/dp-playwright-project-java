package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OrderFlowTests extends Base {

    // This test demonstrates how "softly" assertions work - they don't stop the test, they collect all failures at the end
    @Test
    @Tag("Stage2")
    void shoppingCartQuantityUpdatesCorrectly() {

        goToMainPage();

        mainPage.addProductToCart("Sauce Labs Backpack");
        softly.assertThat(mainPage.shoppingCartQuantityText()).isEqualTo("1"); //soft assertions

        mainPage.addProductToCart("Sauce Labs Bike Light");
        softly.assertThat(mainPage.shoppingCartQuantityText()).isEqualTo("2");

        mainPage.addProductToCart("Sauce Labs Onesie");
        softly.assertThat(mainPage.shoppingCartQuantityText()).isEqualTo("3");

        mainPage.removeProductFromCart("Sauce Labs Backpack");
        mainPage.removeProductFromCart("Sauce Labs Bike Light");
        mainPage.removeProductFromCart("Sauce Labs Onesie");

        assertAll(
                () -> assertThat(mainPage.shoppingCartQuantityText()).isEqualTo(""),
                () -> assertThat(mainPage.addToCartButtonVisible("Sauce Labs Backpack")).isTrue(),
                () -> assertThat(mainPage.addToCartButtonVisible("Sauce Labs Bike Light")).isTrue(),
                () -> assertThat(mainPage.addToCartButtonVisible("Sauce Labs Onesie")).isTrue()
        );
    }
}
