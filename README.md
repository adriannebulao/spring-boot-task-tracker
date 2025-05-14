# Spring Boot Task Tracker

## About
A simple task tracker REST API built with Spring Boot. This application allows users to manage tasks with features such as creating, updating, deleting, and retrieving tasks. It uses a PostgreSQL database for data storage and provides CRUD operations for task management via RESTful endpoints.
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

3. Run PostgreSQL Database
```
docker compose up -d
```

4. Run the Spring Boot Application

On Windows:
```
mvnw.cmd spring-boot:run
```

5. Stop the Application

   1. Press ``CTRL + C`` in the terminal where the application is running
   2. Stop the Docker container:
    ```
    docker compose down
    ```