package Playwright.project.FlightBooker.tests;

import Playwright.project.FlightBooker.base.TestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


public class FlightSearchTests extends TestBase {

    @Test
    @Tag("Smoke")
    void mainPageLoadsCorrectly() {
        assertAll(
                () -> assertThat(flightsPage.searchComponent.searchComponentIsVisible()).isTrue(),
                () -> assertThat(flightsPage.searchComponent.departureCityIsVisible()).isTrue(),
                () -> assertThat(flightsPage.searchComponent.arrivalCityIsVisible()).isTrue(),
                () -> assertThat(flightsPage.searchComponent.submitButtonIsVisible()).isTrue()
        );
    }

    @Test
    @Tag("Stage1")
    void departureCityCanBeSelected() {
        flightsPage.searchComponent.selectDepartureCity("BKK");

        assertThat(flightsPage.searchComponent.departureCityFieldText()).isEqualTo("Bangkok (BKK)");
    }

    @Test
    @Tag("Stage1")
    void arrivalCityCanBeSelectedGivenThatDepartureCityIsSelected() {
        flightsPage.searchComponent.selectDepartureCity("BKK");
        flightsPage.searchComponent.selectArrivalCity("DEL");

        assertThat(flightsPage.searchComponent.arrivalCityFieldText()).isEqualTo("Delhi (DEL)");
    }

    @Test
    @Tag("Stage1")
    void roundTripCanBeSelected() {
        flightsPage.searchComponent.selectRoundTrip();

        assertThat(flightsPage.searchComponent.clearReturnDateIsVisible()).isTrue();
    }

    @Test
    @Tag("Stage2")
    void multicityCanBeSelected() {
        flightsPage.searchComponent.selectMulticity();

        assertAll(
                () -> assertThat(flightsPage.searchComponent.multiCityWarningMessageIsVisible()).isTrue(),
                () -> assertThat(flightsPage.searchComponent.multiCityWarningMessageText()).contains("Multicity booking does not mean connecting flight.")
        );

        flightsPage.searchComponent.closeMultiCityWarningMessage();

        assertAll(
                () -> assertThat(flightsPage.searchComponent.multiCityWarningMessageIsVisible()).isFalse(),
                () -> assertThat(flightsPage.searchComponent.multipleDepartureCitiesAreVisible()).isTrue(),
                () -> assertThat(flightsPage.searchComponent.multiDatePickersAreVisible()).isTrue()
        );

    }
}
