package extra;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.ColorScheme;
import com.microsoft.playwright.options.ForcedColors;
import com.microsoft.playwright.options.SelectOption;
import compareWithSelenium.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FramesTestEmulations extends BaseTest {
    BrowserContext context;
    Page page;

    @BeforeEach
    public void setUpTest() {
        // MOBILE
        //context = getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(430, 932)); // iPhone 14 Pro Max
        // LOCALE & TIMEZONE
        //context = getBrowser().newContext(new Browser.NewContextOptions().setLocale("pl-PL").setTimezoneId("Europe/Warsaw"));
        // GEOLOCATION
        //context = getBrowser().newContext(new Browser.NewContextOptions().setGeolocation(54.404152, 18.571213));
        // DARK MODE
        context = getBrowser().newContext(new Browser.NewContextOptions().setForcedColors(ForcedColors.ACTIVE).setColorScheme(ColorScheme.LIGHT)); // można też na page zrobić; dark - light - no-preference
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

}
