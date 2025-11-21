package Playwright.project.websites.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // Locators
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator errorMessage;

    // Constructor
    public LoginPage(Page page) {

        this.page = page;

        usernameField = page.locator("#user-name");
        passwordField = page.locator("#password");
        loginButton = page.locator("#login-button");
        errorMessage = page.locator("[data-test='error']");
    }

    // Actions
    public void enterValidUsername() {
        usernameField.fill("standard_user");
    }

    public void enterUsername(String username) {
        usernameField.fill(username);
    }

    public void enterValidPassword() {
        passwordField.fill("secret_sauce");
    }

    public void enterPassword(String password) {
        passwordField.fill(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void successfullyLogin() {
        enterValidUsername();
        enterValidPassword();
        clickLoginButton();
    }


    //Assertions
    public boolean usernameFieldIsVisible() {
        return usernameField.isVisible();
    }

    public boolean passwordFieldIsVisible() {
        return passwordField.isVisible();
    }

    public boolean loginButtonIsVisible() {
        return loginButton.isVisible();
    }

    public boolean errorMessageIsVisible() {
        return errorMessage.isVisible();
    }

    public String errorMessageText() {
        return errorMessage.innerText();
    }

}

