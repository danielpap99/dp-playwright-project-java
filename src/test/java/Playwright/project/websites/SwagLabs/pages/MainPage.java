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
    private final Locator productName;
    private final Locator productPrice;


    // Constructor
    public MainPage(Page page) {
        this.page = page;

        shoppingCartIcon = page.locator("[data-test='shopping-cart-link']");
        header = page.locator("[data-test='primary-header']");
        productItems = page.locator("[data-test='inventory-item']");
        sortControl = page.locator("[data-test='product-sort-container']");
        activeSortControl = page.locator("[data-test='active-option']");
        productName = page.locator("[data-test='inventory-item-name']");
        productPrice = page.locator("[data-test='inventory-item-price']");
    }

    // Dynamic locators
    public Locator addToCartButton(String productName) {
        String product = productName.toLowerCase().replace(" ", "-");
        return page.locator("[data-test='add-to-cart-" + product + "']");
    }

    public Locator removeButton(String productName) {
        String product = productName.toLowerCase().replace(" ", "-");
        return page.locator("[data-test='remove-" + product + "']");
    }

    // Actions
    public void selectSortOption(String sort) {
        sortControl.selectOption(sort);
    }

    public void addProductToCart(String productName) {
        addToCartButton(productName).click();
    }

    public void removeProductFromCart(String productName) {
        removeButton(productName).click();
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

    public String firstProductName() {
        return productName.first().textContent();
    }

    public String firstProductPrice() {
        return productPrice.first().textContent();
    }

    public String shoppingCartQuantityText() {
        return shoppingCartIcon.textContent();
    }

    public boolean addToCartButtonVisible(String productName) {
        String product = productName.toLowerCase().replace(" ", "-");
        return addToCartButton(product).isVisible();
    }

}
