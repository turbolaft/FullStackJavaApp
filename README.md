# FullStackJavaApp

## Project Overview
This project consists of three main components:
1. **Front-End**: A web interface.
2. **Back-End**: A Spring Boot application.
3. **JavaFX Application**: A JavaFX desktop BoulderDash game.
4. **PostgreSQL database**: Data source.
5. **Nginx reverse proxy**: Entry point to any request coming into the server

### Description
This project is a back-end for Boulder-dash game written in JavaFX. I'm using microservice to put each service into a Docker container for better isolation. Docker compose connects all the containers together in a default bridge docker network. All the requests coming into the server are first processed by nginx reverse proxy, than based on the domain name they go to the dedicated service. Back-end is written in Spring:Boot with Hibernate as JPA, and authorization using JWT token. Front-end is built upon HTML, Bootstrap, JS. To see the site please go ahead to https://www.turbolaft.com, (might be down because is running on my notebook at home xD)

## Directory Structure
```
/FullStackJavaApp/
├── front-end/
│   └── static/
│   └── Dockerfile
│   └── nginx.conf
├── java1_pav0546_projekt/
│   └── src/
│   └── .gitignore
│   └── pom.xml
├── back-end-spring/
│   └── src/
│   └── Dockerfile
│   └── .gitignore
│   └── pom.xml
├── docker-compose.yml
└── nginx.conf
```

## Getting Started

### Prerequisites
- **Java Development Kit (JDK) 21 or later**: [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- **Apache Maven**: [Download Maven](https://maven.apache.org/download.cgi)
- **Visual Studio Code**: [Download VS Code](https://code.visualstudio.com/)
- **Docker daemon, docker-compose**: [Download Docker](https://docs.docker.com/engine/install/ubuntu/)

### Run application
To run the application U need to:

1. **Run back-end infrastructure**:

```sh
git clone https://github.com/turbolaft/FullStackJavaApp
cd FullStackJavaApp
sudo docker-compose build
sudo docker-compose up
```

> [!WARNING]
> Critical content comes here.
> don't forget to change
> ```yml
> volumes: 
>     - /home/artem/https:/etc/nginx/certs
> ```
> with the actual path to your SSL certificate and public key where **/home/artem/https** directory has files **certificate.pem** and **privkey.pem**, also in **root/nginx.conf** change turbolaft.com, www.turbolaft.com with your actual domains 

2. **JavaFX Application**:
To run the JavaFX application, follow these steps:

- *Navigate to the java1_pav0546_projekt directory*:
```sh
cd /FullStackJavaApp/java1_pav0546_projekt
```
- *Set Up JavaFX*:
Download JavaFX SDK: [Download JavaFX](https://www.oracle.com/java/technologies/install-javafx-sdk.html)
Extract the SDK to a location on your system (e.g., C:\javafx-sdk-15.0.1).
- *Run the JavaFX Application*:
```sh
mvn javafx:run
```

