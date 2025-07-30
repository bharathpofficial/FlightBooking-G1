package org.agile.qa.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

import org.testng.annotations.Test;

import io.cucumber.core.cli.Main;

@Test
@CucumberOptions(
		features = "src/test/java/org/agile/qa/features",
		glue = {"org.agile.qa.stepDefinitions", "org.agile.qa.hooks"},
		plugin = {"pretty", "junit:target/cucumber-reports/Cucumber.xml"}
//		tags = "not @adamya and not @adamyaBooking and not @adamyalogin and not @enquiry"
	)

public class RunnerTests extends AbstractTestNGCucumberTests{
	
}