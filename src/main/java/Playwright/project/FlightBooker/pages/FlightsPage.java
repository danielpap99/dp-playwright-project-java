package Playwright.project.FlightBooker.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FlightsPage {

    private final Page page;

    // Locators
    private final Locator departureCityField;
    private final Locator arrivalCityField;
    private final Locator submitButton;

    // Constructor
    public FlightsPage(Page page) {

        this.page = page;

        departureCityField = page.locator("#ctl00_mainContent_ddl_originStation1_CTXT");
        arrivalCityField = page.locator("#ctl00_mainContent_ddl_destinationStation1_CTXT");
        submitButton = page.locator("#ctl00_mainContent_btn_FindFlights");
    }

    // Methods
    public void enterDepartureCity(String city) {
        departureCityField.click();
    }




    //Asserts
    public boolean departureCityIsVisible() {
        return departureCityField.isVisible();
    }

    public boolean arrivalCityIsVisible() {
        return arrivalCityField.isVisible();
    }

    public boolean submitButtonIsVisible() {
        return submitButton.isVisible();
    }

}

