# Spring Boot Task Tracker

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)]()
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)]()
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)]()
[![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)]()
[![Amazon S3](https://img.shields.io/badge/Amazon_S3-232F3E?style=for-the-badge&logo=amazons3&logoColor=white)]()

## About

A simple task tracker REST API built with Spring Boot. This application allows users to manage tasks with features such as creating, updating, deleting, and retrieving tasks. It uses a PostgreSQL database running in Docker, Spring Security, and JWT for authentication. The project also integrates with Amazon AWS S3 for storing user profile images, and uses Lombok and MapStruct. RESTful endpoints are exposed for task and user management.
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

3. Configure Secrets

On Windows:
```
xcopy secrets.properties.example secret.properties
```

Input your own secrets here:

```properties
# secrets.properties

#JWT
jwt.secret=

# AWS S3
aws.accessKeyId=
aws.secretKey=
aws.s3.bucket=
aws.region=
```

*The `jwt.secret` must be a Base64-encoded 512-bit key.*

4. Run PostgreSQL Database
```
docker compose up -d
```

5. Run the Spring Boot Application

On Windows:
```
mvnw.cmd spring-boot:run
```

6. API Documentation

Once the application is running, you can view and interact with the API documentation using Swagger UI at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

7. Stop the Application

   1. Press ``CTRL + C`` in the terminal where the application is running
   2. Stop the Docker container:
    ```
    docker compose down
    ```