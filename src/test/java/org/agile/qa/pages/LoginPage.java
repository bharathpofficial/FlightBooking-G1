package org.agile.qa.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BaseAuthPage {
	private WebDriver driver;
	private static final int TIMEOUT = 10;
		
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "reset-password-link") protected WebElement passwordResetLink;
    @FindBy(id = "usernameErr") protected WebElement usernameErrElmt;
    @FindBy(id = "passwordErr") protected WebElement passwordErrElmt;
    @FindBy(id = "captchaErr") protected WebElement captchaErrElmt;
    
    public void clickPasswordResetLink() {
    	getWait().until(ExpectedConditions.elementToBeClickable(passwordResetLink));
    	passwordResetLink.click();
    }
    
    public String getUsernameError() {
        getWait().until(ExpectedConditions.visibilityOf(usernameErrElmt));
        return usernameErrElmt.getText();
    }

    public String getPasswordError() {
        getWait().until(ExpectedConditions.visibilityOf(passwordErrElmt));
        return passwordErrElmt.getText();
    }

    public String getCaptchaError() {
        getWait().until(ExpectedConditions.visibilityOf(captchaErrElmt));
        return captchaErrElmt.getText();
    }

	public void handleAlert(Alert alert, String action) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		// todo	
		}
		
	    if ("accept".equalsIgnoreCase(action)) {
	        alert.accept();
	    } else {
	        alert.dismiss();
	    }
	}
	
}