package Playwright.project.FlightBooker.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class SearchComponent {

    private final Page page;

    // Locators
    private final Locator searchComponent;
    private final Locator departureCityField1;
    private final Locator departureCityField2;
    private final Locator departureCityField3;
    private final Locator arrivalCityField1;
    private final Locator arrivalCityField2;
    private final Locator arrivalCityField3;
    private final Locator submitButton;
    private final Locator roundTripRadioButton;
    private final Locator clearReturnDate;
    private final Locator multicityRadioButton;
    private final Locator multiCityWarningMessage;
    private final Locator popup;
    private final Locator warningMessageCloseButton;

    // Constructor
    public SearchComponent(Page page) {
        this.page = page;

        searchComponent = page.locator("#flightSearchContainer");
        departureCityField1 = page.locator("#ctl00_mainContent_ddl_originStation1_CTXT");
        departureCityField2 = page.locator("#ctl00_mainContent_ddl_originStation2_CTXT");
        departureCityField3 = page.locator("#ctl00_mainContent_ddl_originStation3_CTXT");
        arrivalCityField1 = page.locator("#ctl00_mainContent_ddl_destinationStation1_CTXT");
        arrivalCityField2 = page.locator("#ctl00_mainContent_ddl_destinationStation2_CTXT");
        arrivalCityField3 = page.locator("#ctl00_mainContent_ddl_destinationStation3_CTXT");

        submitButton = page.locator("#ctl00_mainContent_btn_FindFlights");
        roundTripRadioButton = page.locator("#ctl00_mainContent_rbtnl_Trip_1");
        clearReturnDate = page.locator("#spclearDate");
        multicityRadioButton = page.locator("#ctl00_mainContent_rbtnl_Trip_2");
        multiCityWarningMessage = page.locator("#MultiCityModelPopup");
        popup = page.locator("#popupOverlay");
        warningMessageCloseButton = page.locator("#MultiCityModelAlert");
    }

    // Dynamic locators

    public Locator departureDatePicker(int number) {
        return page.locator("#ctl00_mainContent_view_date" + number);
    }


    // Methods
    public void selectDepartureCity(String airportCode) {
        departureCityField1.click();
        page.locator("a[value='" + airportCode + "']").first().click();
    }

    public void selectArrivalCity(String airportCode) {
        page.locator("a[value='" + airportCode + "']").first().click();
    }

    public void selectRoundTrip() {
        roundTripRadioButton.click();
    }

    public void selectMulticity() {
        multicityRadioButton.click();
    }

    public void closeMultiCityWarningMessage() {
        warningMessageCloseButton.click();

    }



    //Asserts
    public boolean searchComponentIsVisible() {
        return searchComponent.isVisible();
    }

    public boolean departureCityIsVisible() {
        return departureCityField1.isVisible();
    }

    public boolean arrivalCityIsVisible() {
        return arrivalCityField1.isVisible();
    }


    public boolean multipleDepartureCitiesAreVisible() {
        return departureCityField1.isVisible()
                && departureCityField2.isVisible()
                && departureCityField3.isVisible()
                && arrivalCityField1.isVisible()
                && arrivalCityField2.isVisible()
                && arrivalCityField3.isVisible();
    }

    public boolean multipleDepartDatePickersAreVisible() {
        return departureDatePicker(1).isVisible()
                && departureDatePicker(2).isVisible()
                && departureDatePicker(3).isVisible();
    }

    public boolean submitButtonIsVisible() {
        return submitButton.isVisible();
    }

    public String departureCityFieldText() {
        return departureCityField1.getAttribute ("value");
    }

    public String arrivalCityFieldText() {
        return arrivalCityField1.getAttribute ("value");
    }

    public boolean clearReturnDateIsVisible() {
        return clearReturnDate.isVisible();
    }

    public boolean multiCityWarningMessageIsVisible() {
        return popup.isVisible();
    }

    public String multiCityWarningMessageText() {
        return multiCityWarningMessage.textContent();
    }
}
