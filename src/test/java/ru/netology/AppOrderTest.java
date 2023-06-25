package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppOrderTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void formPositiveTest() throws InterruptedException {

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иванов И");
        form.$("input[name=phone]").setValue("+12345678901");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
        $(withText("успешно отправлена")).shouldBe(visible);
    }

    @Test
    public void validationNameUnsupportedSymbolsTest() {

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Ivanov Ivan");
        form.$("input[name=phone]").setValue("+12345678901");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$("[data-test-id=name]").$(".input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
        form.$("[data-test-id=name]").shouldHave(Condition.cssClass("input_invalid"));

    }

    @Test
    public void validationPhoneNotCompleteTest() {

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иванов и");
        form.$("input[name=phone]").setValue("+1234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$("[data-test-id=phone]").$(".input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
        form.$("[data-test-id=phone]").shouldHave(Condition.cssClass("input_invalid"));

    }

    @Test
    public void validationEmptyCheckboxTest() {

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иванов и");
        form.$("input[name=phone]").setValue("+12345678900");
        form.$("button").click();
        form.$("[data-test-id=agreement]").shouldHave(Condition.cssClass("input_invalid"));
    }
    @Test
    public void validationEmptyNameTest() {

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("");
        form.$("input[name=phone]").setValue("+12345678900");
        form.$("button").click();
        form.$("[data-test-id=name]").$(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        form.$("[data-test-id=name]").shouldHave(Condition.cssClass("input_invalid"));
    }
    @Test
    public void validationEmptyPhoneTest() {

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иваноов Петр");
        form.$("input[name=phone]").setValue("");
        form.$("button").click();
        form.$("[data-test-id=phone]").$(".input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
        form.$("[data-test-id=phone]").shouldHave(Condition.cssClass("input_invalid"));

    }
}
