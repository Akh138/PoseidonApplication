FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="habib"

# On copie le JAR de l'interface (vérifie bien le nom dans target/)
COPY target/poseidon-ui-1.0.0-SNAPSHOT.jar poseidon-ui.jar

# On lance le portail UI
ENTRYPOINT ["java", "-jar", "poseidon-ui.jar"]