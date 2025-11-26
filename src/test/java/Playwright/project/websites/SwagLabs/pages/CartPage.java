package Playwright.project.websites.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CartPage {

    private final Page page;

    // Locators
    private final Locator cartItems;
    private final Locator checkoutButton;

    // Constructor
    public CartPage(Page page) {
        this.page = page;

        cartItems = page.getByTestId("inventory-item");
        checkoutButton = page.getByTestId("checkout");
    }

    // Dynamic locators
    private Locator cartItemByName(String productName) {
        // Filter cart items by the product name they contain
        return cartItems.filter(new Locator.FilterOptions().setHasText(productName));
    }

    private Locator itemQuantity(String productName) {
        return cartItemByName(productName).getByTestId("item-quantity");
    }

    private Locator itemName(String productName) {
        return cartItemByName(productName).getByTestId("inventory-item-name");
    }

    private Locator itemDescription(String productName) {
        return cartItemByName(productName).getByTestId("inventory-item-desc");
    }

    private Locator itemPrice(String productName) {
        return cartItemByName(productName).getByTestId("inventory-item-price");
    }

    private Locator itemRemoveButton(String productName) {
        String productId = productName.toLowerCase().replace(" ", "-");
        return cartItemByName(productName).getByTestId("remove-" + productId);
    }

    // Actions
    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    public void clickProductLink(String productName) {
        itemName(productName).click();
    }

    public void removeProductFromCart(String productName){
        itemRemoveButton(productName).click();
        itemRemoveButton(productName).waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.DETACHED));
    }


    // Assertions
    public String itemQuantityText(String productName) {
        return itemQuantity(productName).textContent().trim();
    }

    public String itemNameText(String productName) {
        return itemName(productName).textContent().trim();
    }

    public String itemDescriptionText(String productName) {
        return itemDescription(productName).textContent().trim();
    }

    public String itemPriceText(String productName) {
        return itemPrice(productName).textContent().trim();
    }

    public boolean removeButtonVisible(String productName) {
        return itemRemoveButton(productName).isVisible();
    }

    public boolean productCardIsVisible(String productName) {
        return cartItemByName(productName).isVisible()
                && itemRemoveButton(productName).isVisible();
    }
}
