package org.agile.qa.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlightSearchPage extends BaseAuthPage {

    private static final long TIMEOUT = 10;
    private WebDriver driver;
    private WebDriverWait wait;

    public FlightSearchPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }


    @FindBy(id = "myInputnumber")
    private WebElement flightNumberInput;

    @FindBy(id = "myInputname")
    private WebElement flightNameInput;

    @FindBy(id = "myInputtype")
    private WebElement flightTypeInput;

    @FindBy(id = "mySearchnumber")
    private WebElement searchByFlightNumberButton;

    @FindBy(id = "mySearchname")
    private WebElement searchByFlightNameButton;

    @FindBy(id = "mySearchtype")
    private WebElement searchByFlightTypeButton;

    @FindBy(id = "myTable")
    private WebElement flightTable;

    @FindBy(id = "flightSearchHeading")
    private WebElement heading;



    private void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }



    public void enterFlightNumber(String number) {
        waitForVisibility(flightNumberInput);
        flightNumberInput.clear();
        flightNumberInput.sendKeys(number);
    }

    public void enterFlightName(String name) {
        waitForVisibility(flightNameInput);
        flightNameInput.clear();
        flightNameInput.sendKeys(name);
    }

    public void enterFlightType(String type) {
        waitForVisibility(flightTypeInput);
        flightTypeInput.clear();
        flightTypeInput.sendKeys(type);
    }

    public void clickSearchByFlightNumber() {
        waitForClickable(searchByFlightNumberButton);
        searchByFlightNumberButton.click();
    }

    public void clickSearchByFlightName() {
        waitForClickable(searchByFlightNameButton);
        searchByFlightNameButton.click();
    }

    public void clickSearchByFlightType() {
        waitForClickable(searchByFlightTypeButton);
        searchByFlightTypeButton.click();
    }

    public boolean isFlightTableDisplayed() {
        waitForVisibility(flightTable);
        return flightTable.isDisplayed();
    }

    public String getPageHeading() {
        waitForVisibility(heading);
        return heading.getText().trim();
    }
}
