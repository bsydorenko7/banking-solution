# Banking Solution

This project is a simple REST API for a banking application, allowing users to perform basic banking operations 
such as creating accounts, making deposits, and transferring funds.

## Tech Stack

- **Java 17**: The application runs on Java 17.
- **Spring Boot**: For building the REST API.
- **H2 Database**: In-memory database for development and testing.
- **Spring Data JPA**: For database interactions.
- **Springdoc OpenAPI**: For API documentation.
- **Lombok**: For reducing boilerplate code in Java.
- **JaCoCo**: For code coverage analysis.

## Local Setup and Running Instructions

To run the application locally, follow these steps:

1. **Clone the repository:**

   ```bash
   git clone https://github.com/bsydorenko7/banking-solution.git

2. **Navigate to the project directory:**
   ```bash
   cd banking-solution
   
3. **Build the project with Maven:**
   ```bash
   mvn clean install

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   
*The application will start on the default port 8080.*

## Access the H2 Database Console
**Open your browser and go to http://localhost:8080/h2-ui.**

Use the following credentials:
- JDBC URL: jdbc:h2:mem:bankdb
- Username: admin
- Password: admin

## API Documentation
**Open your browser and go to http://localhost:8080/swagger-ui.html to view the API documentation.**

## Postman Collection
A Postman collection *banking-solution.postman_collection.json* is included in the project to help you test the API endpoints. You can find the collection 
in the *postman_collection* directory:

**To import the collection into Postman:**
- Open Postman.
- Click on "Import" in the top left corner.
- Select the banking_solution_collection.json file from the postman_collection directory.
- Use the imported collection to test the API endpoints.

## Tests
1. **Build the project with Maven:**
   ```bash
   mvn tests

2. **Run the application:**
   ```bash
   mvn clean verify

3. **The report will be generated in the target/site/jacoco directory.**