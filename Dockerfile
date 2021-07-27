FROM openjdk:8
COPY ./target/linkaja*.jar linkaja.jar
ENTRYPOINT  ["java","-jar","linkaja.jar"]