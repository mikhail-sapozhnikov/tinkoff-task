package ru.tinkoff.pom;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    private final WebDriver driver;

    @FindBy(css = "li[data-department='Электроника']")
    private WebElement menuElectronics;

    @FindBy(linkText = "Мобильные телефоны")
    private WebElement menuMobilePhones;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void pointCursor() {
        Actions action = new Actions(this.driver);
        action.moveToElement(menuElectronics);
        action.perform();
    }

    public MobilePhonesPage clickMenuElectronics() {
        menuMobilePhones.click();
        return new MobilePhonesPage(this.driver);
    }

}
