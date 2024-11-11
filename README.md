<div align="center">
  <img src="./frontend/public/logo.png" width="250" />
</div>

# BANK OF MOROCCO 
The **Bank of Morocco** is a web-based banking application designed to manage customer accounts, transactions, and banking operations. This application leverages modern frameworks and technologies such as **Spring Boot**, **Angular**, **Spring Security**, and **Lombok** to provide a secure, efficient, and user-friendly banking experience.

## Features

- **User Authentication and Authorization**: Utilizes **Spring Security** to manage user roles and permissions (e.g., admin, customer) to ensure secure access to banking features.
- **Account Management**: Customers can view their accounts, check balances, and manage personal details.
- **Transaction Management**: Supports transferring funds between accounts, viewing transaction history, and managing funds securely.
- **DTOs for Data Transfer**: **Lombok** is used to simplify the development process by automating the generation of getter, setter, and other common methods in DTO classes.
- **Responsive Frontend**: Built with **Angular**, providing a modern, responsive user interface that works seamlessly across all devices.
- **Secure Communication**: The application ensures secure communication using **SSL/TLS** encryption for all transactions and sensitive data.

## Technologies Used

- **Backend**: 
  - **Spring Boot**: Framework for building the backend application.
  - **Lombok**: To reduce boilerplate code in model and DTO classes.
  - **JPA** (Java Persistence API): For interacting with relational databases.
  
- **Frontend**:
  - **Angular**: Framework used for building the dynamic and interactive frontend interface.

- **Database**:
  - **MySQL**: Relational database to store user information, accounts, transactions, etc.

  
## Setup Instructions

### Prerequisites

- Java 11 or higher
- Node.js and npm
- MySQL database server
- Maven

### Running the Backend (Spring Boot)

- Clone the repository:
  ```
   git clone https://github.com/username/bank-of-morocco.git
   cd bank-of-morocco
  ```
- Set up the database in **MySQL**:
   - Create a new database `bank_of_morocco`.
   - Update the database connection properties in `application.properties`.

- Build and run the Spring Boot application:
   mvn spring-boot:run

- The backend should now be running on `http://localhost:8080`.

### Running the Frontend (Angular)

- Navigate to the frontend directory:
  ```
   cd frontend
  ```

- Install dependencies:
  ```
   npm install
  ```
- Run the Angular application:
  ```
    ng serve
  ```
- The frontend should now be running on `http://localhost:4200`.

## Contributing

We welcome contributions! To get started:

- Fork the repository.
- Create a new branch (`git checkout -b feature-name`).
- Commit your changes (`git commit -am 'Add new feature'`).
- Push to the branch (`git push origin feature-name`).
- Open a pull request.

## License

This project is licensed under the MIT License.
