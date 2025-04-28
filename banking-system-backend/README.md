# Banking System Backend

This project is a modernized banking system backend, originally written in COBOL, now implemented using Java and Spring Boot. The architecture follows a microservices approach, with separate services for customer management, account management, transaction processing, authentication, and service discovery.

## Project Structure

The backend is organized into several microservices, each responsible for a specific domain:

- **api-gateway**: Acts as the entry point for all client requests, routing them to the appropriate service.
- **customer-service**: Manages customer-related operations, including registration and retrieval of customer information.
- **account-service**: Handles account-related functionalities, such as account creation, retrieval, and management.
- **transaction-service**: Manages transactions, including processing and reporting of transactions.
- **auth-service**: Responsible for user authentication and authorization.
- **discovery-server**: Facilitates service discovery for microservices, allowing them to find and communicate with each other.

## Technologies Used

- **Java**: The primary programming language for backend development.
- **Spring Boot**: Framework used to build the microservices.
- **Maven**: Build tool for managing dependencies and project structure.
- **H2 Database**: In-memory database used for development and testing.
- **JUnit**: Framework for unit testing Java applications.
- **Spring Security**: Provides authentication and authorization capabilities.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Docker (optional, for running services in containers)

### Running the Application

1. Clone the repository:
   ```
   git clone <repository-url>
   cd banking-system-backend
   ```

2. Build the project using Maven:
   ```
   mvn clean install
   ```

3. Start the services:
   - You can run each service individually by navigating to the respective service directory and executing:
     ```
     mvn spring-boot:run
     ```
   - Alternatively, you can use Docker Compose to start all services at once:
     ```
     docker-compose up
     ```

### Accessing the Services

- The API Gateway will be available at `http://localhost:8080`.
- Each service can be accessed through the API Gateway, which routes requests to the appropriate service based on the defined endpoints.

## Testing

Unit tests are provided for each service. To run the tests, navigate to the service directory and execute:
```
mvn test
```

## Documentation

Each microservice contains its own README.md file with specific details about its functionality, endpoints, and usage.

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for more details.