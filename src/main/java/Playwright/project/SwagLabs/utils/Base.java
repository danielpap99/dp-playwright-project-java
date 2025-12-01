package Playwright.project.SwagLabs.utils;

import com.microsoft.playwright.*;
import Playwright.project.SwagLabs.pages.LoginPage;
import Playwright.project.SwagLabs.pages.MainPage;

public class Base {

    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;
    protected Page page;


    // Pages that should be available in all tests
    protected LoginPage loginPage;
    protected MainPage mainPage;

    //Create browser context for each test, making them completely isolated to prevent interference
    public void setUpBrowser() {
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
        loginPage = new LoginPage(page);
        mainPage = new MainPage(page);
    }

    public void goToMainPage() {
        loginPage.successfullyLogin();
    }

    // tear-down method
    public void tearDownTest() {
        browserContext.close();
        browser.close();
        playwright.close();
    }
}
