package Playwright.project.ToolShop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.nio.file.Paths;

public class ContactPage {

    private final Page page;

    // Locators
    private final Locator subjectDropdown;
    private final Locator attachment;
    private final Locator sendButton;
    private final Locator attachmentError;

    // Constructor
    public ContactPage(Page page) {

        this.page = page;

        subjectDropdown = page.getByTestId("subject");
        attachment = page.getByTestId("attachment");
        sendButton = page.getByTestId("contact-submit");
        attachmentError = page.getByTestId("attachment-error");
    }

    // Actions
    public void selectDropdownOption(String option) {
        page.getByTestId("subject").selectOption(option);
    }

    public void attachFile(String filePath) {
        attachment.setInputFiles(Paths.get(filePath));
    }

    public void clickSendButton() {
        sendButton.click();
    }


    //Assertions
    public String subjectDropdownValue() {
        return subjectDropdown.inputValue();
    }

    public String attachmentValue() {
        return attachment.inputValue();
    }

    public boolean attachmentSizeErrorMessageAppears() {
        return attachmentError.isVisible()
                && attachmentError.textContent().equals("File should be empty.");
    }

}
