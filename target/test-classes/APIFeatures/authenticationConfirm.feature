Feature: Verify proper authentication functionality for Baze application

  @apitest
  Scenario: Authentication functionality check with valid access token 
  	
  	Given the Authentication end point is available
    And I generate the Authentication token respose
    When I use the access token to login the User end point   
    Then I should get success response
    And I should be able to verify the respose payload
	