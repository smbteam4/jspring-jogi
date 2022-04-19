# Robot Apocalypse - Survivor Information System

## Overview
This system is used to manage Survivor Information. It is built with Java Spring Boot and MongoDB as it's database.

## Key Application Features
This application provides following functionalities:
1. Able to add Survivor or a list of Survivors with following data
      -  Name
      - Age
      - Gender
      - Location
      - ResourceInventory
2. Update survivor location 
3. Update ResourceInventory
4. Flag survivor as infected
5. Able to fetch details of all the Survivor  
6. Percentage of infected survivors
7. Percentage of non-infected survivors
8. List details of infected survivors 
9. List details of non-infected survivors 
10. List details of all robots

## Database Setup

1. Run docker command docker-compose up.
2. This will start mongodb and mongo-express and listen to port 27017 and 8081 respectively.
3. Verify that the DB server is running using mongo-express [mongo-express](http://localhost:8081).
4. Create the database `ApocalypseSurvivors` using client (only for the first time).

## Runtime variables

server.port=8080

## Technology Stack
- MongoDB
- Spring Boot 2.6
- Java 8

## Running the application
1. Run the following command to create an executable jar file.
```
mvn clean package
```
2. Run the below command to start up the Spring Boot application.
```
java -jar target/robot-apocalypse-0.0.1-SNAPSHOT.jar
```

## Swagger Endpoint

Swagger spec can be accessed from http://localhost:8080/swagger-ui.html

## Endpoints
| URL                                                         | HTTP Request Metods | Description                                       |
|-------------------------------------------------------------|:-------------------:|:--------------------------------------------------|
| http://localhost:8080/api/survivor/addSurvivor              |        post         | To add single Survivor details.                   |
| http://localhost:8080/api/survivor/addSurvivorList          |        post         | To add multiple Survivors details.                |
| http://localhost:8080/api/survivor/updateInventory          |        patch        | Update survivor location.                         |
| http://localhost:8080/api/survivor/updateLastKnownLocation  |        patch        | Update survivor last known Location.              |
| http://localhost:8080/api/survivor/reportSurvivorIfInfected |        patch        | Update if survivors is Infected.                  |
| http://localhost:8080/api/survivor/                         |         get         | To get the details of all Survivors.              |
| http://localhost:8080/api/report/infectedSurvivors          |         get         | To get the details of all infected Survivor.      |
| http://localhost:8080/api/report/nonInfectedSurvivors       |         get         | To get the details of all non-infected Survivors. |
| http://localhost:8080/api/report/infectedPercentage         |         get         | To get percentage of infected survivors.          |
| http://localhost:8080/api/report/nonInfectedPercentage      |         get         | To get percentage of non-infected survivors.      |
| http://localhost:8080/api/report/getAllRobots               |         get         | To get the details of all Robots CPU.             |
