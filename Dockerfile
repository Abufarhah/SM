FROM openjdk:8
ADD build/libs/SM-0.0.1.jar SM-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","SM-0.0.1.jar"]