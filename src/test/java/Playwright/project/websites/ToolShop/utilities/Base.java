package Playwright.project.websites.ToolShop.utilities;

import Playwright.project.websites.ToolShop.pages.MainPage;
import com.microsoft.playwright.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;


public class Base {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    protected SoftAssertions softly;
    protected Page page;

    // Pages that should be available in all tests
    protected MainPage mainPage;

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
                        .setViewportSize(1500, 955) // set as large as your screen
        );
    }

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
        softly = new SoftAssertions();
        mainPage = new MainPage(page);
    }

    @AfterEach
    public void tearDownTest() {
        softly.assertAll();
    }

    @AfterAll
    public static void TearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    public void goToContactPage() {
        mainPage.clickContactMenuOption();
    }
}