package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTests extends Base {

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

        assertTrue(mainPage.isOnInventoryPage(), "inventory page loads correctly");
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

    @Test
    @Tag("Stage1")
    void userNameAndPasswordRequired() {

        loginPage.enterUsername("standard_user");
        loginPage.clickLoginButton();

        assertAll(
                () -> assertTrue(loginPage.errorMessageIsVisible(), "error message should be visible"),
                () -> assertEquals("Epic sadface: Password is required", loginPage.errorMessageText()),
                () -> assertFalse(mainPage.isOnInventoryPage())
        );

        loginPage.closeErrorMessage();
        loginPage.enterUsername("");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        assertAll(
                () -> assertTrue(loginPage.errorMessageIsVisible(), "error message should be visible"),
                () -> assertEquals("Epic sadface: Username is required", loginPage.errorMessageText())
        );
    }

}

