# Online Food Delivery Management System

A Spring Boot REST API application for managing customers, restaurants, food items, orders, and deliveries. This project demonstrates a real-world layered architecture using Spring Boot, Spring Data JPA, Hibernate, DTOs, Bean Validation, WebClient, Spring AOP, logging, transactions, and centralized exception handling.

## 📌 Project Overview

The **Online Food Delivery Management System** provides REST APIs to:

* Register and manage customers
* Register restaurants
* Manage restaurant food menus
* Place and manage food orders
* Support multiple food items within an order
* Manage order status
* Create and retrieve delivery information
* Retrieve external food information using WebClient
* Validate API requests
* Handle exceptions globally
* Maintain database consistency using `@Transactional`
* Log service-layer activities using Spring AOP

---

## 🎯 Business Objectives

1. Manage customer information.
2. Register restaurants and their food menus.
3. Allow customers to place food orders.
4. Support multiple food items per order.
5. Calculate order totals automatically.
6. Manage order and delivery statuses.
7. Integrate with an external food information API.
8. Provide meaningful validation and error responses.
9. Maintain database consistency through transactions.
10. Demonstrate AOP-based logging and execution-time monitoring.

---

## 🛠️ Technology Stack

| Technology      | Purpose                          |
| --------------- | -------------------------------- |
| Java            | Programming Language             |
| Spring Boot     | Application Framework            |
| Spring Web      | REST API Development             |
| Spring Data JPA | Database Access                  |
| Hibernate       | ORM                              |
| MySQL           | Database                         |
| Bean Validation | Request Validation               |
| WebClient       | External API Integration         |
| Spring AOP      | Logging & Cross-Cutting Concerns |
| SLF4J / Logback | Application Logging              |
| Maven           | Build Management                 |
| Lombok          | Boilerplate Code Reduction       |
| Postman         | API Testing                      |

---

## 🏗️ Architecture

The project follows a layered architecture:

```text
Client / Postman
       |
       v
+----------------------+
|     Controller       |
+----------------------+
       |
       v
+----------------------+
|       Service        |
| Business Logic       |
+----------------------+
       |
       v
+----------------------+
|     Repository       |
|    Spring Data JPA   |
+----------------------+
       |
       v
+----------------------+
|       MySQL          |
+----------------------+
```

Cross-cutting components:

```text
                +----------------+
                |   Spring AOP   |
                +----------------+
                        |
                        v
Controller → Service → Repository → MySQL
                        |
                        v
                 Logging / Timing
```

---

## 📂 Project Structure

```text
src
└── main
    ├── java
    │   └── com.bridgelabz.OnlineFoodDelivery
    │       │
    │       ├── controller
    │       │   ├── CustomerController.java
    │       │   ├── RestaurantController.java
    │       │   ├── FoodController.java
    │       │   ├── OrderController.java
    │       │   └── DeliveryController.java
    │       │
    │       ├── service
    │       │   ├── CustomerService.java
    │       │   ├── RestaurantService.java
    │       │   ├── FoodService.java
    │       │   ├── OrderService.java
    │       │   └── DeliveryService.java
    │       │
    │       ├── repository
    │       │   ├── CustomerRepository.java
    │       │   ├── RestaurantRepository.java
    │       │   ├── FoodItemRepository.java
    │       │   ├── OrderRepository.java
    │       │   ├── OrderItemRepository.java
    │       │   └── DeliveryRepository.java
    │       │
    │       ├── entity
    │       │   ├── Customer.java
    │       │   ├── Restaurant.java
    │       │   ├── FoodItem.java
    │       │   ├── Order.java
    │       │   ├── OrderItem.java
    │       │   └── Delivery.java
    │       │
    │       ├── dto
    │       │   ├── CustomerRequestDTO.java
    │       │   ├── CustomerResponseDTO.java
    │       │   ├── RestaurantRequestDTO.java
    │       │   ├── RestaurantResponseDTO.java
    │       │   ├── FoodRequestDTO.java
    │       │   ├── FoodResponseDTO.java
    │       │   ├── OrderRequestDTO.java
    │       │   ├── OrderResponseDTO.java
    │       │   ├── OrderItemResponseDTO.java
    │       │   ├── DeliveryRequestDTO.java
    │       │   ├── DeliveryResponseDTO.java
    │       │   └── ExternalFoodDTO.java
    │       │
    │       ├── exception
    │       │   ├── GlobalExceptionHandler.java
    │       │   ├── CustomerNotFoundException.java
    │       │   ├── RestaurantNotFoundException.java
    │       │   ├── FoodNotFoundException.java
    │       │   ├── OrderNotFoundException.java
    │       │   ├── DeliveryNotFoundException.java
    │       │   ├── DuplicateCustomerException.java
    │       │   └── InvalidOrderException.java
    │       │
    │       ├── aspect
    │       │   └── LoggingAspect.java
    │       │
    │       ├── config
    │       │   └── WebClientConfig.java
    │       │
    │       └── OnlineFoodDeliveryApplication.java
    │
    └── resources
        └── application.properties
```

