package Playwright.project.ToolShop.tests;

import Playwright.project.ToolShop.base.TestBase;
import Playwright.project.ToolShop.pages.ContactPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

// this test class also focuses on making the allure report more descriptive
// features and stories

@Feature("Product Catalog")
public class ContactFormTests extends TestBase {

    private ContactPage contactPage;

    @BeforeEach
    void initPage() {
        contactPage = new ContactPage(page);
    }

    @Test
    @Story("Selecting query subject")
    void subjectCanBeSelected() {
        goToContactPage();
        contactPage.selectDropdownOption("warranty");

        assertThat(contactPage.subjectDropdownValue()).isEqualTo("warranty");
    }

    @Test
    @Story("Attaching a text file")
    void txtFileCanBeAttached() {
        goToContactPage();
        contactPage.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactPage.attachmentValue()).endsWith("testdata.txt");
    }

    @Test
    @Story("Text file size validation")
    void txtFileSizeIsTooLarge() {
        goToContactPage();
        contactPage.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactPage.attachmentValue()).endsWith("testdata.txt");

        contactPage.clickSendButton();
        assertThat(contactPage.attachmentSizeErrorMessageAppears()).isTrue();
    }
}