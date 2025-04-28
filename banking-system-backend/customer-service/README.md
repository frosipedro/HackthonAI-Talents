# Customer Service Backend

This is the Customer Service module of the Banking System backend. It is responsible for managing customer-related operations, including creating, retrieving, updating, and deleting customer information.

## Structure

The Customer Service module follows a layered architecture, consisting of the following components:

- **Controller**: Handles HTTP requests and responses. The `CustomerController` class is responsible for exposing RESTful endpoints for customer operations.
  
- **Service**: Contains business logic related to customers. The `CustomerService` class provides methods to interact with the customer data.

- **Repository**: Interfaces for data access. The `CustomerRepository` extends `JpaRepository` to provide CRUD operations on `Customer` entities.

- **Model**: Represents the data structure. The `Customer` class defines the attributes of a customer.

## Configuration

The application is configured using the `application.yml` file located in the `src/main/resources` directory. This file contains properties such as database connection details and other service-specific configurations.

## Database Schema

The database schema for the Customer Service is defined in the `schema.sql` file, which is also located in the `src/main/resources` directory. This file outlines the structure of the customer table.

## Running the Application

To run the Customer Service application, use the following command:

```bash
mvn spring-boot:run
```

Ensure that you have the necessary dependencies installed and that your database is properly configured.

## Testing

Unit tests for the `CustomerController` and `CustomerService` are located in the `src/test/java/com/banking/customer` directory. You can run the tests using:

```bash
mvn test
```

## API Endpoints

The following endpoints are available in the Customer Service:

- `POST /customers`: Create a new customer.
- `GET /customers/{id}`: Retrieve a customer by ID.
- `PUT /customers/{id}`: Update an existing customer.
- `DELETE /customers/{id}`: Delete a customer by ID.

## Conclusion

This module is a crucial part of the Banking System backend, enabling efficient management of customer data. For further details, refer to the source code and the associated tests.