package org.agile.qa.pages;

import java.time.Duration;

import org.agile.qa.setup.DriverSetup;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BookingPage extends BaseAuthPage {
	
	private static final long TIMEOUT = 10;
	public WebDriver driver;
	private WebDriverWait wait;
	
    public BookingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }
    
    @FindBy(id = "travelFrom")
    private WebElement travelFromInput;

    @FindBy(id = "travelTo")
    private WebElement travelToInput;

    @FindBy(id = "departure")
    private WebElement departureInput;

    @FindBy(id = "selectclass")
    private WebElement selectClassDropdown;

    @FindBy(id = "name")
    private WebElement passengerNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "phone")
    private WebElement phoneInput;

    @FindBy(id = "ticket-class-count")
    private WebElement ticketClassCountInput;

    @FindBy(id = "ticket-class-increase")
    private WebElement ticketClassIncreaseButton;

    @FindBy(id = "book-now")
    private WebElement bookNowButton;

    @FindBy(id = "reset-now")
    private WebElement resetNowButton;

    @FindBy(id = "total-amount")
    private WebElement totalAmountSpan;

    // Private helper for waiting element visibility
    private void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Private helper for waiting element to be clickable
    private void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Interaction methods

    public void setTravelFrom(String from) {
        waitForVisibility(travelFromInput);
        travelFromInput.clear();
        travelFromInput.sendKeys(from);
    }

    public void setTravelTo(String to) {
        waitForVisibility(travelToInput);
        travelToInput.clear();
        travelToInput.sendKeys(to);
    }

    public void setDepartureDate(String date) {
        waitForVisibility(departureInput);
        departureInput.clear();
        departureInput.sendKeys(date);
    }

    public void selectClass(String classValue) {
        waitForVisibility(selectClassDropdown);
        org.openqa.selenium.support.ui.Select dropdown = new org.openqa.selenium.support.ui.Select(selectClassDropdown);
        dropdown.selectByVisibleText(classValue);
    }

    public void setPassengerName(String name) {
        waitForVisibility(passengerNameInput);
        passengerNameInput.clear();
        passengerNameInput.sendKeys(name);
    }

    public void setEmail(String email) {
        waitForVisibility(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void setPhone(String phone) {
        waitForVisibility(phoneInput);
        phoneInput.clear();
        phoneInput.sendKeys(phone);
    }

    public int getTicketCount() {
        waitForVisibility(ticketClassCountInput);
        try {
            return Integer.parseInt(ticketClassCountInput.getAttribute("value"));
        } catch (NumberFormatException e) {
            return 0; // Default to 0 if parsing fails
        }
    }

    public void increaseTicketCountTo(int targetCount) {
        waitForVisibility(ticketClassIncreaseButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ticketClassIncreaseButton);
        final int[] currentCount = {getTicketCount()};
        while (currentCount[0] < targetCount) {
            waitForClickable(ticketClassIncreaseButton);
            ticketClassIncreaseButton.click();
            currentCount[0]++;
            wait.until(driver -> getTicketCount() == currentCount[0]);
        }
    }

    public void clickBookNow() {
        waitForClickable(bookNowButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookNowButton);
        bookNowButton.click();
    }

    public void clickResetNow() {
        waitForClickable(resetNowButton);
        resetNowButton.click();
    }

    public String getTotalAmount() {
        waitForVisibility(totalAmountSpan);
        return totalAmountSpan.getText().trim();
    }
	
}
