Project is for USD currencies Assessment given to me

Editor required:
Eclipse version 23-24

PRogramming language :
Java

Project is created with : Maven 

It has Feature file and a Step Definition File

Feature : C:\Users\Home\git\repository\CucumberAPIAutomation\src\test\resources\features\USDCurrencies\GetScenario.feature
StepDefinition: C:\Users\Home\git\repository\CucumberAPIAutomation\src\test\java\usdCurrenciesAPI\stepdefs\StepDefs_GetUSD.java


BaseClass : C:\Users\Home\git\repository\CucumberAPIAutomation\src\test\java\usd\rates\utils\BaseClass.java

Feature File has below scenarios covered:
Scenario: Check the status code and status retuned by the API response[case: status=SUCCESS].
Scenario: Check the status code and status retuned by the API response[case: status=error].
Scenario: Verify USD price against AED within specified range
Scenario: Verify API response matches JSON schema
Scenario: Verify the number of currency pairs returned by the API
Scenario: Check valid currency prices

You may run the code by executing the runner file below:

runner file: C:\Users\Home\git\repository\CucumberAPIAutomation\src\test\java\runner\CucumberTest.java

You will need to add all depedencies in the POM.xml file
C:\Users\Home\git\repository\CucumberAPIAutomation\pom.xml