---

## 🗃️ Database Entities

### Customer

```text
Customer
---------
id
name
email
phone
```

### Restaurant

```text
Restaurant
----------
id
name
location
```

### FoodItem

```text
FoodItem
--------
id
name
price
category
restaurant
```

### Order

```text
Order
-----
id
orderDate
totalAmount
status
customer
```

### OrderItem

```text
OrderItem
---------
id
quantity
price
order
foodItem
```

### Delivery

```text
Delivery
--------
id
deliveryAddress
status
order
```

---

## 🔗 JPA Relationships

The project demonstrates the following JPA relationships:

```text
Restaurant 1 -------- N FoodItem

Customer   1 -------- N Order

Order      1 -------- N OrderItem

FoodItem   1 -------- N OrderItem

Order      1 -------- 1 Delivery
```

The implementation demonstrates:

* `@OneToMany`
* `@ManyToOne`
* `@OneToOne`
* `@JoinColumn`
* `mappedBy`
* Cascade operations
* Fetch strategies

---

# 🚀 REST API Endpoints

The application provides **12 REST endpoints**.

| #  | Method | Endpoint                           | Description               |
| -- | ------ | ---------------------------------- | ------------------------- |
| 1  | POST   | `/api/customers`                   | Create customer           |
| 2  | GET    | `/api/customers`                   | Get all customers         |
| 3  | POST   | `/api/restaurants`                 | Create restaurant         |
| 4  | POST   | `/api/restaurants/{id}/foods`      | Add food item             |
| 5  | GET    | `/api/restaurants/{id}/foods`      | Get restaurant menu       |
| 6  | POST   | `/api/orders`                      | Place order               |
| 7  | GET    | `/api/orders/{id}`                 | Get order details         |
| 8  | PUT    | `/api/orders/{id}/status`          | Update order status       |
| 9  | DELETE | `/api/orders/{id}`                 | Cancel order              |
| 10 | POST   | `/api/orders/{id}/delivery`        | Create delivery           |
| 11 | GET    | `/api/orders/{id}/delivery`        | Get delivery              |
| 12 | GET    | `/api/foods/{id}/external-details` | Get external food details |

---

# 🔄 Order Placement Flow

The order creation process follows this business flow:

```text
POST /api/orders
       |
       v
Validate Request
       |
       v
Find Customer
       |
       v
Find Food Items
       |
       v
Validate Quantity
       |
       v
Calculate Total
       |
       v
Create Order
       |
       v
Create OrderItems
       |
       v
Save Order
       |
       v
Return OrderResponseDTO
```

The complete operation is executed using:

```java
@Transactional
```

If any database operation fails, the complete order transaction is rolled back.

---

# 📦 DTO Architecture

JPA entities are **not directly exposed** through REST APIs.

### Request DTOs

