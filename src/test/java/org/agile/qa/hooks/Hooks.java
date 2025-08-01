package org.agile.qa.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.agile.qa.setup.DriverSetup;
import org.openqa.selenium.WebDriver;

public class Hooks {
    public static WebDriver driver;
    
    @Before
    public void setUp() {
        driver = DriverSetup.getDriver();
        System.out.println("WebDriver instantiated: " + driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("WebDriver quit");
            driver = null;
        }
    }

}
