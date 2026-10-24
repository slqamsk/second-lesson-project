import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
public class SimpleFormTest {

    @BeforeEach
    void setUp() {
        open("https://slqa.ru/cases/SimpleForm/");
    }

    @Test
    void test01SpecificCommands() {
        sleep(4_000);
        $(By.id("unique_id")).sendKeys("Сергей");
        sleep(1_000);
        $(By.name("unique_name")).sendKeys("Слесарев");
        sleep(1_000);
        $(By.tagName("blockquote")).shouldBe(Condition.exist);
        $(By.tagName("blockquote")).shouldHave(text("спрашивает"));

        $(By.className("unique_class")).shouldBe(Condition.visible);
        sleep(2_000);
    }

    @Test
    void test02CSSSelectors() {
        $("#unique_id").sendKeys("Сергей");
        $("[name=unique_name]").sendKeys("Слесарев");
        $("blockquote")
                .shouldBe(Condition.exist)
                .shouldHave(text("спрашивает"));
        $(".unique_class").shouldBe(Condition.visible);
    }
}