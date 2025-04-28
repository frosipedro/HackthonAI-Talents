# Discovery Server

The Discovery Server is a crucial component of the banking system's microservices architecture. It is responsible for service discovery, allowing different services to find and communicate with each other dynamically.

## Features

- **Service Registration**: Microservices can register themselves with the Discovery Server upon startup.
- **Service Discovery**: Other services can discover registered services and communicate with them without hardcoding their locations.
- **Load Balancing**: The Discovery Server can help distribute requests among multiple instances of a service.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

### Running the Application

1. Navigate to the `discovery-server` directory.
2. Build the project using Maven:

   ```
   mvn clean install
   ```

3. Run the application:

   ```
   mvn spring-boot:run
   ```

### Configuration

The Discovery Server can be configured using the `application.yml` file located in `src/main/resources`. You can set properties such as server port, Eureka server settings, and more.

### Testing

Unit tests for the Discovery Server can be found in the `src/test/java/com/banking/discovery` directory. You can run the tests using:

```
mvn test
```

## Dependencies

The Discovery Server uses the following key dependencies:

- Spring Cloud Netflix Eureka for service discovery
- Spring Boot for building the application

## Conclusion

The Discovery Server plays a vital role in the microservices architecture of the banking system, enabling seamless communication and scalability among services.