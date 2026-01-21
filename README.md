# coffee-catalog-api

### Architecture

1. Entity layer (Database mapping)
2. Repository Layer (Data access)
3. Response / Request / Mapper Layer (DTO - Data Transfer Objects)
4. Service Layer (Business logic)
5. Controller Layer (REST API)
6. Exception Handling
7. Testing

## Docker

### Start PostgreSQL

`docker-compose up -d`

### Check if running

`docker-compose ps`

### View logs

`docker-compose logs -f postgres`

## Spring Boot

`./mvnw spring-boot:run`

## Swagger UI

`http://localhost:8080/swagger-ui.html`

## OpenAPI api-docs (Postman Collection)

`http://localhost:8080/api-docs`

### Postman Collection

Import the collection and environment:

- Collection: `src/main/resources/postman/collection.json`
- Environment: `src/main/resources/postman/local_environment.json`
