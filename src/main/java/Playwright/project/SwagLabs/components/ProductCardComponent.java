package Playwright.project.SwagLabs.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class ProductCardComponent {

    private final Page page;

    // Locators
    private final Locator products;


    // Constructor
    public ProductCardComponent(Page page) {
        this.page = page;

        products = page.getByTestId("inventory-item");
    }

    // Dynamic locators
    private Locator productByName(String productName) {
        return products.filter(new Locator.FilterOptions().setHasText(productName));
    }

    private Locator productName(String productName) {
        return productByName(productName).getByTestId("inventory-item-name");
    }

    private Locator productPrice(String productName) {
        return productByName(productName).getByTestId("inventory-item-price");
    }

    private Locator itemQuantity(String productName) {
        return productByName(productName).getByTestId("item-quantity");
    }

    private Locator productDescription(String productName) {
        return productByName(productName).getByTestId("inventory-item-desc");
    }

    private Locator itemRemoveButton(String productName) {
        String productId = productName.toLowerCase().replace(" ", "-");
        return productByName(productName).getByTestId("remove-" + productId);
    }

    public Locator addToCartButton(String productName) {
        String productId = productName.toLowerCase().replace(" ", "-");
        return productByName(productName).getByTestId("add-to-cart-" + productId);
    }


    // Actions
    public void clickProductLink(String productName) {
        productName(productName).click();
    }

    public void addProductToCart(String productName) {
        addToCartButton(productName).click();
    }

    public void removeProductFromCart(String productName) {
        itemRemoveButton(productName).click();
    }



    // Assertions
    public String productNameText(String productName) {
        return productName(productName).textContent();
    }

    public String productPriceText(String productName) {
        return productPrice(productName).textContent();
    }

    public String itemQuantityText(String productName) {
        return itemQuantity(productName).textContent().trim();
    }

    public String itemDescriptionText(String productName) {
        return productDescription(productName).textContent().trim();
    }

    public boolean removeButtonVisible(String productName) {
        return itemRemoveButton(productName).isVisible();
    }

    public boolean productCardIsVisible(String productName) {
        return productByName(productName).isVisible()
                && itemRemoveButton(productName).isVisible();
    }

    public boolean addToCartButtonVisible(String productName) {
        return addToCartButton(productName).isVisible();
    }

}