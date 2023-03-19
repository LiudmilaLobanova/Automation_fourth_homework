package ru.netology.cardDelivery;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        Configuration.headless = true;
        open("http://localhost:9999/");


    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void ShouldBeSuccess() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue((ltdFormat));
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(text(), 'Успешно!')]").should(Condition.appear, Duration.ofSeconds(15));

    }

    @Test
    void shouldNotBeSuccessLessTimeToDelivery() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(2);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=date] .input_invalid").should(Condition.appear);

    }

    @Test
    void shouldNotBeSuccessWrongCity() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Лос-Анжелес");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessEmptyCity() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=city].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessWrongLatinName() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Elon Musk");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessWrongNameWithDigit() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("X Æ A-12");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessWrongNameWithSpecialCharacter() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков$");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessWrongEmptyName() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=name].input_invalid").should(Condition.appear);
    }
    @Test
    void shouldNotBeSuccessWrongShortPhone() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+7499111223");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessWrongLongPhone() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+749911122333");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessWrongPhoneHasNotPlus() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessEmptyPhone() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=phone].input_invalid").should(Condition.appear);
    }

    @Test
    void shouldNotBeSuccessCheckMarkNotChecked() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);

        $x("//input[@placeholder='Город']").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+74991112233");
//        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=agreement].input_invalid").should(Condition.appear);
    }


    @Test
    void shouldBeSuccessDropDownListCity() {
        LocalDateTime ldt = LocalDateTime.now().plusDays(3);
        DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String ltdFormat = format1.format(ldt);
        String rand = randomAlphabetic(2);
        char [] alphabet = {'а','б','в','г','д','е','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ш','ю'};
        int rNum1 = (int) (Math.random() * 22);
        int rNum2 = (int) (Math.random() * 22);
        char letter1 = alphabet[rNum1];
        char letter2 = alphabet[rNum2];
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(letter1);
        sb.append(letter2);
        str = sb.toString();



        $x("//input[@placeholder='Город']").setValue(str);
        $(".menu-item__control").click();
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(ltdFormat);
        $x("//input[@name='name']").setValue("Олег-Тиньков");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(text(), 'Успешно!')]").should(Condition.appear, Duration.ofSeconds(15));
    }
}