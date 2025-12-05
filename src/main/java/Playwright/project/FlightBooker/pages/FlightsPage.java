package Playwright.project.FlightBooker.pages;

import Playwright.project.FlightBooker.components.SearchComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class FlightsPage {

    private final Page page;
    public final SearchComponent searchComponent;

    // Locators


    // Constructor
    public FlightsPage(Page page) {

        this.page = page;
        this.searchComponent = new SearchComponent(page);
    }

    // Methods




    //Asserts


}

