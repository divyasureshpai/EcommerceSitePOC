Feature: Resest password for Baze application

@apitest
Scenario: User try to reset the password

Given I place the user password reset request
When I read the passowrd reset mail
And get the token from the passowrd reset end point in the mail
Then the password should be updated on sending the request with new passowrd the the token
