# MedExpress Consultation API

## Overview

This repository contains the backend implementation for the "Consultation" phase of the user journey for MedExpress. The APIs serve questions to the frontend, receive answers, and determine if a prescription is likely based on the answers provided.

## Notes

- The backend is implemented using Java 17 and Spring Boot 3.3.0.
- In-memory collections are used for data storage as per the requirements.
- OpenAPI 3 documentation is provided for the APIs.

## Requirements

- Java 17 or later
- Gradle

## Running the Application

1. Clone the repository:
   ```sh
   git clone <repository_url>
   cd MedExpress

2. Build the application:
    ```sh
    ./gradlew build

3. Run the application:
    ```
    ./gradlew bootRun

## API Documentation

The API documentation is generated using Springdoc OpenAPI and is available at:

OpenAPI JSON: http://localhost:8080/api-docs
Swagger UI: http://localhost:8080/swagger-ui.html

## Usage

### Postman collection

- Added separately to as secrets cannot be pushed to github

### Get Consultation Questions
Endpoint: `GET /questionnaire/{id}`

Example cURL:
```
curl -X GET "http://localhost:8080/questionnaire/test" -H "accept: application/json"
```

### Submit Consultation Answers
Endpoint: POST /questionnaire

Example cURL:
```
curl -X POST "http://localhost:8080/questionnaire" -H "accept: application/json" -H "Content-Type: application/json" -d '{
"id": "test",
"questions": [
{
"id": "q1",
"value": "true",
"answer": true
},
{
"id": "q2",
"value": "false",
"answer": false
}
]
}'
```

## Trade-offs and Decisions
- The decision to use in-memory collections was made to comply with the requirement of not needing permanent storage.
- Spring Boot was chosen for its simplicity and ease of setup for REST APIs.
- OpenAPI 3 was used for API documentation to provide an interactive way to test the APIs via Swagger UI.

## Further Improvements
- Implementing more robust validation for the consultation answers.
- Adding more detailed logging and error handling.
- Extending the in-memory storage to use a more sophisticated data structure if needed for scaling.

