package playwrightDemo;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.SelectOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

public class FramesTestWithTrace extends BaseTest {
    BrowserContext context;
    Page page;

    @BeforeEach
    public void setUpTest() {
        context = getBrowser().newContext();
        page = context.newPage();

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    @Test
    void should_switch_between_iframes_with_trace_mechanism() {

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

    @AfterEach
    public void tearDownTest() {
        context.tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("trace.zip")));
    }

}
