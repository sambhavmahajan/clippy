FROM mcr.microsoft.com/openjdk/jdk:25-ubuntu
WORKDIR /wkdir
COPY app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
