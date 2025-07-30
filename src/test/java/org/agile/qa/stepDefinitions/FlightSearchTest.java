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
        searchPage.enterFlightNumber("AC789");  // Using actual flight number from the HTML
        logger.info("Entered valid flight number: AC789");
    }

    @Then("I click the \"Search by Flight Number\" button")
    public void i_click_search_by_flight_number_button() {
        searchPage.clickSearchByFlightNumber();
        logger.info("Clicked search by flight number button.");
        // Wait a moment for search results to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("I should see the flight details corresponding to that flight number")
    public void i_should_see_details_by_number() {
        // Check if the flight table is displayed
        if (searchPage.isFlightTableDisplayed()) {
            logger.info("Flight table is displayed after flight number search.");
            
            // Verify that the table contains flight data
            WebElement table = Hooks.driver.findElement(By.id("myTable"));
            Assert.assertTrue(table.isDisplayed(), "Flight table should be visible");
            
            // Check if there are any visible flight rows (the search should show/hide rows)
            java.util.List<WebElement> visibleRows = Hooks.driver.findElements(By.xpath("//table[@id='myTable']//tbody//tr[not(contains(@style,'display: none'))]"));
            Assert.assertTrue(visibleRows.size() > 0, "At least one flight should be visible after search");
            
            logger.info("Flight search by number completed successfully. Found {} visible flights.", visibleRows.size());
        } else {
            Assert.fail("Flight table not displayed after search by flight number");
        }
    } 



    @Then("I enter a valid flight name in the \"Search by Flight Name\" field")
    public void i_enter_valid_flight_name() {
        searchPage.enterFlightName("SkyRider Express");  // Using actual flight name from the HTML
        logger.info("Entered valid flight name: SkyRider Express");
    }

    @Then("I click the \"Search by Flight Name\" button")
    public void i_click_search_by_flight_name_button() {
        searchPage.clickSearchByFlightName();
        logger.info("Clicked search by flight name button.");
        // Wait a moment for search results to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("I should see the flight details corresponding to that flight name")
    public void i_should_see_details_by_name() {
        // Check if the flight table is displayed
        if (searchPage.isFlightTableDisplayed()) {
            logger.info("Flight table is displayed after flight name search.");
            
            // Verify that the table contains flight data
            WebElement table = Hooks.driver.findElement(By.id("myTable"));
            Assert.assertTrue(table.isDisplayed(), "Flight table should be visible");
            
            // Check if there are any visible flight rows (the search should show/hide rows)
            java.util.List<WebElement> visibleRows = Hooks.driver.findElements(By.xpath("//table[@id='myTable']//tbody//tr[not(contains(@style,'display: none'))]"));
            Assert.assertTrue(visibleRows.size() > 0, "At least one flight should be visible after search");
            
            logger.info("Flight search by name completed successfully. Found {} visible flights.", visibleRows.size());
        } else {
            Assert.fail("Flight table not displayed after search by flight name");
        }
    }

    @Then("I enter a valid flight type in the \"Search by Flight Type\" field")
    public void i_enter_valid_flight_type() {
        searchPage.enterFlightType("Direct Flight");  // Using actual flight type from the HTML
        logger.info("Entered valid flight type: Direct Flight");
    }

    @Then("I click the \"Search by Flight Type\" button")
    public void i_click_search_by_flight_type_button() {
        searchPage.clickSearchByFlightType();
        logger.info("Clicked search by flight type button.");
        // Wait a moment for search results to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("I should see the flight details of all flights matching that type")
    public void i_should_see_details_by_type() {
        // Check if the flight table is displayed
        if (searchPage.isFlightTableDisplayed()) {
            logger.info("Flight table is displayed after flight type search.");
            
            // Verify that the table contains flight data
            WebElement table = Hooks.driver.findElement(By.id("myTable"));
            Assert.assertTrue(table.isDisplayed(), "Flight table should be visible");
            
            // Check if there are any visible flight rows (the search should show/hide rows)
            java.util.List<WebElement> visibleRows = Hooks.driver.findElements(By.xpath("//table[@id='myTable']//tbody//tr[not(contains(@style,'display: none'))]"));
            Assert.assertTrue(visibleRows.size() > 0, "At least one flight should be visible after search");
            
            logger.info("Flight search by type completed successfully. Found {} visible flights.", visibleRows.size());
        } else {
            Assert.fail("Flight table not displayed after search by flight type");
        }
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