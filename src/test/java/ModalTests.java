import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.*;
public class ModalTests {
    @Test
    void test01SimpleModal() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/modals/");
        sleep(5_000);
        $x("//div[@id='popmake-1318']").shouldBe(exist);
        $x("//div[@id='popmake-1318']").shouldNotBe(visible);

        $("#simpleModal").click();
        sleep(2_000);
        $x("//div[@id='popmake-1318']").shouldBe(visible);
        $x("//div[@id='popmake-1318']//button").shouldBe(clickable);
        $x("//div[@id='popmake-1318']//button").click();
        sleep(2_000);
    }
}

