package Playwright.project.websites.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage {

    private final Page page;

    // Locators
    private final Locator shoppingCartIcon;
    private final Locator header;

    // Constructor
    public MainPage(Page page) {
        this.page = page;

        shoppingCartIcon = page.locator("[data-test='shopping-cart-link']");
        header = page.locator("[data-test='primary-header']");
    }

    // Actions


    // Assertions
    public boolean shoppingCartIconIsVisible() {
        return shoppingCartIcon.isVisible();
    }

    public boolean headerIsVisible() {
        return header.isVisible();
    }

    public String headerText() {
        return header.innerText();
    }
}
