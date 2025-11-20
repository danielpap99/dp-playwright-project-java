package Playwright.project.SwagLabs.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import Playwright.project.SwagLabs.pages.LoginPage;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class LoginTests {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;
    LoginPage loginPage;

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
        page.navigate("https://www.saucedemo.com/");
        loginPage = new LoginPage(page);
    }

    @AfterAll
    public static void TearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    @Test
    @Tag("Smoke")
    void mainPageLoadsCorrectly() {

        loginPage.successfullyLogin();

        assertTrue(loginPage.shoppingCartIconIsVisible(), "shopping cart is visible on screen");
        assertTrue(loginPage.headerIsVisible(),  "the header Swag Labs is visible on screen");
        assertTrue(loginPage.headerText().contains("Swag Labs"));
    }

}

