# Event API

Event API is a RESTful service designed to manage and remind users of upcoming events. It provides a comprehensive set of features including CRUD operations on events, batch processing, and automated reminders for upcoming events.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [API Documentation](#api-documentation)
- [Pending Features](#pending-features)
- [Contact](#contact)

## Features

- **Event Management**: Create, retrieve, update, and delete events.
- **Batch Processing**: Bulk save/update and delete operations for events.
- **Reminders**: Automated reminders for events happening within a predefined threshold.
- **Search & Sort**: Find events based on location and sort them based on various attributes.

## Getting Started

This section provides a quick overview of how to set up the project and get it running on your local machine.

### Prerequisites

- Java 11+
- Maven
- Currently running on H2 in-memory database for development purposes. You can easily swap to another SQL database.

### Installation & Setup

1. **Clone the Repository**
```
git clone https://github.com/yourusername/eventapi.git 
```

2. **Navigate to the Project Directory**  
```
cd eventapi
```
* Install the project
```
mvn clean install
```

4. **Configure Database**  
Update the `src/main/resources/application.properties` file with your database connection details. The project is currently set up with H2 for development purposes, but can be easily swapped to another SQL database.

5. **Run the Application** 
 ```
mvn spring-boot:run
```
## Usage

Once the application is running, you can access the API via `http://localhost:8080/events`.

### Testing
This project includes a suite of unit and integration tests designed to ensure the correctness of the application.

Running Tests
To run the tests, you can execute the following Maven command:

 ```
mvn test
```

## API Endpoints

- **Create Event**: `POST /events`
- **Retrieve Event**: `GET /events/{id}`
- **Update Event**: `PUT /events/{id}`
- **Delete Event**: `DELETE /events/{id}`
- **List All Events**: `GET /events/getallevents`
- **Batch Save/Update**: `POST /events/batch`
- **Batch Delete**: `POST /events/delete/batch`

## API Documentation

For detailed API documentation, including parameters, body, and response formats, please refer to our Swagger UI documentation at `http://localhost:8080/swagger-ui.html`.

## Pending Features

- **Subscriptions**: Allow users to subscribe to events. Subscribers will be notified, possibly through a simulated mechanism like console logs, when an event is updated or canceled.
- **Real-Time Notifications**: Provide instant notifications to subscribers regarding event changes, possibly using WebSockets.

## Contact

For any inquiries, please reach out to `sharkonz@gmail.com`.