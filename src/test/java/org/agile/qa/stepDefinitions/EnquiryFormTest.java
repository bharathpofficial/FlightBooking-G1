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
import org.agile.qa.pages.LoginPage;
import org.agile.qa.hooks.Hooks;
import org.agile.qa.pages.EnquiryFormPage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnquiryFormTest {

	ConfigFileReader fileReader = new ConfigFileReader();
	LoginPage loginWeb;
	EnquiryFormPage enquiryFormPage;
	private static final Logger logger = LoggerFactory.getLogger(EnquiryFormTest.class);
	ScreenshotUtils ss;
	
	@Given("navigate to the application login URL to enquiry")
	public void navigate_to_the_application_login_url_to_enquiry() {

	    logger.info("Driver initiated at EnquiryFormTest");
	    Hooks.driver.navigate().to(fileReader.getLoginUrl());
	    
	    loginWeb = new LoginPage(Hooks.driver);
	    enquiryFormPage = new EnquiryFormPage(Hooks.driver);
	    ss = new ScreenshotUtils(Hooks.driver);
	    logger.info("LoginPage and EnquiryFormPage initiated successfully");
	}

	@Then("I enter empty data into form fields for equiry")
	public void i_enter_empty_data_into_form_fields_for_equiry(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps();
		
		List<String> usernameList = new ArrayList<>();
		List<String> passwordList = new ArrayList<>();
		
		for ( Map<String, String> credential : data ) {
			usernameList.add(credential.getOrDefault("username", ""));
			passwordList.add(credential.getOrDefault("password", ""));
		}
		
		String[] usernames = usernameList.toArray(new String[0]);
		String[] passwords = passwordList.toArray(new String[0]);
		
		loginWeb.setUsername(usernames[0]);
		loginWeb.setPassword(passwords[0]);
		logger.info("Empty username and password entered: " + usernames[0] + ", " + passwords[0]);
	}

	@Then("I visit enquiry form.")
	public void i_visit_enquiry_form() {
	    enquiryFormPage.navigateToEnquiryForm();
	    logger.info("Navigated to enquiry form");
	}

	@Then("I leave the subject field blank.")
	public void i_leave_the_subject_field_blank() {
	    enquiryFormPage.setSubject("");
	    logger.info("Left subject field blank");
	}

	@Then("I click submit button.")
	public void i_click_submit_button() {
	    enquiryFormPage.clickSubmitButton();
	    logger.info("Submit button clicked");
	}

	@Then("Finally Error Message to enter subject should be displayed.")
	public void finally_error_message_to_enter_subject_should_be_displayed() {
		String pageContent = Hooks.driver.getPageSource();
		
		boolean isErrorPresent = pageContent.contains("Please enter subject") || 
		                        pageContent.contains("Subject is required") ||
		                        pageContent.contains("Subject field cannot be empty");
		
		try {
			Assert.assertTrue(isErrorPresent, "Subject validation error should be displayed");
			logger.info("Subject validation error message displayed successfully");
		} catch (AssertionError e) {
			logger.error("Subject validation error was not present");
			ss.takeScreenshot("enquiryform/no_subject_error_present");
			throw e;
		}
	}

	@Then("I enter email field.")
	public void i_enter_email_field(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps();
		
		List<String> emailList = new ArrayList<>();
		
		for ( Map<String, String> emailData : data ) {
			emailList.add(emailData.getOrDefault("ds.id", ""));
		}
		
		String[] emails = emailList.toArray(new String[0]);
		
		enquiryFormPage.setEmail(emails[0]);
		logger.info("Email entered: " + emails[0]);
	}

	@Then("I try submitting the form.")
	public void i_try_submitting_the_form() {
	    enquiryFormPage.clickSubmitButton();
	    logger.info("Form submission attempted");
	}

	@Then("finally error message to ener valid email.")
	public void finally_error_message_to_ener_valid_email() {
		String pageContent = Hooks.driver.getPageSource();
		
		boolean isErrorPresent = pageContent.contains("Please enter valid email") || 
		                        pageContent.contains("Invalid email format") ||
		                        pageContent.contains("Email is not valid");
		
		try {
			Assert.assertTrue(isErrorPresent, "Valid email validation error should be displayed");
			logger.info("Email validation error message displayed successfully");
		} catch (AssertionError e) {
			logger.error("Email validation error was not present");
			ss.takeScreenshot("enquiryform/no_email_validation_error");
			throw e;
		}
	}

	@Then("I enter the username in enquiry form.")
	public void i_enter_the_username_in_enquiry_form(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps();
		
		List<String> usernameList = new ArrayList<>();
		
		for ( Map<String, String> usernameData : data ) {
			usernameList.add(usernameData.getOrDefault("@hds03", ""));
		}
		
		String[] usernames = usernameList.toArray(new String[0]);
		
		enquiryFormPage.setUsername(usernames[0]);
		logger.info("Username entered in enquiry form: " + usernames[0]);
	}

	@Then("I try submitting the enquiry form.")
	public void i_try_submitting_the_enquiry_form() {
	    enquiryFormPage.clickSubmitButton();
	    logger.info("Enquiry form submission attempted");
	}

	@Then("finally error message to enter valid username.")
	public void finally_error_message_to_enter_valid_username() {
		String pageContent = Hooks.driver.getPageSource();
		
		boolean isErrorPresent = pageContent.contains("Please enter valid username") || 
		                        pageContent.contains("Invalid username format") ||
		                        pageContent.contains("Username is not valid");
		
		try {
			Assert.assertTrue(isErrorPresent, "Valid username validation error should be displayed");
			logger.info("Username validation error message displayed successfully");
		} catch (AssertionError e) {
			logger.error("Username validation error was not present");
			ss.takeScreenshot("enquiryform/no_username_validation_error");
			throw e;
		}
	}

	@Then("I enter enquiry message.")
	public void i_enter_enquiry_message(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps();
		
		List<String> messageList = new ArrayList<>();
		
		for ( Map<String, String> messageData : data ) {
			messageList.add(messageData.getOrDefault("No message or Short message", ""));
		}
		
		String[] messages = messageList.toArray(new String[0]);
		
		enquiryFormPage.setMessage(messages[0]);
		logger.info("Enquiry message entered: " + messages[0]);
	}

	@Then("I try submitting enquiry form.")
	public void i_try_submitting_enquiry_form() {
	    enquiryFormPage.clickSubmitButton();
	    logger.info("Enquiry form submission attempted for message validation");
	}

	@Then("finally error message to enter valid enquiry message should get displayed.")
	public void finally_error_message_to_enter_valid_enquiry_message_should_get_displayed() {
		String pageContent = Hooks.driver.getPageSource();
		
		boolean isErrorPresent = pageContent.contains("Please enter valid enquiry message") || 
		                        pageContent.contains("Message is too short") ||
		                        pageContent.contains("Please provide a detailed message") ||
		                        pageContent.contains("Enquiry message is required");
		
		try {
			Assert.assertTrue(isErrorPresent, "Valid enquiry message validation error should be displayed");
			logger.info("Enquiry message validation error displayed successfully");
		} catch (AssertionError e) {
			logger.error("Enquiry message validation error was not present");
			ss.takeScreenshot("enquiryform/no_message_validation_error");
			throw e;
		}
	}

	@Then("I enter the phone number.")
	public void i_enter_the_phone_number(DataTable dataTable) {
		List<Map<String, String>> data = dataTable.asMaps();
		
		List<String> phoneList = new ArrayList<>();
		
		for ( Map<String, String> phoneData : data ) {
			phoneList.add(phoneData.getOrDefault("-22", ""));
		}
		
		String[] phoneNumbers = phoneList.toArray(new String[0]);
		
		enquiryFormPage.setPhoneNumber(phoneNumbers[0]);
		logger.info("Phone number entered: " + phoneNumbers[0]);
	}

	@Then("finally error message to enter valid phone number should appear.")
	public void finally_error_message_to_enter_valid_phone_number_should_appear() {
		String pageContent = Hooks.driver.getPageSource();
		
		boolean isErrorPresent = pageContent.contains("Please enter valid phone number") || 
		                        pageContent.contains("Invalid phone number format") ||
		                        pageContent.contains("Phone number is not valid") ||
		                        pageContent.contains("Please enter a valid mobile number");
		
		try {
			Assert.assertTrue(isErrorPresent, "Valid phone number validation error should be displayed");
			logger.info("Phone number validation error displayed successfully");
		} catch (AssertionError e) {
			logger.error("Phone number validation error was not present");
			ss.takeScreenshot("enquiryform/no_phone_validation_error");
			throw e;
		}
	}
}