@booking
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
		
	@adamyaBooking
	Scenario: Submit form with empty fields
    Then I leave all booking form fields empty
    Then Click the Book Now button.
    And I should see an error message saying "Can't leave input fields Empty"
	
	@adamyaBooking
  Scenario: Submit form with valid inputs
    Then I enter valid details in all booking form fields
        | travel_from   | travel_to | departure_date | class  | passenger_name     | email              | phone       | passengers |
        | delhi         | dubai     | 04/07/2025      | Luxury Class | Adamya Kr Pandey   | akpandey@gmail.com | 9876543210  | 1          |
    Then Click the Book Now button.
    And I should see a confirmation message saying "Your flight reservation is confirmed"
