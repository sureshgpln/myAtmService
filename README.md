# myAtmService
myAtmService

This is a RestFul web service to simulate the cash operations in an ATM

Endpoints.
* Initialize - To initialize or add currencies into the ATM
* withdraw - To withdraw cash amount from ATM
* balance - To check current balance currency and amount 

Technology used:
* Maven
* Spring boot
* H2
* Mockito


How to run
* Clone repo from https://github.com/sureshgpln/myAtmService.git 
* Build using Maven
* Run the application class as springboot application
* embedded tomcat should start on port 8080

Testing: 
You can test using Postman or browser
The success response will look like
```
{
    "responseCode": "0",
    "responseDesc": "SUCCESS",
    "responseStatus": "SUCCESS",
    "responseBody": [
        {
            "type": "ONE_HUNDRED",
            "value": 100,
            "amount": 1
        },
        {
            "type": "FIFTY",
            "value": 50,
            "amount": 1
        }
    ]
}