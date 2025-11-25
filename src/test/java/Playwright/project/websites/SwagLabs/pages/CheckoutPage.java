package Playwright.project.websites.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class CheckoutPage {

    private final Page page;

    // Locators
    private final Locator checkoutButton;



    // Constructor
    public CheckoutPage(Page page) {
        this.page = page;

        checkoutButton = page.getByTestId("checkout");
    }

    // Dynamic locators


    // Actions


    // Assertions

}