```text
CustomerRequestDTO
RestaurantRequestDTO
FoodRequestDTO
OrderRequestDTO
DeliveryRequestDTO
```

### Response DTOs

```text
CustomerResponseDTO
RestaurantResponseDTO
FoodResponseDTO
OrderResponseDTO
OrderItemResponseDTO
DeliveryResponseDTO
ExternalFoodDTO
```

This provides:

* Better API design
* Separation between persistence and API models
* Controlled response data
* Easier validation
* Reduced coupling

---

# ✅ Validation

Bean Validation is used for incoming requests.

Examples:

```java
@NotBlank
@NotNull
@Email
@Positive
@Size
@NotEmpty
```

Invalid requests return:

```text
HTTP 400 BAD REQUEST
```

Example error response:

```json
{
  "timestamp": "2026-08-17T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Email must be valid",
  "path": "/api/customers"
}
```

---

# ⚠️ Global Exception Handling

The application uses:

```java
@RestControllerAdvice
```

to centrally handle exceptions.

Handled exceptions include:

```text
CustomerNotFoundException
RestaurantNotFoundException
FoodNotFoundException
OrderNotFoundException
DeliveryNotFoundException
DuplicateCustomerException
InvalidOrderException
Validation Exceptions
Unexpected Exceptions
```

### Standard Error Response

```json
{
  "timestamp": "2026-08-17T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found",
  "path": "/api/customers/10"
}
```

---

# 🌐 WebClient Integration

The following endpoint communicates with an external REST service:

```text
GET /api/foods/{id}/external-details
```

The external response is mapped to:

```text
ExternalFoodDTO
```

The application handles:

* 4xx errors
* 5xx errors
* Timeout failures
* Connection failures
* External service unavailable errors

External service failures return an appropriate:

```text
HTTP 502 BAD GATEWAY
```

---

# 📊 Spring AOP

Spring AOP is used for service-layer logging.

The aspect captures:

* Class name
* Method name
* Execution time
* Successful execution
* Failed execution

Example log:

```text
INFO  OrderService.placeOrder() started
INFO  OrderService.placeOrder() completed successfully
INFO  Execution time: 125 ms
```

For failures:

```text
ERROR OrderService.placeOrder() failed
ERROR Reason: Food item not found
```

---

# 📝 Logging

SLF4J/Logback is used for application logging.

The project demonstrates:

```text
INFO
DEBUG
WARN
ERROR
```

Important operations are logged, including:

* Customer creation
* Restaurant creation
* Food creation
* Order creation
* Order cancellation
* Unavailable food items
* External API failures
* Service execution failures

---

# 📡 HTTP Status Codes

| Status | Meaning               | Usage                      |
| ------ | --------------------- | -------------------------- |
| 200    | OK                    | Successful GET/PUT         |
| 201    | CREATED               | Successful POST            |
| 204    | NO CONTENT            | Successful DELETE          |
| 400    | BAD REQUEST           | Validation failure         |
| 404    | NOT FOUND             | Resource not found         |
| 409    | CONFLICT              | Duplicate/conflicting data |
| 500    | INTERNAL SERVER ERROR | Unexpected server error    |
| 502    | BAD GATEWAY           | External API failure       |

---

# 🗄️ MySQL Configuration

Create a MySQL database:

```sql
CREATE DATABASE online_food_delivery;
```

Configure the database in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.application.name=OnlineFoodDelivery

