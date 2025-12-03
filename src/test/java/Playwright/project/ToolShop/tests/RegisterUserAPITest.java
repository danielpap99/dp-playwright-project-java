package Playwright.project.ToolShop.tests;

import Playwright.project.ToolShop.records.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@UsePlaywright
public class RegisterUserAPITest {

    private APIRequestContext request;
    private Gson gson = new Gson();
    protected SoftAssertions softly;

    @BeforeEach
    void setup(Playwright playwright) {
        request = playwright.request().newContext(
                new APIRequest.NewContextOptions().setBaseURL("https://api.practicesoftwaretesting.com")
        );

        softly = new SoftAssertions();
    }

    @AfterEach
    void tearDown() {
        if (request != null) {
            request.dispose();
        }
        softly.assertAll();
    }

    @Test
    void shouldRegisterUser() {
        User validUser = User.randomUser();

        var response = request.post("/users/register",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(validUser)
        );

        String responseBody = response.text();
        User createdUser = gson.fromJson(responseBody, User.class);
        JsonObject responseObject = gson.fromJson(responseBody, JsonObject.class);

        assertAll(
                () -> assertThat(response.status()).isEqualTo(201),
                () -> assertThat(createdUser).isEqualTo(validUser.withPassword(null)), // created user matches specified user without password
                () -> assertThat(responseObject.has("password")).isFalse(), // should not contain password in response
                () -> assertThat(responseObject.get("id").getAsString()).isNotEmpty(), // registered user should have an id
                () -> assertThat(response.headers().get("content-type")).contains("application/json")
        );
    }

    @Test
    void firstNameIsMandatory() {
        User newUserWithNoName = User.randomUser().withoutFirstName();

        var response = request.post("/users/register",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(newUserWithNoName)
        );

        JsonObject responseObject = gson.fromJson(response.text(), JsonObject.class);
        String errorMessage = responseObject.get("first_name").getAsString();

        assertAll(
                () -> assertThat(response.status()).isEqualTo(422),
                () -> assertThat(responseObject.has("first_name")).isTrue(),
                () -> assertThat(errorMessage).isEqualTo("The first name field is required.")
        );
    }
}