# Banks payments transfer
This project is RESTful web service for payments processing.
Client is able to create a payment in USD or EUR curriencies with possibility to cancel it with small cancelation fee in current day.
There are three types of payments client can choose from. Cancelation fee is calculated by payment type rate.
All input data is validated.
All methods are covered by unit tests.  
Client IP and location are stored in database. But it's not used in business logic. 

## Running application
H2 in-memory database is used in this project. Data compilated from data.sql. 
### Compile and run application:
<i>createPayment method</i>:
http://localhost:8080/payments/create?type=TYPE1&currency=EUR
![project](https://user-images.githubusercontent.com/56863735/96388677-1ab7bf80-11b3-11eb-98e1-68b88c0dbc8b.png)
### Used dependencies:
- spring-boot-starter-data-jpa
- spring-boot-starter-hateoas
- spring-boot-starter-web
- spring-boot-starter-validation
- spring-boot-devtools
- h2database
- spring-boot-starter-test