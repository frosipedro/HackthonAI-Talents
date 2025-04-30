# Banking System

## Overview

This project simulates a banking system migrated from COBOL to a modern Java Spring Boot application. It includes functionalities for managing customers, accounts, and transactions, utilizing an H2 in-memory database for data storage.

## Project Structure

```
banking-system
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── banking
│   │   │           ├── BankingApplication.java
│   │   │           ├── controllers
│   │   │           │   ├── AccountController.java
│   │   │           │   ├── CustomerController.java
│   │   │           │   └── TransactionController.java
│   │   │           ├── entities
│   │   │           │   ├── Account.java
│   │   │           │   ├── Customer.java
│   │   │           │   └── Transaction.java
│   │   │           ├── repositories
│   │   │           │   ├── AccountRepository.java
│   │   │           │   ├── CustomerRepository.java
│   │   │           │   └── TransactionRepository.java
│   │   │           ├── services
│   │   │           │   ├── AccountService.java
│   │   │           │   ├── CustomerService.java
│   │   │           │   └── TransactionService.java
│   │   │           └── utils
│   │   │               └── ValidationUtils.java
│   │   └── resources
│   │       ├── application.properties
│   │       └── data.sql
│   └── test
│       └── java
│           └── com
│               └── banking
│                   ├── controllers
│                   │   ├── AccountControllerTest.java
│                   │   ├── CustomerControllerTest.java
│                   │   └── TransactionControllerTest.java
│                   └── services
│                       ├── AccountServiceTest.java
│                       ├── CustomerServiceTest.java
│                       └── TransactionServiceTest.java
├── pom.xml
└── README.md
```

## Getting Started

### Prerequisites

- Java 21
- Maven 3.9.9

### Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/frosipedro/HackthonAI-Talents.git
   cd banking-system-backend
   ```

2. Build the project:
   ```
   mvn clean install
   ```

3. Run the application:
   ```
   mvn spring-boot:run
   ```

### API Endpoints

- **Base URL**
  - `http://localhost:8080/api`

- **Customer Endpoints**
  - `POST /customers`: Create a new customer.
  - `GET /customers/{id}`: Retrieve a customer by ID.
  - `PUT /customers/{id}`: Update a customer.
  - `DELETE /customers/{id}`: Delete a customer.

- **Account Endpoints**
  - `POST /accounts`: Create a new account.
  - `POST /accounts/deposit`: Deposit to an account.
  - `POST /accounts/withdraw`: withdraw from an account.

- **Transaction Endpoints**
  - `GET /transactions/customer/{id}?startDate={startDate}&endDate={endDate}`: Retrieve transactions by account ID.


### Database Configuration

- **Database**
  - `http://localhost:8080/h2-console`

The application uses an H2 in-memory database. The configuration can be found in `src/main/resources/application.properties`.

### Testing

Unit tests are included for controllers and services. To run the tests, use:
```
mvn test
```

## License

This project is licensed under the MIT License. See the LICENSE file for details.