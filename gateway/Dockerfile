FROM eclipse-temurin:17-jdk-alpine
LABEL maintainer="habib"

# On copie le JAR qui vient d'être créé dans le dossier target
COPY target/gateway-1.0.0-SNAPSHOT.jar gateway.jar

# On lance la Gateway
ENTRYPOINT ["java", "-jar", "gateway.jar"]