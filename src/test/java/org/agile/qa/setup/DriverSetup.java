package org.agile.qa.setup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSetup {
	private static WebDriver driver;
	public static ConfigFileReader filereader = new ConfigFileReader();
	
	public static WebDriver getDriver() {
		if ( driver == null ) {
			System.setProperty(filereader.getDriverName(), filereader.getDriverPath());
			driver = new FirefoxDriver();
		}
		return driver;
	}
}
