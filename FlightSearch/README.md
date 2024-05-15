## Description
A Java 21 application used to search for available and compatible flights to create a flight route from departure airport to the arrival airport. Applicaiton uses Neo4J for storing flight related information and redis in-memory database for storing temporary data. Sample database can be created with liquibase and provided .json data files. 

## Usecase
Based on the users query parameters during flight route search, the application searches for the departure airport and performs depth-first search to find all available paths between departure and arrival airport, until maximum distance has been reached or until there are no compatibile flights found. <br>
<br>
The search performs validations on found flights to eliminate imcompatibile flights and reduce search time. <br>
The flight validation is based on:
- Flight class available
- Departure date compared to the arrival date of the previous flight
- Number of empty seats compared to the required number of seats
<br>

Compatibile flight routes are aggregated into a list that users can interact with. <br>
Each flight route calculates:
- Total route duration
- Number of stops
- Minimal number of empty seats left
- Total price for the route
<br>
### Technologies used <br>
Springboot Web with Spring Neo4J JPA <br>
Liquibase - for database initialization <br>
Neo4j - used to store persistent flight data (Airports, Cities, Flights, Flight related details) <br>
Redis - stores user's search results for a limited period of time <br>
