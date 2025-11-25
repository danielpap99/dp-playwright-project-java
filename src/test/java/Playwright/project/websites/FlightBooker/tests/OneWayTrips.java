package Playwright.project.websites.FlightBooker.tests;

import Playwright.project.websites.FlightBooker.utilities.Base;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class OneWayTrips extends Base {

    @Test
    @Tag("Smoke")
    void mainPageLoadsCorrectly() {
        assertAll(
                () -> assertThat(flightsPage.departureCityIsVisible()).isTrue(),
                () -> assertThat(flightsPage.arrivalCityIsVisible()).isTrue(),
                () -> assertThat(flightsPage.submitButtonIsVisible()).isTrue()
        );
    }
}
