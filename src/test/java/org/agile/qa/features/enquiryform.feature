@enquiry
Feature: Enquiry Form check.

	Background:
		Given navigate to the application login URL to enquiry

	Scenario: Enquiry Form Subject Verification
		Then I enter empty data into form fields for equiry
		|username|password|
		|||
		Then I visit enquiry form.
		Then I leave the subject field blank.
		Then I click submit button. 
		And Finally Error Message to enter subject should be displayed.
		
	Scenario: Valid Enquiry Email
        Then I enter email field.
        |ds.id|
		Then I try submitting the form. 
		And finally error message to ener valid email.
		
	Scenario: Verify Enquiry Form Username
		Then I enter the username in enquiry form.
        |@hds03|
		Then I try submitting the enquiry form.
		And finally error message to enter valid username.
		
	Scenario: Verify Enquiry Message
		Then I enter enquiry message.
        |No message or Short message|
		Then I try submitting enquiry form.
        And finally error message to enter valid enquiry message should get displayed.


    Scenario: Enquiry Form Phone Number Verification
        Then I enter the phone number. 
        |-22|
        Then I try submitting enquiry form.
        And finally error message to enter valid phone number should appear. 
        