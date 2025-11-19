package Playwright.project.ToolShop;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

public class FirstPlaywrightTests {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void setUpBrowser() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setSlowMo(500)
        );

        browserContext = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1500, 955) // set as large as your screen
        );
    }

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterAll
    public static void tearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    @Test
    void pageTitleIsShown() {

        String title = page.title();

        Assertions.assertTrue(title.contains("Practice Software Testing"));
    }

    @Test
    void searchBoxReturnsResults() {
        page.locator("[data-test='search-query']").fill("Combination pliers");
        page.locator("[data-test='search-submit']").click();

        String searchCaption = page.locator("[data-test='search-caption']").textContent();
        String combiPlierProduct = page.locator("[data-test='product-name']").textContent();
        int searchResults = page.locator(".card").count();

        Assertions.assertTrue(searchCaption.contains("Searched for: Combination pliers"));
        Assertions.assertTrue(combiPlierProduct.contains("Combination Pliers"));
        Assertions.assertEquals(1, searchResults);
    }



}