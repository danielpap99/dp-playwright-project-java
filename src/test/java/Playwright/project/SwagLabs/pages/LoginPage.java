package Playwright.project.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;

    // Locators
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator shoppingCartIcon;
    private final Locator header;

    // Constructor
    public LoginPage(Page page) {

        this.page = page;

        usernameField = page.locator("#user-name");
        passwordField = page.locator("#password");
        loginButton = page.locator("#login-button");
        shoppingCartIcon = page.locator("[data-test='shopping-cart-link']");
        header = page.locator("[data-test='primary-header']");
    }

    // Methods
    public void enterUsername() {
        usernameField.fill("standard_user");
    }

    public void enterPassword() {
        passwordField.fill("secret_sauce");
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void successfullyLogin() {
        enterUsername();
        enterPassword();
        clickLoginButton();
    }






    //Asserts
    public boolean shoppingCartIconIsVisible() {
        return shoppingCartIcon.isVisible();
    }

    public boolean headerIsVisible() {
        return header.isVisible();
    }

    public String headerText() {
        return header.innerText();
    }

}

