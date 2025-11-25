package Playwright.project.websites.ToolShop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class MainPage {

    private final Page page;

    // Locators
    private final Locator contactMenuOption;
    private final Locator products;


    // Constructor
    public MainPage(Page page) {

        this.page = page;

        contactMenuOption = page.getByTestId("nav-contact");
        products = page.locator("#card");
    }

    // Actions
    public void clickContactMenuOption() {
        contactMenuOption.click();
    }



    //Assertions


}