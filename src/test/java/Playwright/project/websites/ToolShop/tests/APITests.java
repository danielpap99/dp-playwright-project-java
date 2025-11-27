package Playwright.project.websites.ToolShop.tests;

import Playwright.project.websites.ClearFolderExtension;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


@ExtendWith(ClearFolderExtension.class) // this is for Allure to delete the results before each test run

public class APITests {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

    @BeforeAll
    public static void setUpBrowser() {
        playwright = Playwright.create();

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

    @BeforeEach
    public void setUp() {
        page = browserContext.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterAll
    public static void tearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    @Nested
    class MakingAPICalls {
        record Product(String name, Double price) {}

        private static APIRequestContext requestContext;

        @BeforeAll
        public static void setupRequestContext() {
            requestContext = playwright.request().newContext(
                    new APIRequest.NewContextOptions()
                            .setBaseURL("https://api.practicesoftwaretesting.com/")
                            .setExtraHTTPHeaders(new HashMap<>() {{
                                put("Accept", "application/json");
                            }})
            );
        }

        @DisplayName("Check presence of known products")
        @ParameterizedTest(name = "Checking product {0}")
        @MethodSource("products")
        void checkKnownProducts(Product product) {
            page.locator("[data-test='search-query']").fill(product.name());
            page.locator("[data-test='search-submit']").click();

            // Check that the product appears with the correct name and price
            Locator productCard = page.locator(".card")
                    .filter(
                            new Locator.FilterOptions()
                                    .setHasText(product.name)
                                    .setHasText(Double.toString(product.price))
                    );
            assertThat(productCard).isVisible();
        }

        static Stream<Product> products() {
            APIResponse response = requestContext.get("/products?page=2");
            assertThat(response.status()).isEqualTo(200);

            JsonObject jsonObject = new Gson().fromJson(response.text(), JsonObject.class);
            JsonArray data = jsonObject.getAsJsonArray("data");

            return data.asList().stream()
                    .map(jsonElement -> {
                        JsonObject productJson = jsonElement.getAsJsonObject();
                        return new Product(
                                productJson.get("name").getAsString(),
                                productJson.get("price").getAsDouble()
                        );
                    });
        }
    }



}
