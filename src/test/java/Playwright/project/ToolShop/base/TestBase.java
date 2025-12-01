package Playwright.project.ToolShop.base;

import Playwright.project.ClearFolderExtension;
import Playwright.project.ToolShop.utils.Base;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(ClearFolderExtension.class)

public class TestBase extends Base {

    protected SoftAssertions softly;

    @BeforeAll
    public static void initTestSuite() {
        setUpBrowser();
    }

    @BeforeEach
    public void initTest() {
        setUpContext();
        softly = new SoftAssertions();  // initialise SoftAssertions for each test
    }

    @AfterEach
    public void cleanupTest() {
        softly.assertAll();             // run all soft assertions
    }

    @AfterAll
    public static void cleanupTestSuite() {
        tearDown();
    }
}
