package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class AppOrderTest {

    @Test
    public void Test1() {
        open("http://localhost:9999");

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иванов Иван");
        form.$("input[name=phone]").setValue("+12345678901");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));


    }

    @Test
    public void Test2() {
        open("http://localhost:9999");

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иванов Иdfy");
        form.$("input[name=phone]").setValue("+12345678901");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$("[data-test-id=name]").$(".input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));


    }

    @Test
    public void Test3() {
        open("http://localhost:9999");

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иванов иван");
        form.$("input[name=phone]").setValue("+1234567890");
        form.$("[data-test-id=agreement]").click();
        form.$("button").click();
        form.$("[data-test-id=phone]").$(".input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));


    }

    @Test
    public void Test4() {
        open("http://localhost:9999");

        SelenideElement form = $("form");
        form.$("input[name=name]").setValue("Иванов иван");
        form.$("input[name=phone]").setValue("+12345678900");
        form.$("button").click();
        form.$("[data-test-id=agreement]").shouldHave(Condition.cssClass("input_invalid"));


    }


}
