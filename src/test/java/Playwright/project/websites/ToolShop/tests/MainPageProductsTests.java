package Playwright.project.websites.ToolShop.tests;

import Playwright.project.websites.ToolShop.pages.ContactPage;
import Playwright.project.websites.ToolShop.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.util.List;


public class MainPageProductsTests extends Base {

    private ContactPage contactPage;

    @Test
    @Tag("Smoke")
    void productNamesAreDisplayed() {
        List<String> productNames = mainPage.productNameText();

        softly.assertThat(productNames).isNotEmpty();
    }

    @Test
    @Tag("Stage1")
    void productImagesAreDisplayed() {
        List<String> productImageTitles = mainPage.productImageTitles();

        softly.assertThat(productImageTitles).isNotEmpty();
    }

    @Test
    void searchReturnsRelevantProducts() {
        mainPage.searchForProduct("Pliers");

        List<String> productNames = mainPage.productNameText();

        for (String name : productNames) {
            softly.assertThat(name).contains("pliers");
        }
    }
}
