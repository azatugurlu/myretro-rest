FROM openjdk:8 
ADD target/my-retro-api-0.0.1-SNAPSHOT.jar my-retro-api-0.0.1-SNAPSHOT.jar
EXPOSE 8083 
ENTRYPOINT ["java", "-jar", "my-retro-api-0.0.1-SNAPSHOT.jar"]