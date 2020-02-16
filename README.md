#Weather Rest Api

##Introduction
Service design for a weatherStackWeather system that uses external service(weatherstack) for gathering current weatherStackWeather parameters at different locations and saving then on internal repository for future uses.

##Technology used in project
- Spring Boot
- H2 Database Engine
- Swagger UI
- Maven

<<<<<<< HEAD
##Api documentation at https://weather-rest-api.herokuapp.com/swagger-ui.html
=======
##How too work with it
Service is available at https://weatherStackWeather-rest-api.herokuapp.com/
>>>>>>> develop

##Future Tasks
- Add tests
- Create internal Database for service uses
- Build android application for regular user
- build android application for admin

##Tasks
###Task 1 - Creating a base for our data
- weatherStackLocation
- weatherStackWeather

###Task 2
####Part 1 
Adding CRUD methods and validation for locations.
####Part 2
Implementing mechanism for unique locations. There can by only one weatherStackLocation by same name and cordinates in database. If weatherStackLocation already exist throw error.
####Part 3
Implementing mechanism for searching by id, coordinates, place name, region and country.
####Part 4
Implementing sorting mechanism by name.

###Task3:
####Part 1
Implementing CRUD methods and validation for weathers.
####Part 2
Adding DTO Objects for Location and Weather and change whole project for using them.
####Part 3
Implementing mechanism for searching by id and date.
####Part 4
Implementing sorting mechanism by date.
####Task 5
Implementing service for external api to gather data for locations and weathers.
###Task4 - Tests
####Part 1
Repository tests implementation.
