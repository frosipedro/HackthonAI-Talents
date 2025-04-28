# Account Service

This is the README file for the Account Service of the Banking System backend. The Account Service is responsible for managing bank accounts, including creating, updating, retrieving, and deleting account information.

## Overview

The Account Service is built using Spring Boot and follows a microservices architecture. It communicates with other services through RESTful APIs and uses a relational database for data persistence.

## Features

- Create a new bank account
- Retrieve account details
- Update account information
- Delete an account
- Handle account-related business logic

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. Clone the repository:
   ```
   git clone <repository-url>
   ```

2. Navigate to the account-service directory:
   ```
   cd account-service
   ```

3. Build the project using Maven:
   ```
   mvn clean install
   ```

### Configuration

The application configuration can be found in `src/main/resources/application.yml`. You can configure the database connection and other properties here.

### Running the Application

To run the Account Service, execute the following command:
```
mvn spring-boot:run
```

The service will start on the default port (8080). You can change the port in the `application.yml` file.

### API Endpoints

- **POST /accounts**: Create a new account
- **GET /accounts/{id}**: Retrieve account details by ID
- **PUT /accounts/{id}**: Update account information
- **DELETE /accounts/{id}**: Delete an account

### Testing

Unit tests are located in the `src/test/java/com/banking/account` directory. You can run the tests using:
```
mvn test
```

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any suggestions or improvements.

## License

This project is licensed under the MIT License. See the LICENSE file for details.