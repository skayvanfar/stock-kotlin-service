# stock-kotlin-service


## General Information
* The **Stock Kotlin Service** is a JVM-based backend application built with **Kotlin** **Spring Boot**, **Hibernate**, **Mysql**, **Springdoc-openApi**, **Docker Compose**, **JUnit 5**, and **YAML** property configuration.
* The **Stock Service** uses [JUnit 5](https://junit.org/junit5/) and [TestContainer](https://testcontainers.com/guides/testing-spring-boot-rest-api-using-testcontainers/) to run test database and Springboot integration tests.
* The **Stock Service** uses [springdoc-openapi](https://springdoc.org/) to generate OpenAPI documentation
* The **Stock Service** exposes a RESTful API to manage stocks
* The **Stock Service** can be run locally with **docker-compose** (no local PostgreSQL installation required)

## Database Schema

      column      |    type   |    description
    --------------|-----------|-----------------------------
    id            | integer   | stock id, primary key
    --------------|-----------|-----------------------------
    name          | text      | stock company name, unique
    --------------|-----------|-----------------------------
    current_price | numeric   | stock price
    --------------|-----------|-----------------------------
    last_update   | timestamp | timestamp with timezone

## Stock kotlin Service API

                       |       API endpoint        |    curl example
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Get list of Stocks | GET '/api/stocks'         | curl -X GET 'localhost:8080/api/stocks?page=0&size=40&sort=name'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Get Stock by id    | GET '/api/stocks/{id}'    | curl -X GET 'localhost:8080/api/stocks/1'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Create new Stock   | POST '/api/stocks'        | curl -X POST 'localhost:8080/api/stocks' -H 'Content-Type: application/json' --data-raw '{"name": "BNB", "currentPrice": 100}'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Update Stock price | PATCH '/api/stocks/{id}'  | curl -X PATCH 'localhost:8080/api/stocks/11?price=34'
    -------------------|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------
    Delete Stock       | DELETE '/api/stocks/{id}' | curl -X DELETE 'localhost:8080/api/stocks/2'

## Prerequisites
- Kotlin (https://kotlinlang.org/)
- Java Development Kit (JDK) 17 or higher (https://www.java.com).
- Apache Maven (http://maven.apache.org).
- Git Client (http://git-scm.com).
- Docker (http://docker.com).
- Docker Compose (https://docs.docker.com/compose/).

## Getting Started
1. clone **Stock Kotlin Service** repository:
   ```bash
   git clone https://github.com/skayvanfar/stock-kotlin-service.git
   cd stock-kotlin-service

2. Build docker image **Stock Kotlin Service** application and run tests:
   ```bash
   chmod +x build_docker.sh
   ./build_docker.sh

3. Start the Mysql database and run stock-kotlin-service application using Docker Compose:
    ```bash
    docker compose up

4. Open **Stock Kotlin Service** OpenAPI documentation
   [swagger ui](http://localhost:8080/swagger-ui/index.html)
5. 
5. Test **Stock Kotlin Service** application (see *curl example* in [Stock Kotlin Service API](#stock-kotlin-service-api))
