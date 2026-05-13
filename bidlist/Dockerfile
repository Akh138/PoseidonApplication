FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="habib"
# Vérifie bien que le nom correspond au fichier dans le dossier target
COPY target/bidlist-1.0.0-SNAPSHOT.jar bidlist.jar
ENTRYPOINT ["java", "-jar", "bidlist.jar"]