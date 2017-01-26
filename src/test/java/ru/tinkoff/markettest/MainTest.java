package ru.tinkoff.markettest;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.tinkoff.pom.HomePage;
import ru.tinkoff.pom.MobilePhonesPage;

import java.util.concurrent.TimeUnit;


public class MainTest {


    private WebDriver driver;
    private String baseUrl;

    @BeforeSuite
    public void setUp() throws Exception {
//        System.setProperty("webdriver.chrome.driver", "C:\\Autotests\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        baseUrl = "https://market.yandex.ru/";
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }

    @DataProvider(name = "Prices")
    public Object[][] createData() {
        return new Object[][]{
                {"5125", "10123"}
        };
    }

    @Test(dataProvider = "Prices")
    public void marketTest(String minPrice, String maxPrice) {
        driver.get(baseUrl);
        HomePage homePage = new HomePage(driver);
        homePage.pointCursor();
        MobilePhonesPage mobilePhonesPage = homePage.clickMenuElectronics();
        mobilePhonesPage.setPrices(minPrice, maxPrice);
        mobilePhonesPage.checkNecessaryBoxes();
        mobilePhonesPage.print3RandomPhonesInfo();
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
