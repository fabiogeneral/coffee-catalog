# coffee-catalog-api

### Architecture

1. Entity layer (Database mapping)
2. Repository Layer (Data access)
3. Response and Mapper Layer (DTO - Data Transfer Objects)
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
