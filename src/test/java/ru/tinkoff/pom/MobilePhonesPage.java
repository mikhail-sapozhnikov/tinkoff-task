package ru.tinkoff.pom;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class MobilePhonesPage {

    private final WebDriver driver;
    Logger logger = Logger.getLogger("MainTest");
    FileHandler fh;
    @FindBy(id = "glf-pricefrom-var")
    private WebElement minPriceField;
    @FindBy(id = "glf-priceto-var")
    private WebElement maxPriceField;
    @FindBy(id = "glf-onstock-select")
    private WebElement cbOnSale;
    @FindBy(xpath = "//span[.='Тип']")
    private WebElement itemType;
    @FindBy(xpath = "//span[.='смартфон']")
    private WebElement cbSmartphone;
    @FindBy(xpath = "//span[.='Android']")
    private WebElement cbAndroid;

    @FindBy(css = "div[class='snippet-card__view'] > div")
    private List<WebElement> ratingList;
    @FindBy(css = "h3[class='snippet-card__header i-bem snippet-card__header_js_inited'] > a")
    private List<WebElement> phoneNamesList;
    @FindBy(css = "div[class='snippet-card__info'] > a")
    private List<WebElement> phonePrices;

    public MobilePhonesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void setPrices(String minPrice, String maxPrice) {
        minPriceField.sendKeys(minPrice);
        maxPriceField.sendKeys(maxPrice);
    }

    private void waitUntilReadyStateComplete() {
        String js = "return document.readyState";
        ExpectedCondition<Boolean> pageLoaded =
                driver -> ((JavascriptExecutor) driver).executeScript(js).equals("complete");
        new WebDriverWait(driver, 5).until(pageLoaded);
    }

    public void checkNecessaryBoxes() {
        if (!cbOnSale.isSelected()) cbOnSale.click();
        itemType.click();
        cbSmartphone.click();
        if (!cbAndroid.isSelected()) cbAndroid.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        waitUntilReadyStateComplete();
    }

    public void print3RandomPhonesInfo() {

        try {
            fh = new FileHandler("logs/MyLogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info("Смартфоны:");


        } catch (IOException e) {
            e.printStackTrace();
        }

        int i = 0;
        ArrayList<Integer> indexesOfPhonesWithNeededRating = new ArrayList<>();

        for (WebElement we : ratingList) {

            if (we.getText().equals("3.5")
                    || we.getText().equals("4.0")
                    || we.getText().equals("4.5")) {
                indexesOfPhonesWithNeededRating.add(i);
            }

            i++;
        }

        Collections.shuffle(indexesOfPhonesWithNeededRating);

        int first = indexesOfPhonesWithNeededRating.get(0);
        int second = indexesOfPhonesWithNeededRating.get(1);
        int third = indexesOfPhonesWithNeededRating.get(2);

        logger.info((first + 1) + " - " + phoneNamesList.get(first).getText() + " - "
                + phonePrices.get(first).getText().replaceAll("\n", " "));
        logger.info((second + 1) + " - " + phoneNamesList.get(second).getText() + " - "
                + phonePrices.get(second).getText().replaceAll("\n", " "));
        logger.info((third + 1) + " - " + phoneNamesList.get(third).getText() + " - "
                + phonePrices.get(third).getText().replaceAll("\n", " "));
    }


}
