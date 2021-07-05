# REST-Application-Manager
Rest App to manage applications

## General info
The project is used to manage applications(requests). The basic functions of the application are:
* Creating applications
* Searching for applications using the filter
* Changing the status of applications
* Rejecting applications
* Deleting applications

## End-points
* Get all applications: [GET] http://localhost:8080/api/apps?page_size=&page_number=&state=&name=
* Get one application by id: [GET] http://localhost:8080/api/apps/1
* Get application by application number (status history): http://localhost:8080/api/apps/history?appNumber=&archived=&page_number=&page_size=
* Add application: [POST] http://localhost:8080/api/apps/add - `body: {
        "name": "name",
        "content": "content"
      }`
* Delete application: [DELETE] http://localhost:8080/api/apps/1 - `body: {
        "rejectReason": "reason"
      }`
* Reject application: [PUT] http://localhost:8080/api/apps/reject/1 - `body: {
        "rejectReason": "reason"
      }`
* Change application state: [PUT] http://localhost:8080/api/apps/change/9

## Technologies
Project is created with:
* Java 1.8
* Hibernate ORM 5.5.3
* Hibernate Validator 6.0.13
* H2 Database Engine 1.4.200
* Spring Boot
* FasterXML/Jackson 2.9.10
* REST API
	
## Database
The project uses the H2 database built into Spring Boot. The configuration of the database is included in the project. No additional configuration is required

Access to the database
* Console: http://localhost:8080/h2-console
* Driver Class: org.h2.Driver
* JDBC URL: jdbc:h2:mem:testdb
* User Name: jw
* Password: haslo

## Setup
To run this project through Intellij IDEA
