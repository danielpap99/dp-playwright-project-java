package Playwright.project.websites.SwagLabs.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class CheckoutPage {

    private final Page page;
    public final ProductCardComponent productCardComponent;

    // Locators
    private final Locator firstNameField;
    private final Locator lastNameField;
    private final Locator postcodeField;
    private final Locator continueButton;
    private final Locator subtotal;
    private final Locator finishButton;
    private final Locator orderPlacedTitle;
    private final Locator orderPlacedMessage;
    private final Locator backHomeButton;

    // Constructor
    public CheckoutPage(Page page) {
        this.page = page;
        this.productCardComponent = new ProductCardComponent(page);

        firstNameField = page.getByTestId("firstName");
        lastNameField = page.getByTestId("lastName");
        postcodeField = page.getByTestId("postalCode");
        continueButton = page.getByTestId("continue");
        subtotal = page.getByTestId("subtotal-label");
        finishButton = page.getByTestId("finish");
        orderPlacedTitle = page.getByTestId("complete-header");
        orderPlacedMessage = page.getByTestId("complete-text");
        backHomeButton = page.getByTestId("back-to-products");
    }

    // Dynamic locators


    // Actions
    public void clickContinueButton() {
        continueButton.click();
    }

    public void enterFirstName(String firstName) {
        firstNameField.fill(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameField.fill(lastName);
    }

    public void enterPostcode(String postcode) {
        postcodeField.fill(postcode);
    }

    public void enterPersonalDetails(String firstName, String lastName, String postcode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostcode(postcode);
    }

    public void clickFinishButton() {
        finishButton.click();
    }

    public void clickBackHomeButton() {
        backHomeButton.click();
    }


    // Assertions
    public boolean checkoutPageLoads() {
        return firstNameField.isVisible()
            && lastNameField.isVisible()
                && postcodeField.isVisible()
                    && continueButton.isVisible();
    }

    public String subtotalText() {
        return subtotal.textContent();
    }

    public String orderPlacedTitleText() {
        return orderPlacedTitle.textContent();
    }

    public String orderPlacedMessageText() {
        return orderPlacedMessage.textContent();
    }
}
