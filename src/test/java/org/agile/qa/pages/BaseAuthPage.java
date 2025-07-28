package org.agile.qa.pages;

import java.time.Duration;

import org.agile.qa.stepDefinitions.BookingTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseAuthPage {

    private static final long TIMEOUT = 10;

	protected WebDriver driver;
	private static final Logger logger = LoggerFactory.getLogger(BaseAuthPage.class);

    @FindBy(id = "username") protected WebElement usernameBox;
    @FindBy(id = "password") protected WebElement passwordBox;
    @FindBy(id = "code") protected WebElement captchaCode;
    @FindBy(id = "captcha") protected WebElement captchaBox;
    @FindBy(id = "captchaBtn") protected WebElement captchaValidate;
    @FindBy(id = "remember_me") protected WebElement rememberMeinput;
    @FindBy(id = "login-submit") protected WebElement submitButton;    


    protected BaseAuthPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
	
	
	protected WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }
	
    public void setUsername(String uname) {
        if (uname == null) {
            uname = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(usernameBox));
        usernameBox.clear();
        usernameBox.sendKeys(uname);
    }

    public void setPassword(String passwd) {
        if (passwd == null) {
            passwd = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(passwordBox));
        passwordBox.clear();
        passwordBox.sendKeys(passwd);
    }

    public void setCaptcha(String captcha) {
        if (captcha == null) {
            captcha = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(captchaBox));
        captchaBox.clear();
        captchaBox.sendKeys(captcha);
    }

    public String getCaptchaCode() {
        getWait().until(ExpectedConditions.visibilityOf(captchaCode));
        return captchaCode.getAttribute("textContent").trim();
    }

    public WebElement getUsernameBox() {
        return usernameBox;
    }

    public WebElement getPasswordBox() {
        return passwordBox;
    }

    public WebElement getCaptchaCodeElement() {
        return captchaCode;
    }

    public WebElement getCaptchaBox() {
        return captchaBox;
    }

    public WebElement getCaptchaValidateButton() {
        return captchaValidate;
    }

    public WebElement getRememberMeInput() {
        return rememberMeinput;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
    
    public void validateCaptcha() {
    	getWait().until(ExpectedConditions.elementToBeClickable(captchaValidate));
    	captchaValidate.click();
    	
    	driver.switchTo().alert().accept();
    }
    
    public void clickLoginBtn() {
        getWait().until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
        // After click, check for alert
        try {
            // Wait briefly as needed (not with Thread.sleep) if alert may take time to show up
            Alert alert = driver.switchTo().alert();
            alert.accept();
            logger.info("Alert found and accepted after login click.");
        } catch (NoAlertPresentException e) {
            logger.info("No alert present after login click.");
            // You can proceed safely; it's not an error
        }
    }

    public void clickRememberMe() {
        getWait().until(ExpectedConditions.elementToBeClickable(rememberMeinput));
        if (!rememberMeinput.isSelected()) {
            rememberMeinput.click();
        }
    }
	
}
