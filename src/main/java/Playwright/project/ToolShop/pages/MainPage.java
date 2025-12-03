package Playwright.project.ToolShop.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;


public class MainPage {

    private final Page page;

    // Locators
    private final Locator contactMenuOption;
    private final Locator searchField;
    private final Locator searchButton;
    private final Locator productName;
    private final Locator productImage;
    private final Locator productPrice;
    private final Locator sortDropdown;
    private final Locator signInButton;


    // Constructor
    public MainPage(Page page) {

        this.page = page;

        contactMenuOption = page.getByTestId("nav-contact");
        searchField = page.getByTestId("search-query");
        searchButton = page.getByTestId("search-submit");
        productName = page.getByTestId("product-name");
        productImage = page.locator(".card-img-top");
        productPrice = page.getByTestId("product-price");
        sortDropdown = page.getByTestId("sort");
        signInButton = page.getByTestId("nav-sign-in");
    }

    // Actions
    public void clickContactMenuOption() {
        contactMenuOption.click();
    }

    public void searchForProduct(String productName) {
        searchField.fill(productName);
        searchButton.click();
    }

    public void selectSortOption(String sort) {
        sortDropdown.selectOption(sort);
    }

    public void navigateToSignInPage() {
        signInButton.click();
    }


    //Assertions
    public List<String> productNames() {
        return productName.allTextContents();
    }

    public List<String> productPrices() {
        return productPrice.allTextContents();
    }

    public List<String> productImageTitles() {
        page.waitForSelector(".card-img-top"); //example of a wait method until images appear

        return productImage.all()
                .stream()
                .map(img -> img.getAttribute("alt"))
                .toList();
    }

    public boolean userIsLoggedIn() {
        signInButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        return page.url().equals("https://practicesoftwaretesting.com/account");
    }
}