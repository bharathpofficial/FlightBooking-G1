package org.agile.qa.stepDefinitions;

import org.agile.qa.hooks.Hooks;
import org.agile.qa.pages.FlightSearchPage;
import org.agile.qa.setup.ConfigFileReader;
import org.agile.qa.setup.DriverSetup;
import org.agile.qa.utils.XmlUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class FlightSearchTest {
    private FlightSearchPage searchPage;
    private ConfigFileReader config = new ConfigFileReader();
    private XmlUtils xml = new XmlUtils();
    private static final Logger logger = LoggerFactory.getLogger(FlightSearchTest.class);

    @Given("I am logged in with valid credentials")
    public void i_am_logged_in_with_valid_credentials() {
        Hooks.driver.navigate().to(config.getLoginUrl());
        searchPage = new FlightSearchPage(Hooks.driver);

        searchPage.setUsername(xml.getAdminUsername());
        searchPage.setPassword(xml.getAdminPassword());
        searchPage.setCaptcha(searchPage.getCaptchaCode());
        searchPage.validateCaptcha();
        searchPage.clickLoginBtn();
        logger.info("User logged in successfully.");
    }

    @Given("I navigate to the flight search page")
    public void i_navigate_to_the_flight_search_page() {
        Hooks.driver.navigate().to(config.getFlightSearchUrl());
        logger.info("Navigated to flight search page: {}", config.getFlightSearchUrl());
    }

    @Then("I enter a valid flight number in the \"Search by Flight Number\" field")
    public void i_enter_valid_flight_number() {
        searchPage.enterFlightNumber("AI202");
        logger.info("Entered valid flight number: AI202");
    }

    @Then("I click the \"Search by Flight Number\" button")
    public void i_click_search_by_flight_number_button() {
        searchPage.clickSearchByFlightNumber();
        logger.info("Clicked search by flight number button.");
    }

    @Then("I should see the flight details corresponding to that flight number")
    public void i_should_see_details_by_number() {
        WebElement details = safeFindElement(By.id("result-flight-number"));
        Assert.assertNotNull(details);
        Assert.assertTrue(details.isDisplayed());
        logger.info("Flight details are displayed for flight number.");
    }

    @Then("I enter a valid flight name in the \"Search by Flight Name\" field")
    public void i_enter_valid_flight_name() {
        searchPage.enterFlightName("AirIndiaExpress");
        logger.info("Entered valid flight name: AirIndiaExpress");
    }

    @Then("I click the \"Search by Flight Name\" button")
    public void i_click_search_by_flight_name_button() {
        searchPage.clickSearchByFlightName();
        logger.info("Clicked search by flight name button.");
    }

    @Then("I should see the flight details corresponding to that flight name")
    public void i_should_see_details_by_name() {
        WebElement details = safeFindElement(By.id("result-flight-name"));
        Assert.assertNotNull(details);
        Assert.assertTrue(details.isDisplayed());
        logger.info("Flight details are displayed for flight name.");
    }

    @Then("I enter a valid flight type in the \"Search by Flight Type\" field")
    public void i_enter_valid_flight_type() {
        searchPage.enterFlightType("Luxury");
        logger.info("Entered valid flight type: Luxury");
    }

    @Then("I click the \"Search by Flight Type\" button")
    public void i_click_search_by_flight_type_button() {
        searchPage.clickSearchByFlightType();
        logger.info("Clicked search by flight type button.");
    }

    @Then("I should see the flight details of all flights matching that type")
    public void i_should_see_details_by_type() {
        WebElement details = safeFindElement(By.id("result-flight-type"));
        Assert.assertNotNull(details);
        Assert.assertTrue(details.isDisplayed());
        logger.info("Flight details are displayed for flight type.");
    }

    public WebElement safeFindElement(By locator) {
        try {
            return Hooks.driver.findElement(locator);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            logger.warn("Element not found: {}", locator);
            return null;
        }
    }
}