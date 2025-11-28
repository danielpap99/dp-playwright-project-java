package Playwright.project.websites.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {

    private final Page page;
    public final ProductCardComponent productCardComponent;

    // Locators
    private final Locator checkoutButton;
    private final Locator continueShoppingButton;

    // Constructor
    public CartPage(Page page) {
        this.page = page;
        this.productCardComponent = new ProductCardComponent(page);

        checkoutButton = page.getByTestId("checkout");
        continueShoppingButton = page.getByTestId("continue-shopping");
    }

    // Dynamic locators


    // Actions
    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    public void clickContinueShoppingButton() {
        continueShoppingButton.click();
    }


    // Assertions

}
