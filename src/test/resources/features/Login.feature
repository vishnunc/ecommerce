Feature: As a user I should be able to login to application
	
	Scenario: User should be able to login successfully
		Given I am on the login page
		When I enter my <username> and <password>
		Then I