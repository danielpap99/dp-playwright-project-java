package Playwright.project.SwagLabs.tests;

import Playwright.project.SwagLabs.base.TestBase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InventoryTests extends TestBase {

    @Test
    @Tag("Smoke")
    void inventoryPageLoadsCorrectly() {
        goToMainPage();

        assertTrue(mainPage.isOnInventoryPage(), "Inventory page loads correctly");
        assertTrue(mainPage.productCount() >= 6, "There should be at least 6 products listed");
    }

    // This test demonstrates how "softly" assertions work - they don't stop the test, they collect all failures at the end
    @Test
    @Tag("Stage2")
    void shoppingCartQuantityUpdatesCorrectly() {
        goToMainPage();

        mainPage.productCardComponent.addProductToCart("Sauce Labs Backpack");
        softly.assertThat(mainPage.shoppingCartQuantityText()).isEqualTo("1"); //soft assertions

        mainPage.productCardComponent.addProductToCart("Sauce Labs Bike Light");
        softly.assertThat(mainPage.shoppingCartQuantityText()).isEqualTo("2");

        mainPage.productCardComponent.addProductToCart("Sauce Labs Onesie");
        softly.assertThat(mainPage.shoppingCartQuantityText()).isEqualTo("3");

        mainPage.productCardComponent.removeProductFromCart("Sauce Labs Backpack");
        mainPage.productCardComponent.removeProductFromCart("Sauce Labs Bike Light");
        mainPage.productCardComponent.removeProductFromCart("Sauce Labs Onesie");

        assertAll(
                () -> assertThat(mainPage.shoppingCartQuantityText()).isEqualTo(""),
                () -> assertThat(mainPage.productCardComponent.addToCartButtonVisible("Sauce Labs Backpack")).isTrue(),
                () -> assertThat(mainPage.productCardComponent.addToCartButtonVisible("Sauce Labs Bike Light")).isTrue(),
                () -> assertThat(mainPage.productCardComponent.addToCartButtonVisible("Sauce Labs Onesie")).isTrue()
        );
    }

    @Test
    @Tag("Stage2")
    void alphabeticalSortWorksCorrectly() {
        goToMainPage();

        assertAll(
                () -> assertThat(mainPage.activeSortOptionText()).isEqualTo("Name (A to Z)"),
                () -> Assertions.assertThat(mainPage.productNames()).isSortedAccordingTo(Comparator.naturalOrder())
        );

        mainPage.selectSortOption("za");

        assertAll(
                () -> assertThat(mainPage.firstProductName()).isEqualTo("Test.allTheThings() T-Shirt (Red)"),
                () -> Assertions.assertThat(mainPage.productNames()).isSortedAccordingTo(Comparator.reverseOrder())
        );

        mainPage.selectSortOption("az");

        assertAll(
                () -> assertThat(mainPage.firstProductName()).isEqualTo("Sauce Labs Backpack"),
                () -> Assertions.assertThat(mainPage.productNames()).isSortedAccordingTo(Comparator.naturalOrder())
        );
    }

    @Test
    @Tag("Stage2")
    void pricingSortWorksCorrectly() {
        goToMainPage();

        mainPage.selectSortOption("lohi");

        assertAll(
                () -> assertThat(mainPage.firstProductName()).isEqualTo("Sauce Labs Onesie"),
                () -> assertThat(mainPage.firstProductPrice()).isEqualTo("$7.99"),
                () -> Assertions.assertThat(mainPage.productPrices()).isSortedAccordingTo(Comparator.naturalOrder())
        );

        mainPage.selectSortOption("hilo");

        assertAll(
                () -> assertThat(mainPage.firstProductName()).isEqualTo("Sauce Labs Fleece Jacket"),
                () -> assertThat(mainPage.firstProductPrice()).isEqualTo("$49.99"),
                () -> Assertions.assertThat(mainPage.productPrices()).isSortedAccordingTo(Comparator.reverseOrder())
        );
    }
}
