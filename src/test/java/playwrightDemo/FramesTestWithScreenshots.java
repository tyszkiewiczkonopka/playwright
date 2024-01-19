package playwrightDemo;

import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class FramesTestWithScreenshots extends BaseTest {
    Page page = getBrowser().newPage();

    @Test
    void should_switch_between_iframes_and_take_screenshots() {
        page.navigate("http://automation-practice.emilos.pl/iframes.php");
        fillShortForm("Magdalena", "Tyszkiewicz");
        fillExtendedForm("magda", "admin", "europe", "3");
    }

    private void fillShortForm(String firstName, String surname) {
        Frame frameShortForm = page.frame("iframe1");
        frameShortForm.getByPlaceholder("First name").fill(firstName);
        frameShortForm.getByPlaceholder("Surname").fill(surname);
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("shortForm.png")));

        frameShortForm.getByRole(AriaRole.BUTTON).click();
    }

    private void fillExtendedForm(String login, String password, String continentOptionValue, String experienceOptionId) {
        Frame frameExtendedForm = page.frame("iframe2");
        frameExtendedForm.getByPlaceholder("Login").fill(login);
        frameExtendedForm.getByPlaceholder("Password").fill(password);
        frameExtendedForm.locator(".custom-select").selectOption(new SelectOption().setValue(continentOptionValue));
        frameExtendedForm.getByLabel(experienceOptionId).click();
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("extendedForm.png")));

        frameExtendedForm.getByRole(AriaRole.BUTTON).click();
    }

}