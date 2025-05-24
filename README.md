# Spring Boot Task Tracker

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)]()
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)]()

## About

A simple task tracker REST API built with Spring Boot. This application allows users to manage tasks with features such as creating, updating, deleting, and retrieving tasks. It uses a PostgreSQL database for data storage and provides CRUD operations for task management via RESTful endpoints. The project leverages Lombok to reduce boilerplate code and MapStruct for clean, efficient DTO-to-entity mapping.
## Setup Guide

1. Clone the repository
```
git clone https://github.com/adriannebulao/spring-boot-task-tracker.git
cd spring-boot-task-tracker
```

2. Install Dependencies

On Windows:
```
mvnw.cmd clean install
```

3. Set up `secrets.properties`

On Windows:
```
xcopy secrets.properties.example secret.properties
```

The `jwt.secret` must be a Base64-encoded 512-bit key.

4. Run PostgreSQL Database
```
docker compose up -d
```

5. Run the Spring Boot Application

On Windows:
```
mvnw.cmd spring-boot:run
```

6. Stop the Application

   1. Press ``CTRL + C`` in the terminal where the application is running
   2. Stop the Docker container:
    ```
    docker compose down
    ```