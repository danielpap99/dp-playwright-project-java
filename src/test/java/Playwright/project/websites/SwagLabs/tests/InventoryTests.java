package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

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

    }
}
