package org.agile.qa.setup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
	
	public Properties properties;
	
	public final String propertyFilePath = "src/test/java/org/agile/qa/resources/data.properties";//Change the file path when you are working in local machine

	public ConfigFileReader() {

		properties = new Properties();
		try {
			properties.load(new FileReader(propertyFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getBrowserName() {

		return properties.getProperty("browser");
	}	 	  	  		 	     	     	      	 	

	public String getDriverName() {

		return properties.getProperty("drivername");
	}

	public String getDriverPath() {

		return properties.getProperty("driverpath");
	}

	public String getLoginUrl() {
		return properties.getProperty("loginUrl");
	}

	public String getBookingUrl() {
		return properties.getProperty("bookingUrl");
	}
	
	public String getValidLoginXMLPath() {
		return properties.getProperty("validLoginXML");
	}
}
