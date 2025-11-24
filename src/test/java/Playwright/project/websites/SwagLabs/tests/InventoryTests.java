package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
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
        assertEquals("Name (A to Z)", mainPage.activeSortOptionText());

        mainPage.selectSortOption("za");
        assertThat(mainPage.firstProductName()).isEqualTo("Test.allTheThings() T-Shirt (Red)");

        mainPage.selectSortOption("az");
        assertThat(mainPage.firstProductName()).isEqualTo("Sauce Labs Backpack");
    }

    @Test
    @Tag("Stage2")
    void pricingSortWorksCorrectly() {

        goToMainPage();

        mainPage.selectSortOption("lohi");

        assertAll(
                () -> assertThat(mainPage.firstProductName()).isEqualTo("Sauce Labs Onesie"),
                () -> assertThat(mainPage.firstProductPrice()).isEqualTo("$7.99")
        );

        mainPage.selectSortOption("hilo");

        assertAll(
                () -> assertThat(mainPage.firstProductName()).isEqualTo("Sauce Labs Fleece Jacket"),
                () -> assertThat(mainPage.firstProductPrice()).isEqualTo("$49.99")
        );
    }
}
