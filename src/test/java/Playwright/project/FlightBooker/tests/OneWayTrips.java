package Playwright.project.FlightBooker.tests;

import Playwright.project.FlightBooker.base.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class OneWayTrips extends TestBase {

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
