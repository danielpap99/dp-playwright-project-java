package Playwright.project.websites.SwagLabs.tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import Playwright.project.websites.SwagLabs.pages.LoginPage;
import Playwright.project.websites.SwagLabs.pages.MainPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTests {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;
    LoginPage loginPage;
    MainPage mainPage;

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
        mainPage = new MainPage(page);
    }

    @AfterAll
    public static void TearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    @Test
    @Tag("Smoke")
    void loginPageLoadsCorrectly() {

        assertTrue(loginPage.usernameFieldIsVisible(), "username field should be visible on login page");
        assertTrue(loginPage.passwordFieldIsVisible(), "password field should be visible on login page");
        assertTrue(loginPage.loginButtonIsVisible(), "login button should be visible on login page");
    }

    @Test
    @Tag("Smoke")
    void successfulLogin() {

        loginPage.successfullyLogin();

        assertTrue(mainPage.shoppingCartIconIsVisible(), "shopping cart is visible on screen");
        assertTrue(mainPage.headerIsVisible(),  "the header Swag Labs is visible on screen");
        assertTrue(mainPage.headerText().contains("Swag Labs"));
    }

    @Test
    @Tag("Stage1")
    void loginFailsWithValidUsernameAndInvalidPassword() {

        loginPage.enterValidUsername();
        loginPage.enterPassword("definitely_wrong_password");
        loginPage.clickLoginButton();

        assertAll(
                () -> assertTrue(loginPage.errorMessageIsVisible(), "error message should be visible"),
                () -> assertEquals("Epic sadface: Username and password do not match any user in this service", loginPage.errorMessageText())
        );
    }

    @Test
    @Tag("Stage1")
    void loginFailsForLockedOutUserWithValidPassword() {

        loginPage.enterUsername("locked_out_user");
        loginPage.enterValidPassword();
        loginPage.clickLoginButton();
        
        assertAll(
                () -> assertTrue(loginPage.errorMessageIsVisible(), "error message should be visible"),
                () -> assertEquals("Epic sadface: Sorry, this user has been locked out.", loginPage.errorMessageText())
        );
    }

}

