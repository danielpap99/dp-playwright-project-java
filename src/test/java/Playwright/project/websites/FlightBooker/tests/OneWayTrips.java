package Playwright.project.websites.FlightBooker.tests;

import Playwright.project.websites.FlightBooker.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class OneWayTrips extends Base {

    @Test
    @Tag("Smoke")
    void mainPageLoadsCorrectly() {

        assertTrue(flightsPage.departureCityIsVisible(), "Departure City field is visible on screen");
        assertTrue(flightsPage.arrivalCityIsVisible(),  "Arrival City field is visible on screen");
        assertTrue(flightsPage.submitButtonIsVisible(), "Submit button is visible on screen");
    }


}
