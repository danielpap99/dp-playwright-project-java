package Playwright.project.websites.ToolShop.tests;

import Playwright.project.websites.ToolShop.utilities.Base;
import com.microsoft.playwright.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class MainPageProductsTests extends Base {


    @Test
    @Tag("Smoke")
    void productNamesAndPricesAreDisplayed() {
        List<String> productNames = mainPage.productNames();
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

        List<String> productNames = mainPage.productNames();

        for (String name : productNames) {
            softly.assertThat(name).contains("pliers");
        }
    }

    @Test
    @Tag("Stage2")
    @Description("Wait for api response") //https://api.practicesoftwaretesting.com/products?page=0&sort=name,desc&between=price,1,100&is_rental=false
    void alphabeticalSortWorksCorrectly() {
        page.waitForResponse("**/products?*sort=name,desc*", () -> {
            mainPage.selectSortOption("name,desc");
        });

        assertThat(mainPage.productNames()).isSortedAccordingTo(Comparator.reverseOrder());
    }
}
