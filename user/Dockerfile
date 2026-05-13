FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="habib"
# Vérifie bien que le nom correspond au fichier dans le dossier target
COPY target/user-1.0.0-SNAPSHOT.jar user.jar
ENTRYPOINT ["java", "-jar", "user.jar"]