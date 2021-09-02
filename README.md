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
* Build using Maven “mvn clean install”
* Run the application “mvn spring-boot:run”
* Embedded tomcat should start on port 8080

Testing: 
You can test using Postman or browser
The success response will look like
```
{
    "responseDesc": "SUCCESS",
    "responseStatus": "SUCCESS",
    "responseBody": [
            {
                "currency_type": "THOUSAND",
                "currency_count": 20
            },
            {
                "currency_type": "FIVE_HUNDRED",
                "currency_count": 20
            },
            {
                "currency_type": "ONE_HUNDRED",
                "currency_count": 20
            },
            {
                "currency_type": "FIFTY",
                "currency_count": 5
            },
            {
                "currency_type": "TWENTY",
                "currency_count": 3
            }
        ]
}
```

The Failure response will look like
```
{
    "responseDesc": "Funds not available for the amount requested, please try later",
    "responseStatus": "FAIL",
    "responseBody": []
}
```
