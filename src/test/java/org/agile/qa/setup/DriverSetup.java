package org.agile.qa.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSetup {
    public static ConfigFileReader filereader = new ConfigFileReader();

    public static WebDriver getDriver() {
        System.setProperty(filereader.getDriverName(), filereader.getDriverPath());
        return new FirefoxDriver(); // always a new instance!
    }
}
