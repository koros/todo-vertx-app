# 🧩 TODO Reactive App (Vert.x + Hibernate Reactive + PostgreSQL)

This project is a **learning exercise** exploring how to build a **Reactive CRUD API** using  
**Eclipse Vert.x**, **Hibernate Reactive**, and **PostgreSQL** — all containerized via **Docker Compose**.

> ⚠️ **Note:** This is not a production-ready system.  
> It’s a personal sandbox for experimenting with Hibernate Reactive, reactive database access,
> and reactive Vert.x web APIs.

---

## 🚀 Features

- Reactive REST API built on [Vert.x 5](https://vertx.io)
- Non-blocking data access with [Hibernate Reactive](https://hibernate.org/reactive)
- PostgreSQL database managed via Docker
- Database schema managed with [Liquibase](https://www.liquibase.org)
- Dependency injection using [Dagger 2](https://dagger.dev)
- JSON serialization via Jackson (with [Zalando Problem](https://opensource.zalando.com/problem/) for RFC-7807 error responses)
- Containerized multi-stage build producing a minimal runtime image

---

## 🧱 Project Structure

```
todo-vertx-app/
├── pom.xml                      # Parent POM (dependency & plugin management)
├── todo/                        # Main application module
│   ├── src/
│   │   ├── main/java/com/example/todo/...  # Source code
│   │   └── main/resources/
│   │       └── db/changelog/              # Liquibase migrations
│   ├── Dockerfile              # Multi-stage build for app image
│   └── pom.xml
├── Dockerfile.liquibase        # Liquibase migration runner
├── docker-compose.yml          # Services: postgres, liquibase, app
└── .env                        # Environment configuration
```

---

## ⚙️ Prerequisites

- **Docker** ≥ 24.x  
- **Docker Compose** ≥ v2.x  
- (Optional) **Java 21** + **Maven 3.9+** if you want to build locally without Docker.

---

## 🧩 Environment Configuration

The `.env` file holds all environment variables used by Docker Compose:

```bash
# Database
POSTGRES_DB=todo
POSTGRES_USER=todo
POSTGRES_PASSWORD=todo
POSTGRES_HOST=db
POSTGRES_PORT=5432

# App connection details
DB_USER=${POSTGRES_USER}
DB_PASSWORD=${POSTGRES_PASSWORD}
HR_REACTIVE_URL=vertx-reactive:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}

# App HTTP port
HTTP_PORT=8080

# Liquibase JDBC URL (Liquibase uses JDBC, not reactive)
LIQUIBASE_URL=jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}
LIQUIBASE_USER=${POSTGRES_USER}
LIQUIBASE_PASSWORD=${POSTGRES_PASSWORD}
LIQUIBASE_CHANGELOG=classpath:/changelog/changelog.xml
```

---

## 🧰 Build & Run (Docker)

Everything is containerized. From the project root:

```bash
# 1️⃣ Build the app image
docker compose build --no-cache --progress=plain app

# 2️⃣ Start the full stack (Postgres + Liquibase + App)
docker compose up
```

The flow:
1. `db` service starts PostgreSQL and waits for readiness.
2. `liquibase` service runs DB migrations and exits.
3. `app` service starts Vert.x HTTP server on port `8080`.

To rebuild after code changes:
```bash
docker compose up --build
```

Stop everything:
```bash
docker compose down
```

---

## 🧪 Local Build (optional)

If you prefer to run locally instead of Docker:

```bash
# Compile & package the shaded jar
mvn -q -DskipTests -pl todo -am clean package

# Run it manually
java -jar todo/target/app.jar
```

You’ll need a running PostgreSQL instance configured via `application.yml` or `.env`.

---

## 🔗 Example API Usage

Once running, access the API:

```bash
# List all todos
curl http://localhost:8080/todos

# Create a new todo
curl -X POST http://localhost:8080/todos   -H "Content-Type: application/json"   -d '{"title":"Write documentation"}'
```

---

## 🧩 Troubleshooting

| Problem | Possible Fix |
|----------|---------------|
| `/app` empty inside container | Ensure Dockerfile COPY pattern matches the JAR (e.g., `todo-*.jar` or `*-shaded.jar`) |
| `No suitable driver found for vertx-reactive:postgresql` | Verify `HR_REACTIVE_URL` is set correctly and `persistence.xml` uses reactive provider |
| `Detected use of reactive Session from a different Thread` | Ensure the `AppVertxInstance` SPI class is set up so Hibernate Reactive uses the same Vert.x instance |
| `no main manifest attribute` | Check the Maven Shade Plugin configuration in `todo/pom.xml` |

---

## 🧠 Learning Objectives

This project demonstrates how to:

- Structure a modular Vert.x + Dagger reactive backend
- Integrate Hibernate Reactive with Vert.x event loop context
- Use Liquibase migrations inside Docker
- Implement RFC-7807 problem responses for API errors
- Package Java applications into minimal layered Docker images

---

## 🪶 License

MIT License — for educational and learning purposes only.
