package Playwright.project.websites.ToolShop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.List;


public class MainPage {

    private final Page page;

    // Locators
    private final Locator contactMenuOption;
    private final Locator products;
    private final Locator searchField;
    private final Locator searchButton;
    private final Locator productName;
    private final Locator productImage;


    // Constructor
    public MainPage(Page page) {

        this.page = page;

        contactMenuOption = page.getByTestId("nav-contact");
        products = page.locator("#card");
        searchField = page.getByTestId("search-query");
        searchButton = page.getByTestId("search-submit");
        productName = page.locator(".card-title");
        productImage = page.locator(".card-img-top");
    }

    // Actions
    public void clickContactMenuOption() {
        contactMenuOption.click();
    }

    public void searchForProduct(String productName) {
        searchField.fill(productName);
        searchButton.click();
    }



    //Assertions
    public List<String> productNameText() {
        page.waitForSelector(".card-title"); //wait until product names load
        List<String> names = new ArrayList<>();
        int count = productName.count();

        for (int i = 0; i < count; i++) {
            names.add(productName.nth(i).innerText().toLowerCase());
        }

        return names;
    }

    public List<String> productImageTitles() {
        page.waitForSelector(".card-img-top"); //wait until images load

        return productImage.all()
                .stream()
                .map(img -> img.getAttribute("alt"))
                .toList();
    }

}