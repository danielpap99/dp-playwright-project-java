package Playwright.project.ToolShop.tests;

import Playwright.project.ToolShop.base.ParallelExecution;
import Playwright.project.ToolShop.components.MenuBar;
import Playwright.project.ToolShop.pages.ContactForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

// this test class focuses on parallel test execution

public class ContactFormTestsParallel extends ParallelExecution {

    private ContactForm contactForm;
    private MenuBar menuBar;

    @BeforeEach
    void openHomePage() {
        page.navigate("https://practicesoftwaretesting.com");
    }

    @BeforeEach
    void initPage() {
        menuBar = new MenuBar(page);
        contactForm = new ContactForm(page);
    }

    private void goToContactPage() {
        menuBar.navigateToContactPage();
    }

    @Test
    void subjectCanBeSelected() {
        goToContactPage();
        contactForm.selectDropdownOption("warranty");

        assertThat(contactForm.subjectDropdownValue()).isEqualTo("warranty");
    }

    @Test
    void txtFileCanBeAttached() {
        goToContactPage();
        contactForm.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactForm.attachmentValue()).endsWith("testdata.txt");
    }

    @Test
    void txtFileSizeIsTooLarge() {
        goToContactPage();
        contactForm.attachFile("src/test/resources/data/testdata.txt");

        assertThat(contactForm.attachmentValue()).endsWith("testdata.txt");

        contactForm.clickSendButton();
        assertThat(contactForm.attachmentSizeErrorMessageAppears()).isTrue();
    }
}