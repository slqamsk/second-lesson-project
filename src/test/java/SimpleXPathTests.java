import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class SimpleXPathTests {

    @Test
    void testPageH1() {
        open("https://slqamsk.github.io/tmp/xPath01.html");
        $x("//h1").should(visible);
    }

    @Test
    void testSpecialParagraph() {
        open("https://slqamsk.github.io/tmp/xPath01.html");
        $x("//p[@class='special-paragraph']").should(text("единственный на странице"));
        $x("//p[@class='info-text'][2]")
                .shouldHave(text("Это второй информационный текст."));
        $x("//a[@class='external-link'][2]")
                .should(text("Внешняя ссылка (Google)"));

    }

    @Test
    void test03TextSearch() {
        open("https://slqa.ru/cases/xPathSimpleForm/");

        //1. На странице https://slqa.ru/cases/xPathSimpleForm/ найдите элемент, содержащий текст "Москва"
        // с помощью команды библиотеки Selenide, и проверьте, что этот элемент содержит текст "250 единиц".
        $(withText("Москва")).shouldHave(text("250 единиц"));

        //2. Найдите элемент, содержащий текст "Питер" с помощью xPath,
        // и проверьте, что этот элемент содержит текст "180 единиц".
        $x("//*[contains(text(),'Питер')]").shouldHave(text("180 единиц"));

        //3. Найдите элемент, начинающийся с текста "Казахстан" и проверьте, что он содержит текст "площадь 2 724 902".
        $x("//*[starts-with(.,'Казахстан')]").shouldHave(text("площадь 2 724 902"));
    }
}
