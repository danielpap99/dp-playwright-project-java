package Playwright.project.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProductPage {

    private final Page page;

    // Locators
    private final Locator itemName;
    private final Locator itemDescription;
    private final Locator itemPrice;
    private final Locator itemImage;
    private final Locator removeButton;
    private final Locator backButton;

    // Constructor
    public ProductPage(Page page) {
        this.page = page;

        itemName = page.getByTestId("inventory-item-name");
        itemDescription = page.getByTestId("inventory-item-desc");
        itemPrice = page.getByTestId("inventory-item-price");
        itemImage = page.getByTestId("item-sauce-labs-bolt-t-shirt-img");
        removeButton = page.getByTestId("remove");
        backButton = page.getByTestId("back-to-products");
    }

    // Dynamic locators


    // Actions
    public void clickRemoveButton() {
        removeButton.click();
    }




    // Assertions
    public String itemNameText() {
        return itemName.textContent().trim();
    }

    public String productPageUrl() {
        return page.url();
    }
}
