package basic;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.Test;

public class BasicFrameTest {
    Playwright playwright = Playwright.create();
    BrowserType browserType = playwright.chromium();
    BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions().setHeadless(false);
    Browser browser = browserType.launch(launchOptions);
    BrowserContext context = browser.newContext();
    Page page = browser.newPage();
    @Test
    void should_switch_between_iframes() {
        page.navigate("http://automation-practice.emilos.pl/iframes.php");

        Frame frameShortForm = page.frame("iframe1");
        frameShortForm.getByPlaceholder("First name").fill("Magdalena");
        frameShortForm.getByPlaceholder("Surname").fill("Tyszkiewicz");
        frameShortForm.getByRole(AriaRole.BUTTON).click();

        Frame frameExtendedForm = page.frame("iframe2");
        frameExtendedForm.getByPlaceholder("Login").fill("magda");
        frameExtendedForm.getByPlaceholder("Password").fill("admin");
        frameExtendedForm.locator(".custom-select").selectOption(new SelectOption().setValue("europe"));
        frameExtendedForm.getByLabel("3").click();
        frameExtendedForm.getByRole(AriaRole.BUTTON).click();
    }

}
