package Playwright.project.FlightBooker.base;

import Playwright.project.FlightBooker.utils.Base;
import org.assertj.core.api.SoftAssertions;
import Playwright.project.ClearFolderExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.*;

@ExtendWith(ClearFolderExtension.class) // this is for Allure to delete the results before each test run

public class TestBase extends Base {

    protected SoftAssertions softly;

    @BeforeAll
    public static void initTestSuite() {
        setUpBrowser();
    }

    @BeforeEach
    void initTest() {
        setUpContext();
        softly = new SoftAssertions();  // initialize SoftAssertions for each test
    }

    @AfterEach
    void cleanupTest() {
        softly.assertAll();             // run all soft assertions
    }

    @AfterAll
    public static void cleanupTestSuite() {
        tearDown();
    }
}

