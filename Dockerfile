FROM eclipse-temurin:17-jdk-alpine as build-env

WORKDIR /source
COPY . .
RUN ./gradlew clean build -x test

FROM eclipse-temurin:17-jre-alpine

WORKDIR /sm-rest-api
COPY --from=build-env /source/build/libs/school-management-0.0.1-SNAPSHOT.jar .
CMD java $JAVA_OPTS -Dserver.port=$PORT -jar school-management-0.0.1-SNAPSHOT.jar