package Playwright.project.websites.ToolShop.tests;

import Playwright.project.websites.ToolShop.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;


public class MainPageProductsTests extends Base {


    @Test
    @Tag("Smoke")
    void productNamesAndPricesAreDisplayed() {
        List<String> productNames = mainPage.productNameText();
        List<String> productPrices = mainPage.productPrices();

        assertAll(
                () -> softly.assertThat(productNames).isNotEmpty(),
                () -> softly.assertThat(productPrices).isNotEmpty()
        );
    }

    @Test
    @Tag("Stage2")
    void productImagesAreDisplayed() {
        List<String> productImageTitles = mainPage.productImageTitles();

        softly.assertThat(productImageTitles).isNotEmpty();
    }

    @Test
    @Tag("Stage2")
    void searchReturnsRelevantProducts() {
        mainPage.searchForProduct("Pliers");

        List<String> productNames = mainPage.productNameText();

        for (String name : productNames) {
            softly.assertThat(name).contains("pliers");
        }
    }
}
