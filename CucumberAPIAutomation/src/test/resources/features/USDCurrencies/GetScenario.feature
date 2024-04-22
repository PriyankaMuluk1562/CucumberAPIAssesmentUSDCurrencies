Feature: USD rates against multiple currency
  This feature is to validate https://open.er-api.com/v6/latest/USD API

  
  Scenario: Check the status code and status retuned by the API response[case: status=SUCCESS].
    Given Get USD API is up and running
    When I hit url for USD Currencies
    Then API returns the response with status code as 200 
    And API returns the result as "success"
    
    
  Scenario: Check the status code and status retuned by the API response[case: status=error].
    Given Get USD API is up and running
    When I hit url for USD Currencies with incorrect method
    Then API returns the response with status code as 405 
    And API returns the result as "error"
    And Response returns the error-type as "wrong-http-method"


  
  Scenario: Verify USD price against AED within specified range
    Given Get USD API is up and running
    When I hit url for USD Currencies
    Then API returns the response with status code as 200 
    And API returns the result as "success"
    And USD price against AED is within the range of 3.6 to 3.7
    
    
    Scenario: Verify the number of currency pairs returned by the API
Given Get USD API is up and running
When I hit url for USD Currencies
Then API returns the response with status code as 200
And API returns the result as "success"
And API returns 162 currency pairs


	Scenario: Verify API response matches JSON schema
	  Given Get USD API is up and running
	  When I hit url for USD Currencies
	  Then API returns the response with status code as 200
	  And API response matches the JSON schema
	  
	  
	Scenario: Check validity of currency prices
	  Given Get USD API is up and running
	  When I hit url for USD Currencies
	  Then API returns the response with status code as 200
	  And all currency prices should be valid

