Feature: Flight Ticket Booking check.

	Background: 
		Given that I navigate to login page.
		And succesfully login with valid credentials.
		|username|password|
		|flightadmin|flightadmin|
		
	Scenario: Validate Date in Flight booking.
		Then enter invalid date.
		And the warning message must be diplayed.
		
	Scenario: Validating calculation result while data is invalid.
		Then enter invalid details.
		And expect the total to display 'NaN'
		
	Scenario: Validate log out button.
		Then locate log out button.
		And click on log out button and validate it got logged out.
		
	Scenario: Book Now button validation
		Then enter valid details.
		And Click the Book Now button.
		