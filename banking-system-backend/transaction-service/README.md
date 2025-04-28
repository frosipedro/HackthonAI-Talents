# Transaction Service

The Transaction Service is a microservice responsible for handling all transaction-related operations in the banking system. This service provides APIs to create, retrieve, update, and delete transactions, ensuring that all operations adhere to the business logic and data integrity requirements.

## Features

- **Create Transaction**: Allows users to create new transactions.
- **Retrieve Transactions**: Fetches transaction details based on transaction ID or user account.
- **Update Transaction**: Enables modification of existing transaction details.
- **Delete Transaction**: Removes a transaction from the system.

## Architecture

The Transaction Service follows a layered architecture consisting of:

- **Controller Layer**: Handles incoming HTTP requests and maps them to service methods.
- **Service Layer**: Contains business logic and interacts with the repository layer.
- **Repository Layer**: Manages data access and persistence using Spring Data JPA.

## Technologies Used

- **Java**: Programming language used for developing the service.
- **Spring Boot**: Framework for building the microservice.
- **Spring Data JPA**: Simplifies data access and manipulation.
- **H2 Database**: In-memory database for development and testing.
- **JUnit**: Framework for unit testing the service.

## Setup Instructions

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the transaction-service directory:
   ```
   cd transaction-service
   ```

3. Build the project using Maven:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

5. Access the API documentation (if available) at:
   ```
   http://localhost:8080/swagger-ui.html
   ```

## Testing

Unit tests are provided for the controller and service layers. To run the tests, use the following command:
```
mvn test
```

## Contribution

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for details.