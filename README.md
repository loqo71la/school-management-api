# School Management API
[![codecov](
https://img.shields.io/badge/SchoolManagement-4E69C8?labelColor=4E69C8&amp;logo=Firefox&amp;)](https://school-management.loqo71la.dev)
[![docker](https://img.shields.io/docker/v/loqo71la/school-management-api)](https://hub.docker.com/r/loqo71la/school-management-api)
[![Unit Test](https://github.com/loqo71la/school-management-api/actions/workflows/coverage.yml/badge.svg)](https://github.com/loqo71la/school-management-api/actions/workflows/coverage.yml)
[![codecov](https://codecov.io/gh/loqo71la/school-management-api/branch/main/graph/badge.svg?token=EN4SEZUHOB)](https://codecov.io/gh/loqo71la/school-management-api)

It is a robust and scalable solution for managing classes and students in an educational environment. It allows educators to register and manage classes, students, grades, location, teacher assignments and more. Additionally, it offers advanced security features suhch as authentication and authorization to ensure the protection of sensitive student data.

# For Production
## Prerequisites
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## How to use
The following steps will run a local instance of school management.
1. Clone this repository.
```
git clone https://github.com/loqo71la/school-management-api.git
```
2. Navigate into the project root.
```
cd school-management-api
```
3. Run the `docker-compose` command.
```
docker-compose ud -d
```
FInally point your browser to [http://localhost:8080](http://localhost:8080)

# For Development
## Prerequisites
- [Java 17+](https://adoptium.net/temurin/releases/)
- [PostgreSQL](https://www.postgresql.org/download/)

## How to use
Configuring the database connection on the file `src/main/resources/application.properties`
```
spring.datasource.url=jdbc:postgresql://localhost:5432/sm
spring.datasource.username=postgres
spring.datasource.password=Password123!
```
Running the API
```
./gradlew bootRun
```
or
```
./gradlew clean build
java -jar build/libs/school-management-0.1.3-SNAPSHOT.jar
```
Runnig the set of tests
```
./gradlew clean test
```