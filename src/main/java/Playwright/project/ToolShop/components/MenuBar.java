package Playwright.project.ToolShop.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class MenuBar {

    private final Page page;

    // Locators
    private final Locator contactMenuOption;
    private final Locator signInMenuOption;



    // Constructor
    public MenuBar(Page page) {

        this.page = page;

        contactMenuOption = page.getByTestId("nav-contact");
        signInMenuOption = page.getByTestId("nav-sign-in");
    }

    // Actions
    public void navigateToContactPage() {
        contactMenuOption.click();
    }

    public void navigateToSignInPage() {
        signInMenuOption.click();
    }



    // Assertions

}