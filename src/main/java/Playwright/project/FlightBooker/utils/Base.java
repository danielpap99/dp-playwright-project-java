package Playwright.project.FlightBooker.utils;

import Playwright.project.FlightBooker.pages.FlightsPage;
import com.microsoft.playwright.*;

public class Base {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected Page page;

    // Pages that should be available in all tests
    protected FlightsPage flightsPage;

    public static void setUpBrowser() {
        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test"); //configure getByTestId to use "data-test"

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(100)
        );

        browserContext = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1500, 955)
        );
    }


    public void setUpContext() {
        page = browserContext.newPage();
        page.navigate("https://rahulshettyacademy.com/dropdownsPractise/");
        flightsPage = new FlightsPage(page);
    }

    public static void tearDown() {
        browser.close();
        playwright.close();
    }
}
