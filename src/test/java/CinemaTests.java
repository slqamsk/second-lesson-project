import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class CinemaTests {

    @Test
    void test01_simple_cinema() {
        open("https://slqamsk.github.io/cases/cinema/v02");
        $("input[name=age]").setValue("35");
        String dateValue = "03.11.2026"; // Дата в формате YYYY-MM-DD
        $("input[name='date']").setValue(dateValue);
        $x("//input[@name='session' and @value='14:00']").click();
        $("input[name=film][value=crime]").click();
        $x("//button[@type='submit']").click();
        $x("//div[@id='result']").shouldHave(text("Стоимость билета: 400 рублей."));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "cinema_data.csv", numLinesToSkip = 1)
        // Как Excel заставить сохранять значения с разделителем запятая
        // Почему он стал требовать заключать значения в кавычки
        // Почему для параметра session он эти кавычки принял как часть значения
    void test02CinemaParametrized(String age, String date, String session, String film, String message) {
        open("https://slqamsk.github.io/cases/cinema/v02");
        System.out.println(age + ", " + date + ", " + session + ", " + film + ", " + message);
        $("input[name=age]").setValue(age);
        //String dateValue = "28.07.2026"; // Дата в формате YYYY-MM-DD
        $("input[name='date']").setValue(date);
        $x("//input[@name='session' and @value=" + session + "]").click();
        $("input[name=film][value='" + film + "']").click();
        $x("//button[@type='submit']").click();
        $x("//div[@id='result']").shouldHave(text(message));
        sleep(2_000);
    }




    static String getDateByWeekDay(int weekDay, boolean isISO) {
        // Приводим к стандарту Java (1-7 = понедельник-воскресенье)
        DayOfWeek targetDay = DayOfWeek.of(weekDay);

        // Получаем текущую дату
        LocalDate today = LocalDate.now();

        // Находим ближайшую дату с нужным днём недели, начиная со следующего дня
        LocalDate resultDate = today.plusDays(1).with(TemporalAdjusters.nextOrSame(targetDay));

        // Форматируем в строку
        if (isISO) {
            return resultDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } else {
            return resultDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
    }



    static Stream<Arguments> provideCinemaData() {
        Stream.Builder<Arguments> argumentsBuilder = Stream.builder();

        argumentsBuilder.add(Arguments.of("20", getDateByWeekDay(2, false), "'14:00'", "crime", "Стоимость билета: 400 рублей."));
        argumentsBuilder.add(Arguments.of("30", getDateByWeekDay(3, false), "'19:00'", "king", "Стоимость билета: 400 рублей."));
        argumentsBuilder.add(Arguments.of("3", getDateByWeekDay(3, false), "'19:00'", "king", "Стоимость билета: 150 рублей."));
        argumentsBuilder.add(Arguments.of("3", getDateByWeekDay(3, false), "'19:00'", "crime", "Возраст не подходит для просмотра этого фильма."));

        return argumentsBuilder.build();
    }

    @ParameterizedTest
    @MethodSource("provideCinemaData")
    void test03_source_cinema(String age, String date, String session, String film, String message) {
        open("https://slqamsk.github.io/cases/cinema/v02");
        getWebDriver().manage().window().maximize();
        System.out.println(age + ", " + date + ", " + session + ", " + film + ", " + message);
        $("input[name=age]").setValue(age);
        $("input[name='date']").setValue(date);
        $x("//input[@name='session' and @value=" + session + "]").click();
        $("input[name=film][value='" + film + "']").click();
        $x("//button[@type='submit']").click();
        $x("//div[@id='result']").shouldHave(text(message));

        sleep(1_000);
    }
}