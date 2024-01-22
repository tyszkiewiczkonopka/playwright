package compareWithSelenium;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    private static Browser browser;

    @BeforeAll
    public static void setUp() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @AfterAll
    public static void tearDown() {
        if (browser != null) {
            browser.close();
        }
    }

    public static Browser getBrowser() {
        return browser;
    }
}



