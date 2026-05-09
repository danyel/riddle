# Riddle API

## Environment setup

### Docker

```bash
docker compose -f infrastructure/docker-compose.yml up -d
```

### Java

Version: jdk 25

#### SDK Man

```bash
sdk env install
```

#### Manual

Download and install in system path\
Temurin: https://adoptium.net/en-GB/temurin/releases

### Maven

### SDK Man

```bash
sdk env install
```

#### Manual

Download and install in system path\
Maven: https://archive.apache.org/dist/maven/maven-3/

## Tech stack

Spring boot 4 application\
Postgres

### UI

- Vaadin + hilla

### Backend

- JPA
- MVC

### Test

- test containers

## Build

```bash
mvn clean install
```

## Connection

| Technology | Port |
|:-----------|:----:|
| postgres   | 5433 |
| ui         | 8080 |

## Data

| Technology | Port |
|:-----------|:----:|
| postgres   | 5433 |
| ui         | 8080 |