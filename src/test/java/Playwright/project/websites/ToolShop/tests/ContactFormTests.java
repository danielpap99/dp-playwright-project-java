package Playwright.project.websites.ToolShop.tests;

import Playwright.project.websites.ToolShop.pages.ContactPage;
import Playwright.project.websites.ToolShop.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;


public class ContactFormTests extends Base {

    private ContactPage contactPage;

    @BeforeEach
    void initPage() {
        contactPage = new ContactPage(page);
    }

    @DisplayName("Dropdown menu")
    @Test
    void subjectCanBeSelected() {
        goToContactPage();
        contactPage.selectDropdownOption("warranty");

        assertThat(contactPage.subjectDropdownValue()).isEqualTo("warranty");
    }

    @DisplayName("Attachment")
    @Test
    void txtFileCanBeAttached() {
        goToContactPage();
        contactPage.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactPage.attachmentValue()).endsWith("testdata.txt");
    }

    @DisplayName("Attachment")
    @Test
    void txtFileSizeIsTooLarge() {
        goToContactPage();
        contactPage.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactPage.attachmentValue()).endsWith("testdata.txt");

        contactPage.clickSendButton();
        assertThat(contactPage.attachmentSizeErrorMessageAppears()).isTrue();
    }
}