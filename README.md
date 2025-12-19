# Client Migration Platform - Backend

Backend API service for the Client Migration Tool, built with Spring Boot following Domain-Driven Design principles.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.9**
- **Maven**
- **Lombok**

## Architecture

This project follows **Domain-Driven Design (DDD)** with a layered architecture:

```
├── application/          # API controllers, DTOs, exception handling
├── domain/              # Business logic, entities, value objects
└── infrastructure/      # Technical implementations, repositories
```

### Key Design Patterns

- **Repository Pattern** - Abstract data access layer
- **Aggregate Roots** - Client as bounded context root
- **Domain Services** - Business logic orchestration

## Prerequisites

- Java 21 or higher
- Maven 3.6+

## Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/bini219/fred-migration-platform.git
   cd platform
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The API will start at `http://localhost:8080`

## API Endpoints

### Get Legacy Clients
```http
GET /api/legacy/client
```
Returns all clients that haven't been migrated yet.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Acme Corporation",
    "email": "contact@acme.com",
    "migrated": false
  }
]
```

### Migrate Client
```http
POST /api/migrate/{id}
```
Migrates a client from the legacy system to the new platform.

**Response:**
```json
{
  "client": {
    "id": 1,
    "name": "Acme Corporation",
    "email": "contact@acme.com",
    "migrated": true
  },
  "migratedAt": "2024-12-19T10:30:00",
  "message": "Client migrated successfully"
}
```

**Error Responses:**
- `404 Not Found` - Client doesn't exist
- `400 Bad Request` - Client already migrated

### Get Migrated Clients
```http
GET /api/new/client
```
Returns all clients that have been successfully migrated.

**Response:**
```json
[
  {
    "id": 1,
    "name": "Acme Corporation",
    "email": "contact@acme.com",
    "migrated": true
  }
]
```

## Configuration

Configuration is located in `src/main/resources/application.yml`:

```yml
spring:
  application:
    name: client-migration-tool-platform

logging:
  level:
    mau.com.fred.platform: ${MIGRATION-SERVICE-LOG-LEVEL:INFO}

server:
  port: ${MIGRATION-SERVICE-PORT:8080}
```

## Project Structure

```
src/main/java/mau/com/fred/platform/
│
├── application/
│   ├── PlatformApplication.java    # Main entry point
│   ├── client/
│   │   ├── controller/                    # Client REST endpoints
│   │   ├── request/                       # Request DTOs
│   │   └── response/                      # Response DTOs
│   ├── migrate/
│   │   ├── controller/                    # Migration REST endpoints
│   │   ├── request/
│   │   └── response/
│   └── common/
│       ├── ErrorResponse.java
│       └── GlobalExceptionHandler.java    # Centralized error handling
│
├── domain/
│   ├── client/
│   │   ├── model/
│   │   │   ├── Client.java                # Aggregate root
│   │   └── service/
│   │       └── ClientRepository.java      # Repository interface
│   └── migrate/
│       ├── model/
│       │   └── MigrationResult.java
│       └── service/
│           ├── MigrationService.java      # Business logic
│           └── ClientNotFoundException.java
│
└── infrastructure/
    ├── persistence/
    │   └── InMemoryClientRepository.java  # In-memory implementation
    └── config/
        └── CorsConfig.java                # CORS configuration
```

## Domain Model

### Client (Aggregate Root)
```java
public class Client {
    private final Long id;
    private final String name;
    private final String email;
    private boolean migrated;

    public void markAsMigrated() {
        if (this.migrated) {
            throw new IllegalStateException("Client is already migrated");
        }
        this.migrated = true;
    }
}
```

## Error Handling

All exceptions are handled globally by `GlobalExceptionHandler`:

- **ClientNotFoundException** → 404 Not Found
- **IllegalStateException** → 400 Bad Request (already migrated)
- **IllegalArgumentException** → 400 Bad Request (validation error)
- **Generic Exception** → 500 Internal Server Error

## Development

### Running Tests
```bash
./mvnw test
```

### Building for Production
```bash
./mvnw clean package
java -jar target/client-migration-tool-platform-0.0.1-SNAPSHOT.jar
```

### Code Style
This project uses:
- Lombok annotations for cleaner code
- Constructor injection for dependencies
- Immutable value objects
- Rich domain models with behavior

## CORS Configuration

CORS is enabled for local development:
- Allowed origins: `http://localhost:3000`
- Allowed methods: GET, POST, PUT, DELETE
- Allowed headers: All

**Note:** Update `CorsConfig.java` for production deployment.

## Migration Flow

1. Client exists in legacy system (`migrated: false`)
2. POST request to `/api/migrate/{id}`
3. Domain service validates the client
4. Client entity marks itself as migrated
5. Repository persists the change
6. Migration result is returned
7. Client appears in new system list

## Future Enhancements

- [ ] Add integration tests
- [ ] Dockerize
- [ ] Use streaming tools like kafka
- [ ] Batch migration support
- [ ] Add authentication/authorization
- [ ] Add migration rollback functionality
# fred-migration-platform
