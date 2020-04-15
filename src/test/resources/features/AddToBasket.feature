Feature: As a user I should be select a product and Add it to the Basket

Scenario: Add To Basket when not logged in
	Given I am on the product home page
	When I select a <product>
	Then I should see the <product> detail page
	When I add product to Basket
	Then I should see the login page


Scenario: Add To Basket when logged in
	Given I am logged in
	And I am on the product home page
	When I select a <product>
	Then I should see the <product> detail page
	When I add product to Basket
	Then <product> should be added to my basket