Feature: NaturesWay Cart Verify

	@uitest
  Scenario: Verify cart items against the add to cart action
  
    Given I am at the natures way home page
    And I click on the 'VITAMINS & MINERALS' shop option
    When I perform add to cart action for the products with specific quantity
      | productname           | quantity |
      | 5-HTP                 |        2 |
      | B-50 Complex          |        4 |
      | Brain Builder Gummies |        3 |
    And I navigate to the cart
    Then the cart should contain the correct products
    And the product quantities should be correct
    And the subtotal should be displayed correctly
