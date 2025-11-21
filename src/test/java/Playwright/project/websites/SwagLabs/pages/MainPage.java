package Playwright.project.websites.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class MainPage {

    private final Page page;

    // Locators
    private final Locator shoppingCartIcon;
    private final Locator header;
    private final Locator productItems;
    private final Locator sortControl;
    private final Locator activeSortControl;

    // Constructor
    public MainPage(Page page) {
        this.page = page;

        shoppingCartIcon = page.locator("[data-test='shopping-cart-link']");
        header = page.locator("[data-test='primary-header']");
        productItems = page.locator("[data-test='inventory-item']");
        sortControl = page.locator("[data-test='product-sort-container']");
        activeSortControl = page.locator("[data-test='active-option']");
    }

    // Actions
    public void selectSortOption(String sort) {
        sortControl.click();
        page.locator("select[data-test='product-sort-container']").selectOption(sort);
    }

    // Assertions
    public int productCount() {
        return productItems.count();
    }

    public boolean isOnInventoryPage() {
        return page.url().equals("https://www.saucedemo.com/inventory.html")
                && header.isVisible()
                && shoppingCartIcon.isVisible()
                && sortControl.isVisible();
    }

    public String activeSortOptionText() {
        return activeSortControl.textContent();
    }

}
