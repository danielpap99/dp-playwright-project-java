package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.utilities.Base;
import com.microsoft.playwright.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class InventoryTests extends Base {

    @Test
    @Tag("Smoke")
    void inventoryPageLoadsCorrectly() {
        goToMainPage();

        assertTrue(mainPage.isOnInventoryPage(), "Inventory page loads correctly");
        assertTrue(mainPage.productCount() >= 6, "There should be at least 6 products listed");
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
