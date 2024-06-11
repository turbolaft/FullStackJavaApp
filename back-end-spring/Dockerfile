FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app
COPY target/back-end-spring-0.0.1.jar /app

RUN apt-get update && apt-get install -y curl \
    && curl -o /wait-for-it.sh https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh \
    && chmod +x /wait-for-it.sh \
    && apt-get remove -y curl && apt-get autoremove -y

# Run the application
CMD ["/wait-for-it.sh", "db:5432", "--", "java", "-jar", "back-end-spring-0.0.1.jar"]

# Expose the port
EXPOSE 9090