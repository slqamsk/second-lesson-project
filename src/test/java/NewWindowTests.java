import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
public class NewWindowTests {
    @Test
    void test02NewTab() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/");
        sleep(5_000);
        getWebDriver().manage().window().maximize();
        sleep(2_000);
        switchTo().newWindow(WindowType.TAB);
        sleep(2000);
        open("https://slqamsk.github.io/apps/ShopList/v07/");
        sleep(2_000);
        $x("//h1").click();
        sleep(2_000);
        switchTo().window(0);
        $x("//a[text()='Popups']").click();
        sleep(2_000);
        switchTo().window(1);
        $x("//a[contains(.,'Общий список')]").click();
        sleep(5_000);
        closeWindow();
        sleep(2_000);
        switchTo().window(0);
        closeWindow();
        sleep(2_000);
    }

    @Test
    void test03() {
        open("https://slqamsk.github.io/apps/ShopList/v07/");
        sleep(5_000);

        Actions actions = new Actions(WebDriverRunner.getWebDriver());

        actions.keyDown(Keys.LEFT_CONTROL).build().perform();
        $x("//a[contains(.,'Общий список')]").click();
        actions.keyUp(Keys.LEFT_CONTROL).build().perform();

        sleep(5_000);
        switchTo().window(1);

        sleep(5_000);


    }
}
