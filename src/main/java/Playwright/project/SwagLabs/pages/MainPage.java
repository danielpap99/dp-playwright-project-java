package Playwright.project.SwagLabs.pages;

import Playwright.project.SwagLabs.components.ProductCardComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import java.util.ArrayList;
import java.util.List;

public class MainPage {

    private final Page page;
    public final ProductCardComponent productCardComponent;

    // Locators
    private final Locator shoppingCartIcon;
    private final Locator header;
    private final Locator productItems;
    private final Locator sortControl;
    private final Locator activeSortControl;
    private final Locator productName;
    private final Locator productPrice;
    private final Locator checkoutButton;


    // Constructor
    public MainPage(Page page) {
        this.page = page;
        this.productCardComponent = new ProductCardComponent(page);

        shoppingCartIcon = page.getByTestId("shopping-cart-link");
        header = page.getByTestId("primary-header");
        productItems = page.getByTestId("inventory-item");
        sortControl = page.getByTestId("product-sort-container");
        activeSortControl = page.getByTestId("active-option");
        productName = page.getByTestId("inventory-item-name");
        productPrice = page.getByTestId("inventory-item-price");
        checkoutButton = page.getByTestId("checkout");
    }

    // Dynamic locators


    // Actions
    public void selectSortOption(String sort) {
        sortControl.selectOption(sort);
        page.waitForLoadState(LoadState.NETWORKIDLE);
    }

    public void openCart() {
        shoppingCartIcon.click();
    }

    public void clickCheckoutButton() {
        checkoutButton.click();
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

    public List<String> productNames() {
        return productName.allTextContents();
    }

    public List<Double> productPrices() {
        List<String> priceStrings = productPrice.allTextContents();
        List<Double> prices = new ArrayList<>();

        for (String price : priceStrings) {
            double value = Double.parseDouble(price.replace("$", ""));
            prices.add(value);
        }

        return prices;
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
}
