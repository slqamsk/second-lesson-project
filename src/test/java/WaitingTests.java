import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WaitingTests {
    @Test
    void test01() {
        Configuration.timeout = 1_000;
        open("https://slqa.ru/cases/WaitsSimpleForm/");
        sleep(4_000);

        $("#duration_time").sendKeys("5");
        $("#show_element").click();

        $("#dynamic_content")
                .should(exist, Duration.ofSeconds(6))
                .shouldHave(text("5000 миллисекунд"));
    }

    @Test
    void test02() {
        Configuration.pageLoadTimeout = 10_000;
        open("https://specialist.ru");
        $x("//a[contains(.,'Курсы')]")
                .shouldBe(clickable);
    }
}
