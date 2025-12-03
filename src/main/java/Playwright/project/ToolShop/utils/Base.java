package Playwright.project.ToolShop.utils;

import Playwright.project.ToolShop.pages.LoginPage;
import Playwright.project.ToolShop.pages.MainPage;
import com.microsoft.playwright.*;


public class Base {

    private static Playwright playwright;
    private static Browser browser;
    protected static BrowserContext browserContext;
    protected Page page;

    // Pages that should be available in all tests
    protected MainPage mainPage;
    protected LoginPage loginPage;

    //One shared browser context, making test execution faster
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
                        .setViewportSize(1500, 955) // set as large as your screen
        );
    }

    public void setUpContext() {
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
        mainPage = new MainPage(page);
        loginPage = new LoginPage(page);
    }

    public static void tearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    public void goToContactPage() {
        mainPage.clickContactMenuOption();
    }
}