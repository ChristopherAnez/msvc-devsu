FROM openjdk:21-jdk as builder

ARG MSVC_NAME=msvc-cuenta-movimiento

WORKDIR /app/$MSVC_NAME

COPY ./pom.xml /app/$MSVC_NAME
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/

COPY ./src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:21-jdk

ARG MSVC_NAME=msvc-cuenta-movimiento

WORKDIR /app

RUN mkdir ./logs

ARG TARGET_FOLDER=/app/$MSVC_NAME/target

COPY --from=builder $TARGET_FOLDER/$MSVC_NAME-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=8082

ENV PORT $PORT_APP

EXPOSE $PORT

CMD ["java", "-jar", "msvc-cuenta-movimiento-0.0.1-SNAPSHOT.jar"]