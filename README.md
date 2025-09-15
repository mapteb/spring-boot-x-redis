# spring-boot-x-redis

A Spring Boot helloworld style web application that saves name/value pairs to an external Redis DB. Java 17, Gradle and a docker container for Redis are used.

## Features

- Store and retrieve key-value pairs in Redis
- Set TTL (Time To Live) for keys
- Check key existence
- Delete keys
- Get remaining TTL for keys
- RESTful API endpoints

## Usage

### 1. Start Redis using Podman

```bash
podman run -d --name my-redis-server -p 6379:6379 redis:latest

```

### 2. Build and Run the Spring Boot Application

```bash
# Build the application
./gradlew build

# Run the application
./gradlew bootRun

```

## API Endpoints

### Store a Key-Value Pair
```bash
POST /api/redis/set?key=mykey&value=myvalue

POST /api/redis/set?key=mykey&value=myvalue&ttl=300  # with TTL in seconds

curl -X POST -H "Content-Type: application/json" "http://localhost:8080/api/redis/set?key=foo&value=bar" -v

```

### Retrieve a Value
```bash
GET /api/redis/get/{key}

curl -X GET "http://localhost:8080/api/redis/get/foo" -v

Which returns: {"exists":true,"value":"bar","key":"foo"}

```

### Delete a Key
```bash
DELETE /api/redis/delete/{key}
```

### Check if Key Exists
```bash
GET /api/redis/exists/{key}
```

### Set Expiration for a Key
```bash
POST /api/redis/expire/{key}?seconds=300
```

### Get Time to Live (TTL)
```bash
GET /api/redis/ttl/{key}
```

## Example Usage

```bash
# Store a value
curl -X POST "http://localhost:8080/api/redis/set?key=username&value=john_doe"

# Retrieve the value
curl -X GET "http://localhost:8080/api/redis/get/username"

# Store with TTL (expires in 60 seconds)
curl -X POST "http://localhost:8080/api/redis/set?key=session&value=abc123&ttl=60"

# Check if key exists
curl -X GET "http://localhost:8080/api/redis/exists/username"

# Delete a key
curl -X DELETE "http://localhost:8080/api/redis/delete/username"
```
