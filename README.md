# Running the Project

This project is a Java application built with Spring Boot and Gradle. Here are the steps to run the project:

## Prerequisites

- Java 21 or higher
- IntelliJ IDEA
- Docker

## Steps

1. **Clone the repository**

   Open a terminal and run the following git command:
   ```
   <pre>git clone https://github.com/twist250/e-invoice.git </pre> Replace your-repository-name with the name of your repository

1. **Create .env file**

   Open a terminal and run the following git command:
   ```
   cp sample.env .env
   ```
   Fill in the values in the `.env` file with the appropriate values.

2. **Run docker-compose.yaml file**

   This docker-compose file will create a PostgreSQL database and a RABBITMQ containers. To run the docker-compose file,
   run the following command:
   ```
   docker compose up -d
   ```
3. **Run the application**

   Run the project using Intellij IDEA and you can access it
   at `http://localhost:8080`.

## Running Tests

To run the tests, use the following command:

```
./gradlew test
```

The project has testcontainers configured meaning it will use the database and rabbitmq from the tests