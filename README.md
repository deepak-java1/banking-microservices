# Banking Microservices Project

Spring Boot based Banking Microservices project built using Microservices Architecture with Service Discovery, API Gateway, Kafka-based asynchronous communication, JWT Authentication, Feign Client, MySQL, and distributed transaction flow.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Cloud Gateway
- Eureka Server
- OpenFeign
- Apache Kafka
- MySQL
- Spring Data JPA
- Hibernate
- Maven
- Git + GitHub

---

## Services

### 1. Service Registry (Eureka Server)

Used for service discovery.

All microservices register here and communicate using service names instead of hardcoded URLs.

Example:

account-service  
transaction-service

---

### 2. API Gateway

Single entry point for all client requests.

Handles:

- request routing
- load balancing
- centralized access

Example:

/api/accounts/**  
/api/customers/**  
/api/transactions/**

---

### 3. Auth Service

Handles:

- user registration
- login
- JWT token generation
- token validation
- refresh token

APIs:

POST /auth/register  
POST /auth/login  
GET /auth/validate  
POST /auth/refresh-token

---

### 4. Customer Service

Handles customer management:

- create customer
- update customer
- get customer
- delete customer

---

### 5. Account Service

Handles:

- account creation
- balance check
- deposit
- withdraw
- freeze/unfreeze account
- transaction limit
- Kafka transaction processing

Also consumes Kafka transaction events.

---

### 6. Transaction Service

Handles:

- create transaction
- save transaction as PENDING
- publish event to Kafka
- update transaction status (SUCCESS / FAILED)

---

## Kafka Flow

Client  
→ POST /transactions  
→ Transaction Service  
→ Save transaction (PENDING)  
→ Kafka Producer sends event  
→ transaction-topic  
→ Account Service Consumer receives event  
→ processTransaction()  
→ balance validation  
→ update account balance  
→ update transaction status

Final Status:

PENDING → SUCCESS / FAILED

---

## JWT Flow

Client  
→ POST /auth/login  
→ Auth Service validates user  
→ JWT generated  
→ client receives token

For secured APIs:

Client sends JWT  
→ API Gateway validates token  
→ request forwarded to microservice

---

## Important Features

- DB locking using @Transactional + findByIdForUpdate()
- account freeze validation
- insufficient balance handling
- Kafka-based async transaction processing
- Feign Client communication
- DTO-based layered architecture
- centralized logging
- production-style microservice flow

---

## Project Structure

banking-microservices/

- service-registry
- api-gateway
- auth-service
- customer-service
- account-service
- transaction-service

---

## Future Enhancements

- Notification Service
- Circuit Breaker using Resilience4j
- Retry mechanism
- Rate limiting
- Admin/Reporting Service
- CI/CD with Jenkins + Docker + AWS EC2

---

## Author

Deepak Kumar

Java Backend Developer  
Spring Boot | Microservices | Kafka | MySQL | JWT | Feign | API Gateway
