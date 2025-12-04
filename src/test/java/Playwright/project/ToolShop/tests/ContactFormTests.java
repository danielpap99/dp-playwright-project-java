package Playwright.project.ToolShop.tests;

import Playwright.project.ToolShop.base.TestBase;
import Playwright.project.ToolShop.pages.ContactForm;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

// this test class also focuses on making the allure report more descriptive
// features and stories

@Feature("Product Catalog")
public class ContactFormTests extends TestBase {

    private ContactForm contactForm;

    @BeforeEach
    void initPage() {
        contactForm = new ContactForm(page);
    }

    @Test
    @Story("Selecting query subject")
    void subjectCanBeSelected() {
        goToContactPage();
        contactForm.selectDropdownOption("warranty");

        assertThat(contactForm.subjectDropdownValue()).isEqualTo("warranty");
    }

    @Test
    @Story("Attaching a text file")
    void txtFileCanBeAttached() {
        goToContactPage();
        contactForm.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactForm.attachmentValue()).endsWith("testdata.txt");
    }

    @Test
    @Story("Text file size validation")
    void txtFileSizeIsTooLarge() {
        goToContactPage();
        contactForm.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactForm.attachmentValue()).endsWith("testdata.txt");

        contactForm.clickSendButton();
        assertThat(contactForm.attachmentSizeErrorMessageAppears()).isTrue();
    }
}