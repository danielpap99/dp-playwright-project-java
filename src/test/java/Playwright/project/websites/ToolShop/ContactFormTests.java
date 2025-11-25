package Playwright.project.websites.ToolShop;

import com.microsoft.playwright.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class ContactFormTests {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    protected SoftAssertions softly;
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
        softly = new SoftAssertions();
    }

    @AfterEach
    public void tearDownTest() {
        softly.assertAll();
    }

    @AfterAll
    public static void TearDown() {
        browser.close(); // shut down browser
        playwright.close(); //shut down playwright instance
    }

    @DisplayName("Dropdown menu")
    @Test
    void subjectCanBeSelected() {
        Locator contactButton = page.getByTestId("nav-contact");
        Locator subjectDropdown = page.getByTestId("subject");

        contactButton.click();
        page.getByTestId("subject").selectOption("warranty");

        assertThat(subjectDropdown).hasValue("warranty");
    }

    @DisplayName("Dropdown menu")
    @Test
    void txtFileCanBeAttached() {
        Locator contactButton = page.getByTestId("nav-contact");
        Locator attachment = page.getByTestId("attachment");

        contactButton.click();
        attachment.setInputFiles(Paths.get("src/test/resources/data/testdata.txt"));

        String uploadedFileName = attachment.evaluate("el => el.files[0].name").toString();
        assertThat(uploadedFileName).isEqualTo("testdata.txt");
    }

    @DisplayName("Dropdown menu")
    @Test
    void txtFileSizeIsTooLarge() {
        Locator contactButton = page.getByTestId("nav-contact");
        Locator sendButton = page.getByTestId("contact-submit");
        Locator attachment = page.getByTestId("attachment");
        Locator attachmentError = page.getByTestId("attachment-error");

        contactButton.click();
        attachment.setInputFiles(Paths.get("src/test/resources/data/testdata.txt"));

        String uploadedFileName = attachment.evaluate("el => el.files[0].name").toString();
        assertThat(uploadedFileName).isEqualTo("testdata.txt");

        sendButton.click();
        assertThat(attachmentError).isVisible();
        assertThat(attachmentError).hasText("File should be empty.");
    }
}