# Auth Service

The Auth Service is responsible for handling authentication and user management within the banking system. It provides endpoints for user registration, login, and token management.

## Features

- User registration
- User login
- Token generation and validation
- Secure password storage

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

2. Navigate to the `auth-service` directory:
   ```
   cd banking-system-backend/auth-service
   ```

3. Build the project using Maven:
   ```
   mvn clean install
   ```

### Configuration

The application configuration can be found in `src/main/resources/application.yml`. Update the configuration as needed, especially for database connections and security settings.

### Running the Application

To run the Auth Service, execute the following command:
```
mvn spring-boot:run
```

### API Endpoints

- **POST /api/auth/register**: Register a new user.
- **POST /api/auth/login**: Authenticate a user and return a JWT token.

### Testing

Unit tests are located in the `src/test/java/com/banking/auth` directory. You can run the tests using:
```
mvn test
```

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

### Acknowledgments

- Spring Boot for the framework
- JWT for token management
- H2 Database for in-memory storage during development

Feel free to contribute to this project by submitting issues or pull requests!