spring.datasource.url=jdbc:mysql://localhost:3306/online_food_delivery
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.port=8080
```

Replace:

```text
YOUR_PASSWORD
```

with your MySQL password.

---

# ▶️ How to Run the Project

### 1. Clone the project

```bash
git clone <your-github-repository-url>
```

### 2. Open the project

Open the project in:

```text
IntelliJ IDEA
```

### 3. Configure MySQL

Create the database:

```sql
CREATE DATABASE online_food_delivery;
```

Update the database credentials in:

```text
application.properties
```

### 4. Build the project

Using Maven:

```bash
mvn clean install
```

### 5. Run the application

Run:

```text
OnlineFoodDeliveryApplication.java
```

or:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

# 🧪 Testing with Postman

Postman can be used to test all 12 APIs.

### Create Customer

```http
POST http://localhost:8080/api/customers
```

Example request:

```json
{
  "name": "Saravanan",
  "email": "saravanan@gmail.com",
  "phone": "9876543210"
}
```

### Get Customers

```http
GET http://localhost:8080/api/customers
```

### Create Restaurant

```http
POST http://localhost:8080/api/restaurants
```

Example:

```json
{
  "name": "Food Palace",
  "location": "Bangalore"
}
```

### Add Food

```http
POST http://localhost:8080/api/restaurants/1/foods
```

Example:

```json
{
  "name": "Chicken Biryani",
  "price": 180,
  "category": "Main Course"
}
```

### Get Restaurant Menu

```http
GET http://localhost:8080/api/restaurants/1/foods
```

### Place Order

```http
POST http://localhost:8080/api/orders
```

Example:

```json
{
  "customerId": 1,
  "items": [
    {
      "foodItemId": 1,
      "quantity": 2
    }
  ]
}
```

### Get Order

```http
GET http://localhost:8080/api/orders/1
```

### Update Order Status

```http
PUT http://localhost:8080/api/orders/1/status
```

Example:

```json
{
  "status": "CONFIRMED"
}
```

### Cancel Order

```http
DELETE http://localhost:8080/api/orders/1
```

### Create Delivery

```http
POST http://localhost:8080/api/orders/1/delivery
```

Example:

```json
{
  "deliveryAddress": "Bangalore",
  "status": "ASSIGNED"
}
```

### Get Delivery

```http
GET http://localhost:8080/api/orders/1/delivery
```

### External Food Details

```http
GET http://localhost:8080/api/foods/1/external-details
```

---

# 🔐 Out of Scope

The following features are intentionally excluded:

* Authentication
* Authorization
* Online payment gateway
* Real-time GPS tracking
* Reviews and ratings
* Coupons
* Mobile application
* Admin dashboard

These can be added as future enhancements.

---

# 🔮 Future Enhancements

The application architecture can be extended with:

* JWT authentication
* Role-based authorization
* Payment integration
* Restaurant ratings
* Customer reviews
* Coupon management
* Real-time delivery tracking
* Notification service
* Admin dashboard
* Redis caching
* Microservices architecture
* API documentation using Swagger/OpenAPI

---

# 📈 Non-Functional Requirements

### Maintainability

Uses a clear:

```text
Controller → Service → Repository
```

architecture.

### Reliability

Achieved through:

* Global exception handling
* Transaction management
* Validation
* External API error handling

### Performance

Uses:

* Spring Data JPA
* Appropriate fetch strategies
* Transaction management
* Service-layer optimization

### Scalability

The architecture allows future modules such as:

```text
Payment
Authentication
Reviews
Coupons
Notifications
Delivery Tracking
```

---

# 🎓 Learning Outcomes

After completing this project, the developer will understand how to implement:

* Spring Boot REST APIs
* RESTful endpoint design
* Controller-Service-Repository architecture
* Spring Data JPA
* Hibernate ORM
* JPA relationships
* DTO-based API design
* Bean Validation
* Custom exceptions
* Global exception handling
* `ResponseEntity`
* `@Transactional`
* Spring WebClient
* External REST API integration
* Spring AOP
* SLF4J/Logback logging
* MySQL database integration
* Postman API testing

---

# 👨‍💻 Project Status

```text
Project: Online Food Delivery Management System
Type: Spring Boot REST API
Database: MySQL
Architecture: Layered Architecture
API Count: 12
Testing Tool: Postman
Build Tool: Maven
```

---

# 📄 License

This project is developed for **learning and educational purposes** to demonstrate Spring Boot, REST API development, JPA/Hibernate, validation, transactions, WebClient, AOP, and exception handling.
