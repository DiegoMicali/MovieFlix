# 🎬 MovieFlix API

A RESTful API for managing a movie catalog — including categories, streaming platforms, and users — built with **Spring Boot** and secured with **JWT stateless authentication**.

![Java](https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=flyway&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)

---

## 📋 Table of Contents

- [About](#-about)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [API Documentation](#-api-documentation)
- [Database Migrations](#-database-migrations)
- [Project Structure](#-project-structure)

---

## 📖 About

**MovieFlix** is a backend application that exposes a complete REST API for a movie catalog platform. Users register and authenticate via JWT, then manage movies, assign them to categories, and link them to the streaming services where they are available.

The project was designed to demonstrate modern Java backend practices:

- **Stateless security** with Spring Security + JWT
- **Versioned database schema** with Flyway migrations
- **Layered architecture** (Controller → Service → Repository)
- **Centralized exception handling** with predictable, standardized HTTP responses
- **Self-documenting API** with Swagger / OpenAPI

---

## ✨ Features

- 🔐 **Authentication & Authorization** — user registration and login with JWT token issuance; all catalog routes are protected
- 🎥 **Movie Management** — full CRUD plus search by category
- 🏷️ **Category Management** — create, list, and delete movie categories
- 📺 **Streaming Platform Management** — register the platforms where each movie is available
- ✅ **Bean Validation** — request payloads validated with clear error messages (e.g. required fields)
- 🚨 **Global Exception Handling** — `@ControllerAdvice` translating domain errors into proper HTTP status codes
- 📄 **Interactive Documentation** — Swagger UI with JWT security scheme support

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4 (Web MVC, Data JPA, Validation) |
| Security | Spring Security + JWT ([auth0 java-jwt](https://github.com/auth0/java-jwt)) |
| Database | PostgreSQL |
| Migrations | Flyway |
| Documentation | SpringDoc OpenAPI (Swagger UI) |
| Boilerplate | Lombok |
| Build | Maven (wrapper included) |
| Infrastructure | Docker & Docker Compose |

---

## 🏗 Architecture

```
Client ──► Controller ──► Service ──► Repository ──► PostgreSQL
              │
              ├── Request/Response DTOs + Mappers
              ├── Bean Validation
              └── SecurityFilter (JWT) + ControllerAdvice
```

- **Controllers** expose the REST endpoints and work only with DTOs
- **Services** hold the business logic
- **Repositories** (Spring Data JPA) handle persistence
- **Mappers** convert between entities and request/response DTOs
- **SecurityFilter** intercepts every request, validates the JWT, and populates the security context

---

## 🚀 Getting Started
### Environment Variables

Configure these variables before running the application:

- `MOVIEFLIX_SECURITY_SECRET` — JWT signing secret.
- `DB_USERNAME` — PostgreSQL username.
- `DB_PASSWORD` — PostgreSQL password.

Keep real secrets and credentials out of version control.

### Prerequisites

- [Docker](https://www.docker.com/) and Docker Compose — **or**
- Java 25 + a local PostgreSQL instance

### Option 1 — Run with Docker Compose

```bash
# 1. Build the application jar
./mvnw clean package -DskipTests

# 2. Start API + PostgreSQL
docker compose up --build
```

The API will be available at `http://localhost:8080`.

### Option 2 — Run locally

1. Start PostgreSQL and create the database:

```sql
CREATE DATABASE movieflix;
```

2. Configure `DB_USERNAME`, `DB_PASSWORD`, and `MOVIEFLIX_SECURITY_SECRET` as environment variables before running the application.
3. Run the application:

```bash
./mvnw spring-boot:run
```

Flyway runs all migrations automatically on startup — no manual schema setup required.

---

## 🔗 API Endpoints

Base path: `/movieflix`

### 🔓 Authentication (public)

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/movieflix/auth/register` | Register a new user |
| `POST` | `/movieflix/auth/login` | Authenticate and receive a JWT token |

### 🎥 Movies (requires JWT)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/movieflix/movie` | List all movies |
| `GET` | `/movieflix/movie/{id}` | Get a movie by id |
| `GET` | `/movieflix/movie/search?category={id}` | Search movies by category |
| `POST` | `/movieflix/movie` | Create a movie |
| `PUT` | `/movieflix/movie/{id}` | Update a movie |
| `DELETE` | `/movieflix/movie/{id}` | Delete a movie |

### 🏷️ Categories (requires JWT)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/movieflix/category` | List all categories |
| `GET` | `/movieflix/category/{id}` | Get a category by id |
| `POST` | `/movieflix/category` | Create a category |
| `DELETE` | `/movieflix/category/{id}` | Delete a category |

### 📺 Streaming Platforms (requires JWT)

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/movieflix/streaming` | List all streaming platforms |
| `GET` | `/movieflix/streaming/{id}` | Get a streaming platform by id |
| `POST` | `/movieflix/streaming` | Create a streaming platform |
| `DELETE` | `/movieflix/streaming/{id}` | Delete a streaming platform |

### Authentication flow

```bash
# 1. Register
curl -X POST http://localhost:8080/movieflix/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@email.com", "password": "123456"}'

# 2. Login — returns the JWT token
curl -X POST http://localhost:8080/movieflix/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "john@email.com", "password": "123456"}'

# 3. Use the token on protected routes
curl http://localhost:8080/movieflix/movie \
  -H "Authorization: Bearer <your-token>"
```

---

## 📄 API Documentation

With the application running, the interactive Swagger UI is available at:

- **Swagger UI:** [http://localhost:8080/swagger/index.html](http://localhost:8080/swagger/index.html)
- **OpenAPI spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

The documentation includes the JWT security scheme — click **Authorize** and paste your token to test protected endpoints directly from the browser.

---

## 🗄 Database Migrations

The schema is fully versioned with Flyway (`src/main/resources/db/migration`):

| Version | Migration |
|---|---|
| `V1` | Create `category` table |
| `V2` | Create `streaming` table |
| `V3` | Create `movie` table |
| `V4` | Create `movie_category` join table |
| `V5` | Create `movie_streaming` join table |
| `V6` | Create `user` table |

---

## 📁 Project Structure

```
src/main/java/com/movieflix
├── config/          # Security, JWT token service, Swagger configuration
├── controller/      # REST controllers + global exception handler
├── entity/          # JPA entities (Movie, Category, Streaming, User)
├── exception/       # Custom domain exceptions
├── mapper/          # Entity ↔ DTO mappers
├── repository/      # Spring Data JPA repositories
├── request/         # Request DTOs (with validation)
├── response/        # Response DTOs
└── service/         # Business logic
```

---

## 👤 Author

**Diego Micali** — [GitHub](https://github.com/DiegoMicali)

---

<p align="center">Made with ☕ and Spring Boot</p>
