package Playwright.project.websites.FlightBooker.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import com.microsoft.playwright.Page;
import Playwright.project.websites.FlightBooker.pages.FlightsPage;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class OneWayTrips {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;
    FlightsPage flightsPage;

    @BeforeAll
    public static void SetUpBrowser() {
        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test"); //configure getByTestId to use "data-test"

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(1000)
        );

        browserContext = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1500, 955) // set as large as your screen
        );
    }

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
        page.navigate("https://rahulshettyacademy.com/dropdownsPractise/#");
        flightsPage = new FlightsPage(page);
    }

    @AfterAll
    public static void TearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    @Test
    @Tag("Smoke")
    void mainPageLoadsCorrectly() {

        assertTrue(flightsPage.departureCityIsVisible(), "Departure City field is visible on screen");
        assertTrue(flightsPage.arrivalCityIsVisible(),  "Arrival City field is visible on screen");
        assertTrue(flightsPage.submitButtonIsVisible(), "Submit button is visible on screen");
    }


}
