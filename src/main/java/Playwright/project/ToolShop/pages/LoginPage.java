package Playwright.project.ToolShop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class LoginPage {

    private final Page page;

    // Locators
    private final Locator emailField;
    private final Locator passwordField;
    private final Locator loginButton;


    // Constructor
    public LoginPage(Page page) {

        this.page = page;

        emailField = page.getByTestId("email");
        passwordField = page.getByTestId("password");
        loginButton = page.getByTestId("login-submit");
    }

    // Actions
    public void login(String email, String password) {
        emailField.fill(email);
        passwordField.fill(password);
        loginButton.click();
    }


    //Assertions

}
