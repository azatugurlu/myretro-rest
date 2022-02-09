FROM maven:3.8.2-jdk-8
WORKDIR /my_retro_rest-master
COPY . .
EXPOSE 8083 
RUN mvn clean install
CMD mvn spring-boot:run