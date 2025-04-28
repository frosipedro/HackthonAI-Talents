# API Gateway for Banking System

This API Gateway serves as the entry point for all client requests to the banking system microservices. It handles routing, authentication, and security for the various services, ensuring a seamless interaction between clients and the backend services.

## Features

- **Routing**: Directs incoming requests to the appropriate microservice based on the request path.
- **Authentication**: Implements security measures to authenticate users before granting access to the services.
- **Load Balancing**: Distributes incoming requests across multiple instances of microservices to ensure high availability and reliability.
- **Monitoring**: Provides insights into the performance and health of the microservices through logging and metrics.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   cd banking-system-backend/api-gateway
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

### Configuration

The application configuration can be found in `src/main/resources/application.yml`. You can customize the following settings:

- **Server Port**: Change the port on which the API Gateway runs.
- **Service URLs**: Define the URLs for the backend services (customer, account, transaction, auth).

### API Endpoints

The API Gateway exposes the following endpoints:

- **Customer Service**: `/customers/**`
- **Account Service**: `/accounts/**`
- **Transaction Service**: `/transactions/**`
- **Auth Service**: `/auth/**`

### Testing

Unit tests for the API Gateway can be found in `src/test/java/com/banking/gateway/ApiGatewayApplicationTests.java`. You can run the tests using:

```
mvn test
```

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.

## License

This project is licensed under the MIT License. See the LICENSE file for details.