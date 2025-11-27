package Playwright.project.websites.SwagLabs.utilities;

import Playwright.project.websites.ClearFolderExtension;
import com.microsoft.playwright.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import Playwright.project.websites.SwagLabs.pages.LoginPage;
import Playwright.project.websites.SwagLabs.pages.MainPage;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ClearFolderExtension.class) // this is for Allure to delete the results before each test run

public class Base {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected Page page;
    protected SoftAssertions softly;

    // Pages that should be available in all tests
    protected LoginPage loginPage;
    protected MainPage mainPage;

    @BeforeEach //Create browser context for each test, making them completely isolated to prevent interference
    public void setUp() {
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

        page = browserContext.newPage();
        page.navigate("https://www.saucedemo.com/");
        softly = new SoftAssertions();
        loginPage = new LoginPage(page);
        mainPage = new MainPage(page);
    }

    public void goToMainPage() {
        loginPage.successfullyLogin();
    }

    @AfterEach
    public void tearDownTest() {
        softly.assertAll();
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
