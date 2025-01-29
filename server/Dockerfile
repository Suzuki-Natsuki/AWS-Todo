# Dockerfile
# $ docker build -t todoapp .
FROM amazoncorretto:21
COPY ./build/libs/toodles-0.0.1-SNAPSHOT.jar ./app.jar
CMD ["java", "-jar", "app.jar"]

#docker run -p 8080:8080 -e AWS_DYNAMODB_ENDPOINT=http://localstack:4566/ --network aws-todo_default todoapp
