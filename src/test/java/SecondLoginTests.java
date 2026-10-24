import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class SecondLoginTests {

    //01. Корректные логин и пароль - успешный вход в систему по нажатию кнопки "Login"
    @Test
    void test01SuccessLoginButton() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test01SuccessLoginButton, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldHave(cssClass("success"));

        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);

        closeWindow();
        System.out.println("Закончили тест test01SuccessLoginButton");
    }

    //02. Корректный логин, пароль не соответствует логину - ошибка
    @Test
    void test02ErrorWrongPassword() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test02ErrorWrongPassword, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("wrong_password");
        $("#loginButton").click();

        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldHave(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);

        closeWindow();
        System.out.println("Закончили тест test02ErrorWrongPassword");
    }

    //03. Корректные логин и пароль - успешный вход в систему по нажатию клавиши Enter на клавиатуре
    @Test
    void test03SuccessLoginEnter() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test03SuccessLoginEnter, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").sendKeys("secret_sauce");
        $("#password").pressEnter();

        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));

        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);

        closeWindow();
        System.out.println("Закончили тест test03SuccessLoginEnter");
    }

    //04. Выход из системы
    @Test
    void test04LogoutSuccess() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test04LogoutSuccess, браузер: " + browser);

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

        closeWindow();
        System.out.println("Закончили тест test04LogoutSuccess");
    }

    //05. Некорректный логин, пароль от корректного логина - ошибка
    @Test
    void test05WrongLoginCorrectPassword() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test05WrongLoginCorrectPassword, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("incorrect_login");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);

        closeWindow();
        System.out.println("Закончили тест test05WrongLoginCorrectPassword");
    }

    //06. Корректный логин, пароль от другого корректного логина - ошибка
    //Эту проверку в текущей реализации невозможно реализовать,
    //т.к. у всех пользователей один и тот же пароль
    //но для версии v04 - это реализовано для пользователя user06/user06_password
    @Test
    void test06LoginAndPasswordNotMatched() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test06LoginAndPasswordNotMatched, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/index_v04.html");
        $("#username").sendKeys("user06");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Invalid username or password."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);

        closeWindow();
        System.out.println("Закончили тест test06LoginAndPasswordNotMatched");
    }

    //07. Проверить, что под заблокированным пользователем нельзя войти в систему
    @Test
    void test07ErrorBlockedUser() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test07ErrorBlockedUser, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("locked_out_user");
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Пользователь заблокирован."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);

        closeWindow();
        System.out.println("Закончили тест test07ErrorBlockedUser");
    }

    //08. Пустой логин, пароль от корректного логина
    @Test
    void test08EmptyLoginCorrectPassword() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test08EmptyLoginCorrectPassword, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").clear();
        $("#password").sendKeys("secret_sauce");
        $("#loginButton").click();

        $("#message").shouldHave(text("Username is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);

        closeWindow();
        System.out.println("Закончили тест test08EmptyLoginCorrectPassword");
    }

    //09. Пустой пароль, корректный логин
    @Test
    void test09ErrorEmptyPassword() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test09ErrorEmptyPassword, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").sendKeys("standard_user");
        $("#password").clear();
        $("#loginButton").click();

        $("#message").shouldHave(text("Password is required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);

        closeWindow();
        System.out.println("Закончили тест test09ErrorEmptyPassword");
    }

    //10. Пустые логин и пароль
    @Test
    void test10ErrorEmptyLoginAndPassword() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test10ErrorEmptyLoginAndPassword, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").clear();
        $("#password").clear();
        $("#loginButton").click();

        $("#message").shouldHave(text("Username and Password are required."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("error"));

        $("#greeting").shouldBe(empty);
        $("#greeting").shouldNotBe(visible);

        closeWindow();
        System.out.println("Закончили тест test10ErrorEmptyLoginAndPassword");
    }

    //11. Проверить, что при вводе пароль скрыт за звёздочками
    //Нет технической возможности автоматизировать эту проверку
    //Требуется выполнять эту проверку ручным способом.
    @Test
    void test11PasswordFieldDisplaysAsterisks() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test11PasswordFieldDisplaysAsterisks, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#password").sendKeys("secret_sauce");
        $("#password").shouldHave(attribute("type", "password"));

        closeWindow();
        System.out.println("Закончили тест test11PasswordFieldDisplaysAsterisks");
    }

    //12. Проверить вход в систему под несколькими разными логинами
    @Test
    void test12SuccessLoginsDifferentUsers() {
        String browser = new String[]{"chrome", "firefox", "edge"}[(int) (Math.random() * 3)];
        Configuration.browser = browser;
        System.out.println("Начали тест test12SuccessLoginsDifferentUsers, браузер: " + browser);

        open("https://slqa.ru/cases/ChatGPTLogin/");
        $("#username").setValue("standard_user");
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, standard_user!"));
        $("#greeting").shouldBe(visible);
        $("#logoutButton").click();

        $("#username").setValue("problem_user");
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, problem_user!"));
        $("#greeting").shouldBe(visible);
        $("#logoutButton").click();

        $("#username").setValue("performance_glitch_user");
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, performance_glitch_user!"));
        $("#greeting").shouldBe(visible);
        $("#logoutButton").click();

        $("#username").setValue("error_user");
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, error_user!"));
        $("#greeting").shouldBe(visible);
        $("#logoutButton").click();

        $("#username").setValue("visual_user");
        $("#password").setValue("secret_sauce");
        $("#loginButton").click();
        $("#message").shouldHave(text("Вход в систему выполнен успешно! Загрузка..."));
        $("#message").shouldBe(visible);
        $("#message").shouldBe(cssClass("success"));
        $("#greeting").shouldHave(text("Welcome, visual_user!"));
        $("#greeting").shouldBe(visible);
        $("#logoutButton").click();

        closeWindow();
        System.out.println("Закончили тест test12SuccessLoginsDifferentUsers");
    }
}