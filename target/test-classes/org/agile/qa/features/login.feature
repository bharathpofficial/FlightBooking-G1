@login
Feature: Login Funtionality check.

	Background:
		Given navigate to the application login URL

	Scenario: Remember Me validation
		Then I enter empty data into form fields
		|username|password|
		|||
		Then I leave the captcha field blank.
		Then I click on Remember Me checkbox.
		Then I "accept" the popup window 
		And Finally Remember me error expected.
		
	Scenario: Captcha Validation
		Then I enter valid data into form fields.
		Then I try copying the captcha field.
		And the captcha field should not get copyied.
		
	Scenario: Password Reset
		Then I click on the password reset form.
		And I get to see the password reset form.
		Then enter the email id.
		Then enter the 'OTP' received for that mail.
		And finally set new password and submit.
		
	Scenario: Unauthenticated users should not access the booking page.
		When directly access the booking page URL
		Then the user should be redirected to the login page
		And the booking page should not be accessible 

	Scenario: Login with invalid password
        Then I enter valid username "flightadmin" and invalid password "wrongpass"
        Then I click on the Login button
        And I should see an error message saying "Please enter valid password"