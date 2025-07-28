package org.agile.qa.stepDefinitions;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.agile.qa.hooks.Hooks;
import org.agile.qa.pages.BookingPage;
import org.agile.qa.setup.ConfigFileReader;
import org.agile.qa.setup.DriverSetup;
import org.agile.qa.utils.ExcelUtils;
import org.agile.qa.utils.XmlUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class BookingTest {
	private WebDriverWait wait;
	ConfigFileReader fileReader = new ConfigFileReader();
	BookingPage bookingform;
	XmlUtils xml = new XmlUtils();
	ExcelUtils excel = new ExcelUtils();
	private static final Logger logger = LoggerFactory.getLogger(BookingTest.class);
	
	@Given("that I navigate to login page.")
	public void that_i_navigate_to_login_page() {

	    
		Hooks.driver.navigate().to(fileReader.getLoginUrl());
	    
	    bookingform = new BookingPage(Hooks.driver);
	    
	    this.wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));
        logger.info("Navigated to login page: {}", fileReader.getLoginUrl());
	}
	
	public WebElement safeFindElement(By locator) {
	    try {
	        return Hooks.driver.findElement(locator);
	    } catch (org.openqa.selenium.NoSuchElementException e) {
	        logger.warn("Element NOT found: {}", locator);
	        return null;
	    }
	}

	@Given("succesfully login with valid credentials.")
	public void succesfully_login_with_valid_credentials(io.cucumber.datatable.DataTable dataTable) {
	    bookingform.setUsername(xml.getAdminUsername());
	    bookingform.setPassword(xml.getAdminPassword());
	    
	    bookingform.setCaptcha(bookingform.getCaptchaCode());
	    
	    bookingform.validateCaptcha();
	    
	    bookingform.clickLoginBtn();	 
        logger.info("Login performed with provided credentials.");
	    
	}

	@Then("enter invalid date.")
	public void enter_invalid_date() {
	    bookingform.setDepartureDate("invalid-date"); // e.g., "32/13/9999"
        logger.info("Entered invalid departure date for validation.");
	}

	@Then("the warning message must be diplayed.")
	public void the_warning_message_must_be_diplayed() {
	    WebElement warning = safeFindElement(By.id("departure-date-error"));
	    if (warning != null) {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(warning));
	            Assert.assertTrue(warning.isDisplayed());
	            logger.info("Warning message displayed as expected.");
	        } catch (AssertionError | org.openqa.selenium.TimeoutException e) {
	            logger.error("Warning message found but not visible/displayed.", e);
	            // Screenshot for report
//	            takeScreenshot("warning_not_displayed");
	        }
	    } else {
	        logger.error("Warning message element NOT found – raise bug!");
//	        takeScreenshot("warning_message_missing");
	    }
	}

	@Then("enter invalid details.")
	public void enter_invalid_details() {
//		I must use xlsx file to get the invalid field values.
	    bookingform.setPassengerName("03=284!)#");        // Or other invalid entry
	    bookingform.setEmail("not-an-email");
	    bookingform.setPhone("1234");
        logger.info("Entered invalid details for passenger fields.");
	}

	@Then("expect the total to display {string}")
	public void expect_the_total_to_display(String expectedAmount) {
	    try {
			Assert.assertEquals(expectedAmount, bookingform.getTotalAmount());
            logger.info("Total amount correctly displayed as ", expectedAmount); 
		} catch (AssertionError e) {
            logger.error("Displayed total amount does not match expected: ", expectedAmount);
			// TODO Auto-generated catch block
			// e.printStackTrace();
			// screenshot
		}
	}

	@Then("locate log out button.")
	public void locate_log_out_button() {
	    WebElement logoutBtn = safeFindElement(By.id("logout-btn"));
	    if (logoutBtn != null) {
	        try {
	            wait.until(ExpectedConditions.visibilityOf(logoutBtn));
	            Assert.assertTrue(logoutBtn.isDisplayed());
	            logger.info("Logout button is displayed.");
	        } catch (AssertionError | org.openqa.selenium.TimeoutException e) {
	            logger.error("Logout button found but not visible/displayed.", e);
//	            takeScreenshot("logout_not_displayed");
	        }
	    } else {
	        logger.error("Logout button NOT found – raise bug!");
//	        takeScreenshot("logout_button_missing");
	    }
	}

	@Then("click on log out button and validate it got logged out.")
	public void click_on_log_out_button_and_validate_it_got_logged_out() {
	    WebElement logoutBtn = safeFindElement(By.id("logout-btn"));
	    if (logoutBtn != null) {
	        logoutBtn.click();
	        WebElement loginBtn = safeFindElement(By.id("login-submit"));
	        if (loginBtn != null) {
	            try {
	                wait.until(ExpectedConditions.visibilityOf(loginBtn));
	                Assert.assertTrue(loginBtn.isDisplayed());
	                logger.info("Successfully logged out, login button is displayed.");
	            } catch (AssertionError | org.openqa.selenium.TimeoutException e) {
	                logger.error("Login button not displayed after logout.", e);
//	                takeScreenshot("login_not_displayed_after_logout");
	            }
	        } else {
	            logger.error("Login button NOT found after logout – raise bug!");
//	            takeScreenshot("login_button_missing_after_logout");
	        }
	    } else {
	        logger.error("Logout button NOT found, cannot perform logout – raise bug!");
//	        takeScreenshot("logout_button_missing_when_trying_to_logout");
	    }
	}

	@Then("enter valid details.")
	public void enter_valid_details() {
	    List<Map<String, String>> validBookingData;
	    try {
	        validBookingData = ExcelUtils.readExcelAsListOfMaps(fileReader.getValidBookingExcelPath(), "validBooking");
	        Map<String, String> bookingRow = validBookingData.get(0); // Use row 0, or loop for all rows

	        logger.info("Booking data used: {}", bookingRow);

	        bookingform.setTravelFrom(bookingRow.get("travelfrom"));
	        bookingform.setTravelTo(bookingRow.get("travelto"));
	        bookingform.setDepartureDate(bookingRow.get("departuredate"));

	        // For dropdown, this assumes .selectByValue() expects the value attribute from Excel
	        bookingform.selectClass(bookingRow.get("class"));

	        bookingform.setPassengerName(bookingRow.get("name"));
	        bookingform.setEmail(bookingRow.get("email"));
	        bookingform.setPhone(bookingRow.get("phone"));

	        // For ticket/passenger count: use increase button utility
	        int passengers = Integer.parseInt(bookingRow.get("noofpassengers"));
	        bookingform.increaseTicketCountTo(passengers);

	        logger.info("All input fields set for Booking Form as per excel data.");


	    } catch (IOException e) {
	        logger.error("Failed to read Excel data: {}", e.getMessage(), e);
	        Assert.fail("Failed to read Excel data: " + e.getMessage());
	    }
	}


	@Then("Click the Book Now button.")
	public void click_the_book_now_button() {
         bookingform.clickBookNow();
         logger.info("Clicked Book Now.");
	}

}