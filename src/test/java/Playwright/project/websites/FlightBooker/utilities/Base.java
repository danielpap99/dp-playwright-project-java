package Playwright.project.websites.FlightBooker.utilities;

import Playwright.project.websites.ClearFolderExtension;
import Playwright.project.websites.FlightBooker.pages.FlightsPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ClearFolderExtension.class)

public class Base {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected Page page;

    // Pages that should be available in all tests
    protected FlightsPage flightsPage;

    @BeforeAll
    public static void SetUpBrowser() {
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

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
        page.navigate("https://rahulshettyacademy.com/dropdownsPractise/");
        flightsPage = new FlightsPage(page);
    }

    @AfterAll
    public static void TearDown() {
        browser.close();
        playwright.close();
    }

}
