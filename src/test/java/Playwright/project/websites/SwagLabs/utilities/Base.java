package Playwright.project.websites.SwagLabs.utilities;


import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import Playwright.project.websites.SwagLabs.pages.LoginPage;
import Playwright.project.websites.SwagLabs.pages.MainPage;


public class Base {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected Page page;

    // Pages that should be available in all tests
    protected LoginPage loginPage;
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
                        .setViewportSize(1500, 955)
        );
    }

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
        page.navigate("https://www.saucedemo.com/");
        loginPage = new LoginPage(page);
        mainPage = new MainPage(page);
    }

    public void goToMainPage() {
        loginPage.successfullyLogin();
    }

    @AfterAll
    public static void TearDown() {
        browser.close();
        playwright.close();
    }

}
