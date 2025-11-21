package Playwright.project.websites.ToolShop;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.*;
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

    @Test
    void findElementUsingDataTestId() {

        Locator signInButton = page.getByTestId("nav-sign-in"); //using data-test id to locate element
        Locator loginButton = page.locator("[data-test='login-submit']"); //behind the scenes of getByTestId

        signInButton.click();

        assertThat(loginButton).isVisible();
    }

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

    @Test
    void findElementUsingAltText() {

        Locator boltCutters = page.getByAltText("Combination Pliers"); //use alt text to select an element
        Locator productTitle = page.locator("[data-test='product-name']");

        boltCutters.click();

        assertThat(productTitle).isVisible();
        assertThat(productTitle).hasText("Combination Pliers");
    }

    @Test
    void findElementUsingTitle() {

        Locator boltCutters = page.getByText("Bolt Cutters");
        Locator logoHoverMessage = page.getByTitle("Practice Software Testing - Toolshop"); //use title to select an element
        Locator searchButton = page.locator("[data-test='search-submit']");

        boltCutters.click();
        logoHoverMessage.click();

        assertThat(searchButton).isVisible();
    }

    @Test
    void findElementUsingPlaceHolder() {

        Locator contactButton = page.locator("[data-test='nav-contact']");
        Locator firstName = page.getByPlaceholder("Your first name"); //using placeholder to locate input field
        Locator lastName = page.getByPlaceholder("Your last name");

        contactButton.click();
        firstName.fill("Papa");
        lastName.fill("Johns");
    }

    @Test
    void findElementUsingRole() {

        Locator signInButton = page.getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName("Sign in")); //using role to locate element
        Locator loginButton = page.locator("[data-test='login-submit']");

        signInButton.click();

        assertThat(loginButton).isVisible();
    }




}
