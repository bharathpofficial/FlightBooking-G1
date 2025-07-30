package org.agile.qa.pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnquiryFormPage {

    private static final long TIMEOUT = 10;
    private WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(EnquiryFormPage.class);


    @FindBy(id = "enquiry-subject") protected WebElement subjectBox;
    @FindBy(id = "enquiry-email") protected WebElement emailBox;
    @FindBy(id = "enquiry-username") protected WebElement usernameBox;
    @FindBy(id = "enquiry-message") protected WebElement messageBox;
    @FindBy(id = "enquiry-phone") protected WebElement phoneBox;
    @FindBy(id = "enquiry-submit") protected WebElement submitButton;
    @FindBy(id = "enquiry-form-link") protected WebElement enquiryFormLink;
    
  
    @FindBy(id = "subjectErr") protected WebElement subjectErrElmt;
    @FindBy(id = "emailErr") protected WebElement emailErrElmt;
    @FindBy(id = "usernameErr") protected WebElement usernameErrElmt;
    @FindBy(id = "messageErr") protected WebElement messageErrElmt;
    @FindBy(id = "phoneErr") protected WebElement phoneErrElmt;
    
    @FindBy(xpath = "//div[contains(@class, 'error') and contains(text(), 'subject')]") protected WebElement subjectErrorAlt;
    @FindBy(xpath = "//div[contains(@class, 'error') and contains(text(), 'email')]") protected WebElement emailErrorAlt;
    @FindBy(xpath = "//div[contains(@class, 'error') and contains(text(), 'username')]") protected WebElement usernameErrorAlt;
    @FindBy(xpath = "//div[contains(@class, 'error') and contains(text(), 'message')]") protected WebElement messageErrorAlt;
    @FindBy(xpath = "//div[contains(@class, 'error') and contains(text(), 'phone')]") protected WebElement phoneErrorAlt;

    public EnquiryFormPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    protected WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }

    public void navigateToEnquiryForm() {
        try {
            getWait().until(ExpectedConditions.elementToBeClickable(enquiryFormLink));
            enquiryFormLink.click();
            logger.info("Clicked on enquiry form link");
        } catch (Exception e) {
            logger.warn("Enquiry form link not found, trying direct navigation");
            
        }
    }

    public void setSubject(String subject) {
        if (subject == null) {
            subject = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(subjectBox));
        subjectBox.clear();
        subjectBox.sendKeys(subject);
        logger.info("Subject entered: " + subject);
    }

    public void setEmail(String email) {
        if (email == null) {
            email = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(emailBox));
        emailBox.clear();
        emailBox.sendKeys(email);
        logger.info("Email entered: " + email);
    }

    public void setUsername(String username) {
        if (username == null) {
            username = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(usernameBox));
        usernameBox.clear();
        usernameBox.sendKeys(username);
        logger.info("Username entered: " + username);
    }

    public void setMessage(String message) {
        if (message == null) {
            message = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(messageBox));
        messageBox.clear();
        messageBox.sendKeys(message);
        logger.info("Message entered: " + message);
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            phoneNumber = "";
        }
        getWait().until(ExpectedConditions.visibilityOf(phoneBox));
        phoneBox.clear();
        phoneBox.sendKeys(phoneNumber);
        logger.info("Phone number entered: " + phoneNumber);
    }

    public void clickSubmitButton() {
        getWait().until(ExpectedConditions.elementToBeClickable(submitButton));
        submitButton.click();
        logger.info("Enquiry form submit button clicked");
       
        try {
            Thread.sleep(500);
            Alert alert = driver.switchTo().alert();
            alert.accept();
            logger.info("Alert found and accepted after enquiry form submission.");
        } catch (NoAlertPresentException e) {
            logger.info("No alert present after enquiry form submission.");
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted while waiting for alert");
        }
    }


    public String getSubjectError() {
        try {
            getWait().until(ExpectedConditions.visibilityOf(subjectErrElmt));
            return subjectErrElmt.getText();
        } catch (Exception e) {
            logger.warn("Primary subject error element not found, trying alternative");
            try {
                getWait().until(ExpectedConditions.visibilityOf(subjectErrorAlt));
                return subjectErrorAlt.getText();
            } catch (Exception ex) {
                logger.error("No subject error message found");
                return "";
            }
        }
    }

    public String getEmailError() {
        try {
            getWait().until(ExpectedConditions.visibilityOf(emailErrElmt));
            return emailErrElmt.getText();
        } catch (Exception e) {
            logger.warn("Primary email error element not found, trying alternative");
            try {
                getWait().until(ExpectedConditions.visibilityOf(emailErrorAlt));
                return emailErrorAlt.getText();
            } catch (Exception ex) {
                logger.error("No email error message found");
                return "";
            }
        }
    }

    public String getUsernameError() {
        try {
            getWait().until(ExpectedConditions.visibilityOf(usernameErrElmt));
            return usernameErrElmt.getText();
        } catch (Exception e) {
            logger.warn("Primary username error element not found, trying alternative");
            try {
                getWait().until(ExpectedConditions.visibilityOf(usernameErrorAlt));
                return usernameErrorAlt.getText();
            } catch (Exception ex) {
                logger.error("No username error message found");
                return "";
            }
        }
    }

    public String getMessageError() {
        try {
            getWait().until(ExpectedConditions.visibilityOf(messageErrElmt));
            return messageErrElmt.getText();
        } catch (Exception e) {
            logger.warn("Primary message error element not found, trying alternative");
            try {
                getWait().until(ExpectedConditions.visibilityOf(messageErrorAlt));
                return messageErrorAlt.getText();
            } catch (Exception ex) {
                logger.error("No message error message found");
                return "";
            }
        }
    }

    public String getPhoneError() {
        try {
            getWait().until(ExpectedConditions.visibilityOf(phoneErrElmt));
            return phoneErrElmt.getText();
        } catch (Exception e) {
            logger.warn("Primary phone error element not found, trying alternative");
            try {
                getWait().until(ExpectedConditions.visibilityOf(phoneErrorAlt));
                return phoneErrorAlt.getText();
            } catch (Exception ex) {
                logger.error("No phone error message found");
                return "";
            }
        }
    }

  
    public WebElement getSubjectBox() {
        return subjectBox;
    }

    public WebElement getEmailBox() {
        return emailBox;
    }

    public WebElement getUsernameBox() {
        return usernameBox;
    }

    public WebElement getMessageBox() {
        return messageBox;
    }

    public WebElement getPhoneBox() {
        return phoneBox;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

   
    public boolean isEnquiryFormDisplayed() {
        try {
            return getWait().until(ExpectedConditions.visibilityOf(submitButton)).isDisplayed();
        } catch (Exception e) {
            logger.error("Enquiry form is not displayed");
            return false;
        }
    }

  
    public void clearAllFields() {
        try {
            subjectBox.clear();
            emailBox.clear();
            usernameBox.clear();
            messageBox.clear();
            phoneBox.clear();
            logger.info("All enquiry form fields cleared");
        } catch (Exception e) {
            logger.error("Error clearing form fields: " + e.getMessage());
        }
    }

    
    public void handleAlert(Alert alert, String action) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted while handling alert");
        }

        if ("accept".equalsIgnoreCase(action)) {
            alert.accept();
            logger.info("Alert accepted");
        } else {
            alert.dismiss();
            logger.info("Alert dismissed");
        }
    }
}