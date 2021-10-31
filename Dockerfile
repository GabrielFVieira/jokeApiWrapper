FROM openjdk:8
ADD target/jokeApiWrapper.jar jokeApiWrapper.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "jokeApiWrapper.jar"]