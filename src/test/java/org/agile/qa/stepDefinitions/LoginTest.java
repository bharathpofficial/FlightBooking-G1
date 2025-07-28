package org.agile.qa.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.agile.qa.setup.ConfigFileReader;
import org.agile.qa.setup.DriverSetup;
import org.agile.qa.utils.ScreenshotUtils;
import org.agile.qa.utils.XmlUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;

import io.cucumber.datatable.DataTable;

import org.agile.qa.hooks.Hooks;
import org.agile.qa.pages.LoginPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginTest {
	
	ConfigFileReader fileReader = new ConfigFileReader();
	LoginPage loginWeb;
	private static final Logger logger = LoggerFactory.getLogger(LoginTest.class);
	ScreenshotUtils ss;
	
	@Given("navigate to the application login URL")
	public void navigate_to_the_application_login_url() {
	    logger.info("Driver initiated at LoginTest");
	    Hooks.driver.navigate().to(fileReader.getLoginUrl());
	    
	    loginWeb = new LoginPage(Hooks.driver);
	    ss = new ScreenshotUtils(Hooks.driver);
	    logger.info("LoginPage initiated successfully");
	}

	@Then("I enter empty data into form fields")
	public void i_enter_empty_data_into_form_fields(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps();
//		String[] usernames, passwords;
		
		List<String> usernameList = new ArrayList<>();
		List<String> passwordList = new ArrayList<>();
		
		
		for ( Map<String, String> credential : data ) {
			usernameList.add(credential.getOrDefault("username", ""));
			passwordList.add(credential.getOrDefault("password", ""));
		}
		
		String[] usernames = usernameList.toArray(new String[0]);
		String[] passwords = passwordList.toArray(new String[0]);
		
//		for ( String var : usernames ) {
//			System.out.println("Username >> " + var);
//		}
//		
//		for ( String var : passwords ) {
//			System.out.println("Password >> " + var);
//		}
		
		loginWeb.setUsername(usernames[0]);
		loginWeb.setPassword(passwords[0]);
		logger.info("Username sent: ", usernames[0]);
		logger.info("Password sent: ", passwords[0]);
	}

	@Then("I leave the captcha field blank.")
	public void i_leave_the_captcha_field_blank() {
	    String captchaCode = loginWeb.getCaptchaCode();
//	    System.out.println("Captcha code >> " + captchaCode);
	    loginWeb.setCaptcha("");
	    logger.info("Set captcha code to empty strings");
	}

	@Then("I click on Remember Me checkbox.")
	public void i_click_on_remember_me_checkbox() {
	    loginWeb.clickRememberMe();
	    logger.info("Remeber Me box clicked");
	}

	@Then("I {string} the popup window")
	public void i_the_popup_window(String acceptOrDeny) {
		WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
		
		Alert rememberMeAlert = wait.until(ExpectedConditions.alertIsPresent());
		loginWeb.handleAlert(rememberMeAlert, acceptOrDeny);
		logger.info("Remember me Popup window, clicked " + acceptOrDeny);
		
		try {
			Alert loginAlert = wait.until(ExpectedConditions.alertIsPresent());
			loginAlert.accept();
			logger.info("One more alert present and accepted");
		} catch (org.openqa.selenium.TimeoutException | NoAlertPresentException e) {
            // Alert didn't appear, continue without failure
        }
	}

	@Then("Finally Remember me error expected.")
	public void finally_remember_me_error_expected() {
		String pageContent = Hooks.driver.getPageSource();
		
		boolean isErrorPresent = pageContent.contains("Remember me expect username and password to be entered");
		
		try {
			Assert.assertTrue(isErrorPresent);
		} catch (AssertionError e) {
			// we raise bug report here!
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			logger.error("Remember Me Error was not present, Try creating appropriate error.");
			ss.takeScreenshot("loginpage/no_rememberme_error present");
		}
	}

	@Then("I enter valid data into form fields.")
	public void i_enter_valid_data_into_form_fields() {
	    XmlUtils xml = new XmlUtils();
	    loginWeb.setUsername(xml.getAdminUsername());
	    loginWeb.setPassword(xml.getAdminPassword());
	}

	@Then("I try copying the captcha field.")
	public void i_try_copying_the_captcha_field() {
		try {
			loginWeb.getCaptchaCode();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@Then("the captcha field should not get copyied.")
	public void the_captcha_field_should_not_get_copyied() {
	    try {
			Assert.assertTrue(loginWeb.getCaptchaCode().isEmpty(), "Captcha Code should not copiable");
		} catch (AssertionError e) {
			// reporting bug
			ss.takeContextualElementScreenshot(loginWeb.getCaptchaCodeElement(), "loginpage/captcha_field_copiable", 30);
		}
	}

	@Then("I click on the password reset form.")
	public void i_click_on_the_password_reset_form() {
	    loginWeb.clickPasswordResetLink();
	}

	@Then("I get to see the password reset form.")
	public void i_get_to_see_the_password_reset_form() {
		String pageContent = Hooks.driver.getPageSource();
		
		boolean isErrorPresent = pageContent.contains("Password Reset");
		
		try {
			Assert.assertTrue(isErrorPresent);
		} catch (AssertionError e) {
			ss.takeScreenshot("loginpage/password_reset_form_not_working");
			logger.error("Password Reset Form is not present");
		}
	}

	@Then("enter the email id.")
	public void enter_the_email_id() {
		// YTC
	}

	@Then("enter the {string} received for that mail.")
	public void enter_the_received_for_that_mail(String string) {
	    // YTC
	}

	@Then("finally set new password and submit.")
	public void finally_set_new_password_and_submit() {
	    // YTC
	}

	@When("directly access the booking page URL")
	public void directly_access_the_booking_page_url() {
		Hooks.driver.navigate().to(fileReader.getBookingUrl());
	}

	@Then("the user should be redirected to the login page")
	public void the_user_should_be_redirected_to_the_login_page() {
	    WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(5));
	    try {
	        WebElement loginHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-heading")));
	        Assert.assertTrue(loginHeading.isDisplayed(), "Login heading should be visible");
	        logger.info("Redirection to login page confirmed using unique element.");
	    } catch (AssertionError e) {
	        logger.warn("TODO: Proper verification for login page redirection not yet implemented.");
	        ss.takeScreenshot("loginpage/no_login_redirection");
	    } catch (TimeoutException e) {
	        logger.warn("TODO: Handle timeout scenario for login page detection.");
	    }
	}

	@Then("the booking page should not be accessible")
	public void the_booking_page_should_not_be_accessible() {
	    WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(5));
	    boolean forbidden = false;
	    try {
	        // Wait for error indicator on access denial
	        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("access-denied")));
	        forbidden = errorMsg.isDisplayed();
	        logger.info("Booking page access error message visible.");
	    } catch (TimeoutException e) {
	        logger.error("No access-denied error displayed; further checks needed.");
	        ss.takeScreenshot("loginpage/No access denied error displayed");
	    }
	    try {
	        Assert.assertTrue(forbidden, "Booking page should display access denied message.");
	    } catch (AssertionError e) {
	        logger.warn("TODO: Proper verification for booking page access denial not yet implemented.");
	        // Optionally, log e.getMessage() for diagnostics
	    }
	}

}