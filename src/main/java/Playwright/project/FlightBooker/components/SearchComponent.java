package Playwright.project.FlightBooker.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SearchComponent {

    private final Page page;

    // Locators
    private final Locator searchComponent;
    private final Locator departureCityField;
    private final Locator arrivalCityField;
    private final Locator submitButton;

    // Constructor
    public SearchComponent(Page page) {
        this.page = page;

        searchComponent = page.locator("#flightSearchContainer");
        departureCityField = page.locator("#ctl00_mainContent_ddl_originStation1_CTXT");
        arrivalCityField = page.locator("#ctl00_mainContent_ddl_destinationStation1_CTXT");
        submitButton = page.locator("#ctl00_mainContent_btn_FindFlights");
    }

    // Dynamic locators


    // Methods
    public void selectDepartureCity(String airportCode) {
        departureCityField.click();
        page.locator("a[value='" + airportCode + "']").first().click();
    }

    public void selectArrivalCity(String airportCode) {
        page.locator("a[value='" + airportCode + "']").first().click();
    }



    //Asserts
    public boolean searchComponentIsVisible() {
        return searchComponent.isVisible();
    }
    public boolean departureCityIsVisible() {
        return departureCityField.isVisible();
    }

    public boolean arrivalCityIsVisible() {
        return arrivalCityField.isVisible();
    }

    public boolean submitButtonIsVisible() {
        return submitButton.isVisible();
    }

    public String departureCityFieldText() {
        return departureCityField.getAttribute ("value");
    }

    public String arrivalCityFieldText() {
        return arrivalCityField.getAttribute ("value");
    }
}
