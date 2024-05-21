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

## Instalation
### Initializing data
To create neo4j and redis docker containers: <br>
```cd docker``` and run command: <br>
```docker-compose up```. <br>

To initialize data: <br>
```cd src/main/resources/db/changelog``` and run command: <br>
```liquibase update --changelog-file=db.changelog-master.yaml --url=jdbc:neo4j:bolt://localhost --username=neo4j --password=neo4j``` <br>
### Running the application
run the application from the ```FlightSearchApplication``` class.
### Requesting resources
GET request with params: ```localhost:8080/api/v1/routes?departureAirportIata={IATACODE}&arrivalAirportIata={IATACODE}&maxStops={stopNum}&adult={adultNum}&flightClass={ECONOMY/BUSINESS/FIRST}&child={childNum}&infant={infantNum}&departureDate={date yyyy-MM-dd:HH:mm:ss}&FlightType={ONEWAY/ROUND}&returnDate={date yyyy-MM-dd:HH:mm:ss}``` <br>
- departureAirportIata - 3-letter long IATA code of the departure airport
- arrivalAirportIata- 3-letter long IATA code of the arrival airport
- maxStops - maximum number of stops (min 1, max 5)
- adult - number of adult passengers (min 1, max 5)
- child - number of child passengers (min 0, max 5)
- infant - number of infant passengers (min 0, max 5)
- flightClass - class of the flight (ECONOMY / BUISNESS / FIRST)
- departureDate - date of the planned departure (yyyy-MM-ddT:HH:mm:ss format)
- flightType - type of desired flight (ONEWAY / ROUND)
- returnDate (optional) - date of the plnned return (yyyy-MM-ddTHH:mm:ss format)
### Example response
```json
{
    "id": "1d277dad-4f67-4aca-acc5-7a1fd34e6ae4",
    "routeDeparture": [
        {
            "id": "d6290d8d-cf4c-4b54-886f-55f20108be4c",
            "flights": [
                {
                    "flightId": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:912",
                    "from": {
                        "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:272",
                        "iata": "CCF",
                        "latitude": "43.2159996033",
                        "longitude": "2.306319952",
                        "name": "Carcassonne Airport"
                    },
                    "to": {
                        "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:82",
                        "iata": "BMK",
                        "latitude": "53.5963897705",
                        "longitude": "6.7091670036",
                        "name": "Borkum Airport"
                    },
                    "options": {
                        "BUSINESS": {
                            "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:914",
                            "flightClass": "BUSINESS",
                            "price": 96521,
                            "seats": 46
                        },
                        "FIRST": {
                            "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:915",
                            "flightClass": "FIRST",
                            "price": 373755,
                            "seats": 25
                        },
                        "ECONOMY": {
                            "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:913",
                            "flightClass": "ECONOMY",
                            "price": 50502,
                            "seats": 59
                        }
                    },
                    "airline": {
                        "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:2",
                        "iata": "LH",
                        "name": "Lufthansa",
                        "logo_url": "https://api-ninjas.com/images/airline_logos/lufthansa.jpg"
                    },
                    "arrivalDate": "2024-05-21T18:18:00",
                    "departureDate": "2024-05-21T09:09:00"
                }
            ],
            "prices": {
                "BUSINESS": 96521,
                "FIRST": 373755,
                "ECONOMY": 50502
            },
            "departureAirport": {
                "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:272",
                "iata": "CCF",
                "latitude": "43.2159996033",
                "longitude": "2.306319952",
                "name": "Carcassonne Airport"
            },
            "arrivalAirport": {
                "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:82",
                "iata": "BMK",
                "latitude": "53.5963897705",
                "longitude": "6.7091670036",
                "name": "Borkum Airport"
            },
            "departureTime": "2024-05-21T09:09:00",
            "arrivalTime": "2024-05-21T18:18:00",
            "duration": 549,
            "stops": 1,
            "seatsLeft": 46
        }
    ],
    "routeReturn": [
        {
            "id": "87853b04-9823-45bf-ac4c-cdc43f73da55",
            "flights": [
                {
                    "flightId": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:2984",
                    "from": {
                        "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:82",
                        "iata": "BMK",
                        "latitude": "53.5963897705",
                        "longitude": "6.7091670036",
                        "name": "Borkum Airport"
                    },
                    "to": {
                        "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:272",
                        "iata": "CCF",
                        "latitude": "43.2159996033",
                        "longitude": "2.306319952",
                        "name": "Carcassonne Airport"
                    },
                    "options": {
                        "BUSINESS": {
                            "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:2986",
                            "flightClass": "BUSINESS",
                            "price": 86517,
                            "seats": 2
                        },
                        "ECONOMY": {
                            "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:2985",
                            "flightClass": "ECONOMY",
                            "price": 30333,
                            "seats": 157
                        }
                    },
                    "airline": {
                        "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:2",
                        "iata": "LH",
                        "name": "Lufthansa",
                        "logo_url": "https://api-ninjas.com/images/airline_logos/lufthansa.jpg"
                    },
                    "arrivalDate": "2024-06-01T04:01:00",
                    "departureDate": "2024-05-31T17:47:00"
                }
            ],
            "prices": {
                "BUSINESS": 86517,
                "ECONOMY": 30333
            },
            "departureAirport": {
                "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:82",
                "iata": "BMK",
                "latitude": "53.5963897705",
                "longitude": "6.7091670036",
                "name": "Borkum Airport"
            },
            "arrivalAirport": {
                "id": "4:b57f02a5-dac0-401f-a6d5-6b5511a64e02:272",
                "iata": "CCF",
                "latitude": "43.2159996033",
                "longitude": "2.306319952",
                "name": "Carcassonne Airport"
            },
            "departureTime": "2024-05-31T17:47:00",
            "arrivalTime": "2024-06-01T04:01:00",
            "duration": 614,
            "stops": 1,
            "seatsLeft": 2
        }
    ],
    "passengers": [
        {
            "type": "ADULT",
            "quantity": 1
        },
        {
            "type": "CHILD",
            "quantity": 0
        },
        {
            "type": "INFANT",
            "quantity": 0
        }
    ]
}
```
