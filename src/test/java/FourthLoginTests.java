import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class FourthLoginTests {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Начали выполнять набор тестов");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Закончили выполнять набор тестов");
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Тест " + testInfo.getDisplayName() + " - начали выполнение. Браузер: " + browser);
    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        closeWindow();
        System.out.println("Тест " + testInfo.getDisplayName() + " - закончили выполнение.");
    }

    //01. Корректные логин и пароль - успешный вход в систему по нажатию кнопки "Login"
    @Test
    void test01SuccessLoginButton() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldHave(cssClass("success"));

        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);
    }

    //02. Корректный логин, пароль не соответствует логину - ошибка
    @Test
    void test02ErrorWrongPassword() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("wrong_password");
        $("#loginButton").click();

        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldHave(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    //03. Корректные логин и пароль - успешный вход в систему по нажатию клавиши Enter на клавиатуре
    @Test
    void test03SuccessLoginEnter() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#password").pressEnter();

        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));

        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);
    }

    //04. Выход из системы
    @Test
    void test04LogoutSuccess() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#logoutButton").click();

        $("#logoutButton").shouldNotBe(visible);
        $("#loginContainer").shouldBe(visible);
        $("#loginInfo").shouldBe(visible);
        $("#username").shouldBe(visible);
        $("#password").shouldBe(visible);
        $("#loginButton").shouldBe(visible);
    }

    //05. Некорректный логин, пароль от корректного логина - ошибка
    @Test
    void test05WrongLoginCorrectPassword() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("incorrect_login");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    //06. Корректный логин, пароль от другого корректного логина - ошибка
    //Эту проверку в текущей реализации невозможно реализовать,
    //т.к. у всех пользователей один и тот же пароль
    //но для версии v04 - это реализовано для пользователя user06/user06_password
    @Test
    void test06LoginAndPasswordNotMatched() {
        open("https://slqa.ru/cases/ChatGPTLogin/index_v04.html");
        $("#username").sendKeys("user06");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    //07. Проверить, что под заблокированным пользователем нельзя войти в систему
    @Test
    void test07ErrorBlockedUser() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("locked_out_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Пользователь заблокирован."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    //08. Пустой логин, пароль от корректного логина
    @Test
    void test08EmptyLoginCorrectPassword() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").clear();
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Username is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    //09. Пустой пароль, корректный логин
    @Test
    void test09ErrorEmptyPassword() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").clear();
        $("#loginButton").click();

        $("#message").shouldHave(text("Password is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    //10. Пустые логин и пароль
    @Test
    void test10ErrorEmptyLoginAndPassword() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").clear();
        $("#password").clear();
        $("#loginButton").click();

        $("#message").shouldHave(text("Username and Password are required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);
    }

    //11. Проверить, что при вводе пароль скрыт за звёздочками
    //Нет технической возможности автоматизировать эту проверку
    //Требуется выполнять эту проверку ручным способом.
    @Test
    void test11PasswordFieldDisplaysAsterisks() {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#password").sendKeys("secret_sauce");
        $("#password").shouldHave(attribute("type", "password"));
    }

    //12. Проверить вход в систему под несколькими разными логинами
    @ParameterizedTest (name = "test12SuccessLoginsDifferentUsers #{index}, username: {0}")
    @ValueSource(strings = { "standard_user", "problem_user", "visual_user", "performance_glitch_user", "error_user" })
    void test12SuccessLoginsDifferentUsers(String username) {
        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").setValue(username);
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, " + username + "!"));
        $("#greeting").shouldBe(visible);
        $("#logoutButton").click();
    }
}