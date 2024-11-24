FROM amazoncorretto:17-alpine-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY database/db-init.sql /docker-entrypoint-initdb.d
VOLUME /var/lib/mysql
ENTRYPOINT ["java", "-Dspring.profiles.active=deploy", "-jar", "app.jar"]