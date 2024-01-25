
# LoginSignUp Project

This is a Spring Boot microservice implementing few REST API for user login and signup functionality, utilizing Spring Security, H2 database, and JSON Web Tokens (JWT) for authentication and authorization.




## Getting Started

**Prerequisites**
+ Java 17 or later
- Maven
- Git



## Setup of project

1. Clone the repository:


```bash
https://github.com/itsviveksinghania/LoginSignUpService.git
cd LoginSignUp
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will be accessible at http://localhost:8080

## Dependencies
* Spring Boot Starter Data JPA: For Java Persistence API (JPA) support.
* Spring Boot Starter Security: For security features.
* Spring Boot Starter Web: For building web applications.
* H2 Database: In-memory database for development and testing.
* Lombok: Reduces boilerplate code.
* Spring Boot Starter Test: For testing.
* JSON Web Tokens (JWT): Used for secure user authentication.
* Spring Boot Starter Validation: For validation support.


## API Endpoints
**User Signup**
* Endpoint: POST /api/signup
* Description: Create a new user account.
* Request Body:
```bash
{
  "username": "your_username",
  "password": "your_password"
}
```

* Response
```bash
{
  "message": "User registered successfully"
}
```

**User Login**
* Endpoint: POST /api/login
* Description: Authenticate the user and generate a JWT token.
* Request Body:
```bash
{
  "username": "your_username",
  "password": "your_password"
}
```

* Response:
```bash
{
  "token": "your_jwt_token",
  "message": "Logged in Successfully"
}
```

**Hello Endpoint (Protected)**
* Endpoint: POST /api/hello
* Description: Accessible only with a valid JWT token.
* Request Body:
```bash
{
  "token": "your_jwt_token"
}
```
* Response:
```bash
{
  "message": "Hello from GreenStitch"
}
```

## Configuration
* **H2 Database** Console: Accessible at http://localhost:8080/h2-console. Settings can be configured in application.properties

* **JWT Configuration**: JWT expiration settings can be configured in application.properties
