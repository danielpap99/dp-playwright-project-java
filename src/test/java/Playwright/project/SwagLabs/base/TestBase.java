package Playwright.project.SwagLabs.base;

import Playwright.project.SwagLabs.utils.Base;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

public class TestBase extends Base {

    protected SoftAssertions softly;

    @BeforeEach
    void setUp() {
        setUpBrowser();
        softly = new SoftAssertions();  // initialize SoftAssertions for each test
    }

    @AfterEach
    void tearDown() {
        tearDownTest();
        softly.assertAll();             // run all soft assertions
    }
}