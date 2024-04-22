package usdCurrenciesAPI.stepdefs;

import cucumber.api.Scenario;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import usd.rates.utils.BaseClass;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.simple.JSONObject;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import static org.junit.Assert.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.Map;

import static org.junit.Assert.*;
/*
 * Hamcrest Matcher Methods
 * equalTo, containsString, hasItem. hasSize etc
 * full list:
 * http://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html
 */
public class StepDefs_GetUsd extends BaseClass {

	/*
	private RequestSpecification _REQUEST_SPEC;
	private Response _RESP;
	String unique_cat_name;
	String unique_cat_id;
		Scenario scn;
	*/


	/*
	ContextDI _CNTXT;

	public StepDefs_GetRequest(ContextDI cntxt) {
		this._CNTXT = cntxt;
	}
	 */

	//Hooks
	
	@Before
	public void beforeHook(Scenario s) {
		this.scn = s;
	}

	@After
	public void afterHook(Scenario s){
		this.scn = s;
		if (_RESP==null) {
			scn.write("Response: No response received.");
		}else {
			scn.write("Response: " + _RESP.asString());
		}
	}


	//*********************************************************
	//*********************GIVEN*******************************
	//*********************************************************
	@Given("Get USD API is up and running")
	public void get_USD_API_is_up_and_running() {
		_REQUEST_SPEC = given().baseUri("https://open.er-api.com");
		scn.write("Base URI = https://open.er-api.com");

	}


	//*********************************************************
	//*******************WHEN**********************************
	//*********************************************************
	@When("I hit url for USD Currencies")
	public void hit_usdcurrencies_check_url() {
		_RESP = _REQUEST_SPEC.when().get("/v6/latest/USD");
		scn.write("Currencies URL = /v6/latest/USD");


	}
	
	@When("I hit url for USD Currencies with incorrect method")
	public void hit_usdcurrencies_check_url_with_wrongMethod() {
		_RESP = _REQUEST_SPEC.when().post("/v6/latest/USD");
		scn.write("Currencies URL = /v6/latest/USD");


	}
	
	

	//**************************************************************
	//*********************THEN*************************************
	//**************************************************************
	@Then("API returns the response with status code as {int}")
	public void api_returns_the_response_with_status_code_as(Integer int1) {
		_RESP.then().assertThat().statusCode(int1);
		scn.write("Status code appearing as: " + int1);

	}
	
	@Then("API returns the result as {string}")
	public void api_returns_the_result_as(String status) {
		_RESP.then().assertThat().body("result", equalTo(status));
		scn.write("Response status code is: " + scn.getName());
	}
	
	
	@Then("Response returns the error-type as {string}")
	public void response_with_wrong_requestMethod(String errorType) {
		_RESP.then().assertThat().body("error-type", equalTo(errorType));
		scn.write("Incorrect method is: " + scn.getName());
	}

	
	@Then("USD price against AED is within the range of {double} to {double}")
	public void verify_usd_price_range(double min, double max) {
	    float usdToAedRate = _RESP.jsonPath().getFloat("rates.AED");
	    assertThat((double) usdToAedRate, closeTo(min, 0.1)); 
	    assertThat((double) usdToAedRate, closeTo(max, 0.1)); 
	    scn.write("USD price against AED is within the specified range.");
	}
	

	@Then("API returns {int} currency pairs")
	public void verify_currency_pairs_count(int expectedCount) {
	    Map<String, Float> rates = _RESP.jsonPath().getMap("rates");
	    int actualCount = rates.size();
	    assertEquals(expectedCount, actualCount); 
	    scn.write("API returned " + actualCount + " currency pairs as expected.");
	}
	

	@Then("API response matches the JSON schema")
	public void verify_json_schema() {
	    String jsonSchemaPath = "getUsdApiRes.json"; 
	    _RESP.then().assertThat().body(matchesJsonSchemaInClasspath(jsonSchemaPath)); 
	    scn.write("API response matches the JSON schema: "+jsonSchemaPath);
	    //scn.write()
	}

	
	 @Then("all currency prices should be valid")
	    public void validateCurrencyPrices() {
	        assertNotNull("Response is null", _RESP);

	        Map<String, Double> rates = _RESP.jsonPath().getMap("rates");
	        assertNotNull("Rates map is null", rates);
	        for (Map.Entry<String, Double> entry : rates.entrySet()) {
	            String currency = entry.getKey();
	            Object priceObj = entry.getValue();
	            assertNotNull(currency + " price is null", priceObj);
	            // Convert to Double if it's an Integer or Float
	            Double price;
	            if (priceObj instanceof Integer) {
	                price = ((Integer) priceObj).doubleValue();
	            } else if (priceObj instanceof Float) {
	                price = ((Float) priceObj).doubleValue();
	            } else {
	                price = (Double) priceObj;
	            }


	            assertTrue(currency + " price is not numeric", isNumeric(price));
	            assertFalse(currency + " price is negative", price <  0);
	            assertFalse(currency + " price is boolean", price == null || price.getClass().isPrimitive());
	            assertNotEquals(currency + " price is 0", 0.0, price, 0.0);
	            assertNotNull(currency + " price is empty", price);
	        }
	 }

	    private boolean isNumeric(Double value) {
	        if (value == null) {
	            return false;
	        }
	        try {
	            Double.parseDouble(String.valueOf(value));
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	 

	
	//*************************UTILS**************************
	//To get random Key
	public String GetRandomString(int n) {
		// lower limit for LowerCase Letters 
		int lowerLimit = 97; 

		// lower limit for LowerCase Letters 
		int upperLimit = 122; 

		Random random = new Random(); 

		// Create a StringBuffer to store the result 
		StringBuffer r = new StringBuffer(n); 

		for (int i = 0; i < n; i++) { 

			// take a random value between 97 and 122 
			int nextRandomChar = lowerLimit 
					+ (int)(random.nextFloat() 
							* (upperLimit - lowerLimit + 1)); 

			// append a character at the end of bs 
			r.append((char)nextRandomChar); 
		} 

		// return the resultant string 
		return r.toString(); 
	} 


}
