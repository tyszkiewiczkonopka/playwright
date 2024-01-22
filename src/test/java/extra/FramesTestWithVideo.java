package extra;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import compareWithSelenium.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class FramesTestWithVideo extends BaseTest {
    BrowserContext context;
    Page page;

    @BeforeEach
    public void setUpTest() {
        context = getBrowser().newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")));
        page = context.newPage();
    }

    @Test
    void should_switch_between_iframes() {
        page.navigate("http://automation-practice.emilos.pl/iframes.php");
        fillShortForm("Magdalena", "Tyszkiewicz");
        fillExtendedForm("magda", "admin", "europe", "3");
    }

    private void fillShortForm(String firstName, String surname) {
        Frame frameShortForm = page.frame("iframe1");
        frameShortForm.getByPlaceholder("First name").fill(firstName);
        frameShortForm.getByPlaceholder("Surname").fill(surname);
        frameShortForm.getByRole(AriaRole.BUTTON).click();
    }

    private void fillExtendedForm(String login, String password, String continentOptionValue, String experienceOptionId) {
        Frame frameExtendedForm = page.frame("iframe2");
        frameExtendedForm.getByPlaceholder("Login").fill(login);
        frameExtendedForm.getByPlaceholder("Password").fill(password);
        frameExtendedForm.locator(".custom-select").selectOption(new SelectOption().setValue(continentOptionValue));
        frameExtendedForm.getByLabel(experienceOptionId).click();
        frameExtendedForm.getByRole(AriaRole.BUTTON).click();
    }

    @AfterEach
    public void tearDownTest() {
        context.close();
    }

}
