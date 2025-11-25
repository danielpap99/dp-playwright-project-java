package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class LoginTests extends Base {

    @Test
    @Tag("Smoke")
    void loginPageLoadsCorrectly() {
        assertAll(
                () -> assertThat(loginPage.usernameFieldIsVisible()).isTrue(),
                () -> assertThat(loginPage.passwordFieldIsVisible()).isTrue(),
                () -> assertThat(loginPage.loginButtonIsVisible()).isTrue()
        );
    }

    @Test
    @Tag("Smoke")
    void successfulLogin() {
        loginPage.successfullyLogin();

        assertThat(mainPage.isOnInventoryPage()).isTrue();
    }

    @Test
    @Tag("Stage1")
    void loginFailsWithValidUsernameAndInvalidPassword() {
        loginPage.enterValidUsername();
        loginPage.enterPassword("definitely_wrong_password");
        loginPage.clickLoginButton();

        assertAll(
                () -> assertThat(loginPage.errorMessageIsVisible()).isTrue(),
                () -> assertThat(loginPage.errorMessageText()).isEqualTo("Epic sadface: Username and password do not match any user in this service"),
                () -> assertThat(mainPage.isOnInventoryPage()).isFalse()
        );
    }

    @Test
    @Tag("Stage1")
    void loginFailsForLockedOutUserWithValidPassword() {
        loginPage.enterUsername("locked_out_user");
        loginPage.enterValidPassword();
        loginPage.clickLoginButton();
        
        assertAll(
                () -> assertThat(loginPage.errorMessageIsVisible()).isTrue(),
                () -> assertThat(loginPage.errorMessageText()).isEqualTo("Epic sadface: Sorry, this user has been locked out."),
                () -> assertThat(mainPage.isOnInventoryPage()).isFalse()
        );
    }

    @Test
    @Tag("Stage1")
    void userNameAndPasswordRequired() {
        loginPage.enterUsername("standard_user");
        loginPage.clickLoginButton();

        assertAll(
                () -> assertThat(loginPage.errorMessageIsVisible()).isTrue(),
                () -> assertThat(loginPage.errorMessageText()).isEqualTo("Epic sadface: Password is required"),
                () -> assertThat(mainPage.isOnInventoryPage()).isFalse()
        );

        loginPage.closeErrorMessage();
        loginPage.enterUsername("");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        assertAll(
                () -> assertThat(loginPage.errorMessageIsVisible()).isTrue(),
                () -> assertThat(loginPage.errorMessageText()).isEqualTo("Epic sadface: Username is required"),
                () -> assertThat(mainPage.isOnInventoryPage()).isFalse()
        );
    }
}

