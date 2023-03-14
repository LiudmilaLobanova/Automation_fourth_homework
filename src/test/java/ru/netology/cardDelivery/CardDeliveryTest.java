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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--disable-dev-shm-usage");
//        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
//        driver = new ChromeDriver(options);
        driver = new ChromeDriver();

    }

    @AfterEach
    void tearDown(){
         driver.quit();
         driver = null;
    }

    @Test
    void ShouldOpenWindow() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "800x1000";
        open("http://localhost:9999/");

        $x("//input[@placeholder='Город']").setValue("Москва");
//        $(withText("Город")).setValue("Москва");
//        $("[data-test-id=city]").setValue("Москва");
//        driver.findElement(By.cssSelector("input [data-test-id=city]")).sendKeys("Москва");
        $x("//input[@name='name']").setValue("Ирина Мордвинова");
        $x("//input[@name='phone']").setValue("+74991112233");
        $("[data-test-id=agreement]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//*[contains(text(), 'Успешно!')]").should(Condition.appear, Duration.ofSeconds(15));







    }



}
