# Spring Boot Shopping Cart Application

A simple Spring Boot application with a ShoppingCartController

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 8 or later
- Spring Boot
- Maven or Gradle for building the project
- MySQL

## Installation

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/your-username/spring-boot-shopping-cart.git
cd spring-boot-shopping-cart
mvn spring-boot:run

# Usage
API Endpoints

GET /api/cart/view: View the contents of the shopping cart.

POST /api/cart/add/{bookId}?quantity={quantity}: Add a book to the shopping cart.

GET /api/cart/order-details: Retrieve order details.

POST /api/cart/remove/{bookId}?quantity={quantity}: Remove a book from the shopping cart.

GET /api/cart/totalPrice: Calculate the total price of the items in the shopping cart.

POST /api/cart/finishOrder: Finish and place an order.

# CSV API endpoint 
APIs which rely on those two endpoints
GET /api/csv/books: View all books which are saved to database.
POST /api/csv/upload: Upload file.csv and then store it in database.

# Logging
Logging is integrated into the application using SLF4J and Logback. Detailed logs help with debugging and monitoring the application's behavior. Log messages are available in the console output.

# Configuration
For configuring the database, open application.properties file and set your credentials:

