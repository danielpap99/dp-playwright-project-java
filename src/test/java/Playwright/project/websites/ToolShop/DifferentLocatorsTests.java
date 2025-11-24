package Playwright.project.websites.ToolShop;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class DifferentLocatorsTests {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    Page page;

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
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterAll
    public static void TearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    @DisplayName("By data-test id")
    @Test
    void findElementUsingDataTestId() {

        Locator signInButton = page.getByTestId("nav-sign-in"); //using data-test id to locate element
        Locator loginButton = page.locator("[data-test='login-submit']"); //behind the scenes of getByTestId

        signInButton.click();

        assertThat(loginButton).isVisible();
    }

    @DisplayName("By CSS Selector")
    @Test
    void findElementUsingCSSSelector() {

        Locator contactButton = page.locator("[data-test='nav-contact']");
        Locator firstName = page.locator("#first_name"); //using id to locate input fields
        Locator lastName = page.locator("#last_name");
        Locator emailAlert = page.locator("#email_alert");
        Locator sendButton = page.locator(".btnSubmit"); // using class to locate button

        contactButton.click();
        firstName.fill("Example");
        lastName.fill("Example");
        sendButton.click();

        assertThat(emailAlert).isVisible();
    }

    @DisplayName("By attribute")
    @Test
    void findElementUsingAttribute() {

        Locator contactButton = page.locator("[data-test='nav-contact']");
        Locator firstName = page.locator("[placeholder='Your first name *']"); // using placeholder attribute
        Locator lastName = page.locator("[placeholder='Your last name *']"); // using placeholder attribute
        Locator emailAlert = page.locator("#email_alert");
        Locator sendButton = page.locator(".btnSubmit");

        contactButton.click();
        firstName.fill("Example");
        lastName.fill("Example");
        sendButton.click();

        assertThat(emailAlert).isVisible();
    }

    @DisplayName("By text value")
    @Test
    void findElementUsingTextValue() {

        Locator boltCutters = page.getByText("Bolt Cutters"); //use text to select an element
        Locator hardwareTag = page.getByText("MightyCraft Hardware");
        Locator productTitle = page.locator("[data-test='product-name']");

        boltCutters.click();

        assertThat(productTitle).isVisible();
        assertThat(productTitle).hasText("Bolt Cutters"); //fluent assertion style
        assertThat(hardwareTag).isVisible(); // different approach, check if visible via text
    }

    @DisplayName("By alt text")
    @Test
    void findElementUsingAltText() {

        Locator boltCutters = page.getByAltText("Combination Pliers"); //use alt text to select an element
        Locator productTitle = page.locator("[data-test='product-name']");

        boltCutters.click();

        assertThat(productTitle).isVisible();
        assertThat(productTitle).hasText("Combination Pliers");
    }

    @DisplayName("By title")
    @Test
    void findElementUsingTitle() {

        Locator boltCutters = page.getByText("Bolt Cutters");
        Locator logoHoverMessage = page.getByTitle("Practice Software Testing - Toolshop"); //use title to select an element
        Locator searchButton = page.locator("[data-test='search-submit']");

        boltCutters.click();
        logoHoverMessage.click();

        assertThat(searchButton).isVisible();
    }

    @DisplayName("By placeholder")
    @Test
    void findElementUsingPlaceHolder() {

        Locator contactButton = page.locator("[data-test='nav-contact']");
        Locator firstName = page.getByPlaceholder("Your first name"); //using placeholder to locate input field
        Locator lastName = page.getByPlaceholder("Your last name");

        contactButton.click();
        firstName.fill("Papa");
        lastName.fill("Johns");
    }

    @DisplayName("By role")
    @Test
    void findElementUsingRole() {

        Locator signInButton = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Sign in")); //using role to locate element
        Locator loginButton = page.locator("[data-test='login-submit']");

        signInButton.click();

        assertThat(loginButton).isVisible();
    }

    @DisplayName("Nested")
    @Test
    void findElementsUsingNestedMethod() {

        Locator contactMenuOption = page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions()
                .setName("Main Menu"))
                .getByText("Contact"); //using the menubar to locate elements within
        Locator sendButton = page.locator(".btnSubmit");

        contactMenuOption.click();

        assertThat(sendButton).isVisible();
    }

    @DisplayName("Filtering")
    @Test
    void findElementsUsingFilteringMethod() {

        Locator searchField = page.getByTestId("search-query");
        Locator searchButton = page.getByTestId("search-submit");

        List<String> allProducts = page.locator(".card")
                        .filter(new Locator.FilterOptions().setHas(page.getByText("Out of stock")))
                        .getByTestId("product-name")
                        .allTextContents();

        searchField.fill("Pliers");
        searchButton.click();

        assertThat(allProducts).contains(" Long Nose Pliers ");
    }

}
