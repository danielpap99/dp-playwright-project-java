package Playwright.project.ToolShop.tests;

import Playwright.project.ToolShop.UserAPIClient;
import Playwright.project.ToolShop.base.TestBase;
import Playwright.project.ToolShop.records.User;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginTestsUsingAPI extends TestBase {

    @Test
    void shouldLoginWithRegisteredUser() {
        User user = User.randomUser();
        UserAPIClient userAPIClient = new UserAPIClient(page);
        userAPIClient.registerUser(user);

        String email = user.email();
        String password = user.password();

        mainPage.navigateToSignInPage();
        loginPage.login(email, password);

        assertThat(mainPage.userIsLoggedIn()).isTrue();
    }
}
