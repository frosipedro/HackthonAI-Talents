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
│   │   │           ├── dto
│   │   │           │   ├── CreateAccountRequest.java
│   │   │           │   ├── DepositRequest.java
│   │   │           │   ├── TransactionReportDTO.java
│   │   │           │   └── WithdrawRequest.java
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
│   │   │               ├── ValidationUtils.java
│   │   │               ├── exception
│   │   │                   ├── GlobalExceptionHandler.java
│   │   │                   ├── InsufficientBalanceException.java
│   │   │                   ├── InvalidTransactionException.java
│   │   │                   ├── NotFoundException.java
│   │   │                   └── ValidationException.java
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
   ```
2. Access the folder:
   ```
   cd banking-system-backend
   ```

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

### API Endpoints

- **Base URL**
  - `http://localhost:8080/api`

- **Customer Endpoints**
  - `POST /customers`: Create a new customer.
    
   Request:
   ```
  {
  "name": "John Doe",
  "email": "john.doe@example.com",
  "birthDate": "1990-05-20",
  "cpf": "52998224725"
   }
   ```
   Response:
   ```
   {
    "id": 100001,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "birthDate": "1990-05-20",
    "cpf": "52998224725"
   }
   ```
   
  - `GET /customers/{id}`: Retrieve a customer by ID.

   Request:
   ```
   No content
   ```
   Response:
   ```
   {
    "id": 100001,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "birthDate": "1990-05-20",
    "cpf": "52998224725"
   }
   ```
 
  - `GET /customers`: Retrieve all customers.

   Request:
   ```
   No content
   ```
  Response:
   ```
   [
    {
        "id": 100001,
        "name": "John Doe",
        "email": "john.doe@example.com",
        "birthDate": "1990-05-20",
        "cpf": "52998224725"
    },
    {
        "id": 100002,
        "name": "Peter Doe",
        "email": "peter.doe@example.com",
        "birthDate": "1990-12-01",
        "cpf": "20583006027"
    }
   ]
   ```
 
  - `PUT /customers/{id}`: Update a customer by ID.
 
   Request:
   ```
  {
    "name": "John Doe Updated",
    "email": "john.updated@example.com",
    "cpf": "26382483044",
    "birthDate": "1990-05-20"
   }
   ```
   Response:
   ```
   {
    "id": 100001,
    "name": "John Doe Updated",
    "email": "john.updated@example.com",
    "birthDate": "1990-05-20",
    "cpf": "26382483044"
   }
   ```
   
  - `PATCH /customers/{id}`: Partially update a customer by ID.
 
   Request:
   ```
   {
    "name": "John"
   }
   ```
   Response:
   ```
   {
    "id": 100001,
    "name": "John",
    "email": "john.updated@example.com",
    "birthDate": "1990-05-20",
    "cpf": "26382483044"
   }
   ```
   
  - `DELETE /customers/{id}`: Delete a customer by ID.
  
   Request:
   ```
   No content
   ```
   Response:
   ```
   No content
   ```

- **Account Endpoints**
  - `POST /accounts`: Create a new account.
  
   Request:
   ```
   {
    "customerId": 100003,
    "accountType": "C"
   }
   ```
   Response:
   ```
   {
    "id": 100002,
    "customerId": 100003,
    "accountType": "C",
    "balance": 0.0
   }
   ```
 - `GET /accounts/customer/{id}`: Retrieve an account by customer ID.

  Request:
   ```
   No content
   ```
   Response:
   ```
   [
    {
        "id": 100002,
        "customerId": 100003,
        "accountType": "C",
        "balance": 0.0
    }
   ]
   ```
  
  - `POST /accounts/deposit`: Deposit to an account.

  Request:
   ```
   {
    "accountId": 100002,
    "amount": 1000.00
   }
   ```
   Response:
   ```
   {
    "id": 100002,
    "customerId": 100003,
    "accountType": "C",
    "balance": 1000.0
   }
   ```
  
  - `POST /accounts/withdraw`: Withdraw from an account.

   Request:
   ```
   {
    "accountId": 100002,
    "amount": 500.00
   }
   ```
   Response:
   ```
   {
    "id": 100002,
    "customerId": 100003,
    "accountType": "C",
    "balance": 500.0
   }
   ```

- **Transaction Endpoints**
  - `GET /transactions/customer/{id}?startDate={startDate}&endDate={endDate}`: Retrieve transactions by account ID.

   Request:
   ```
   No content
   ```
   Response:
   ```
   [
    {
      "date": "2025-04-30",
      "accountType": "C",
      "transactionType": "D",
      "amount": 100000.0
    }
   ]
   ```

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
