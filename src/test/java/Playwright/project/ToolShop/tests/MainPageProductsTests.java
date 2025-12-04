package Playwright.project.ToolShop.tests;

import Playwright.project.ToolShop.base.TestBase;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import java.util.Comparator;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class MainPageProductsTests extends TestBase {


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
        softly.assertThat(mainPage.productImageTitles()).isNotEmpty();
    }

    @Test
    void pageTitleIsShown() {
        String title = page.title();

        assertThat(title).contains("Practice Software Testing");
    }

    @Test
    void searchingForSpecificProductWorksCorrectly() {
        mainPage.searchForProduct("Combination pliers");

        assertAll(
                () -> assertThat(mainPage.searchCaptionText()).contains("Searched for: Combination pliers"),
                () -> assertThat(mainPage.productNames()).contains(" Combination Pliers "),
                () -> assertThat(mainPage.searchResultCount()).isEqualTo(1)
        );
    }

    @Test
    @Tag("Stage2")
    void searchReturnsRelevantProducts() {
        mainPage.searchForProduct("Pliers");

        List<String> productNames = mainPage.productNames();

        for (String name : productNames) {
            softly.assertThat(name.toLowerCase()).contains("pliers");
        }
    }

    @Test
    @Tag("Stage2")
    @Description("Wait for api response") //https://api.practicesoftwaretesting.com/products?page=0&sort=name,desc&between=price,1,100&is_rental=false
    void alphabeticalSortWorksCorrectly() {
        page.waitForResponse("**/products?*sort=name,desc*", () ->
            mainPage.selectSortOption("name,desc")
        );

        assertThat(mainPage.productNames()).isSortedAccordingTo(Comparator.reverseOrder());
    }
}
