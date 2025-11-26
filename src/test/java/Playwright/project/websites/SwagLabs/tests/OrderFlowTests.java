package Playwright.project.websites.SwagLabs.tests;

import Playwright.project.websites.SwagLabs.pages.ProductPage;
import Playwright.project.websites.SwagLabs.utilities.Base;
import Playwright.project.websites.SwagLabs.pages.CartPage;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class OrderFlowTests extends Base {

    private CartPage cartPage;
    private ProductPage productPage;

    @BeforeEach
    void setUpOrderFlowPages() {
        cartPage = new CartPage(page);
        productPage = new ProductPage(page);
    }


}
