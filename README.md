# FullStackJavaApp

## Project Overview
This project consists of three main components:
1. **Front-End**: A web interface.
2. **Back-End**: A Spring Boot application.
3. **JavaFX Application**: A JavaFX desktop BoulderDash game.

### Description
This project is a back-end for the game Boulder-Dash. The back-end can store users, their records, and maps related to the records. It keeps track of everything that is going on in the game, ensuring all data is managed efficiently and securely.

## Directory Structure
```
/FullStackJavaApp/
├── front-end/
├── java1_pav0546_projekt/
│   └── .gitignore
└── back-end-spring/
    └── .gitignore
```


## Getting Started

### Prerequisites
- **Java Development Kit (JDK) 11 or later**: [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- **Apache Maven**: [Download Maven](https://maven.apache.org/download.cgi)
- **Node.js and npm** (for running the web server, if applicable): [Download Node.js](https://nodejs.org/)
- **Visual Studio Code**: [Download VS Code](https://code.visualstudio.com/)

### Front-End
To run the front-end, you need to initialize a web server to serve the HTML file. A common practice is to use a VS Code extension to serve the web page locally.

1. **Open the `front-end` folder in VS Code**:
   ```sh
   cd /FullStackJavaApp/front-end
   code .
   ```
2. **Install the "Live Server" extension in VS Code**:
    - Go to the Extensions view (Ctrl+Shift+X).
    - Search for "Live Server" and install it.
3. **Start the Live Server**:
    - Open the HTML file you want to serve.
    - Right-click on the file and select "Open with Live Server".

### Back-End (Spring Boot)
To run the back-end Spring Boot application:

1. **Navigate to the back-end-spring directory**:
```sh
cd /FullStackJavaApp/back-end-spring
```
2. **Run the Spring Boot application:**:
```sh
mvn spring-boot:run
```
The back-end server will be running on http://localhost:9090.
### JavaFX Application
To run the JavaFX application, follow these steps:

1. **Navigate to the java1_pav0546_projekt directory**:
```sh
cd /FullStackJavaApp/java1_pav0546_projekt
```
2. **Set Up JavaFX**:
- Download JavaFX SDK: [Download JavaFX](https://www.oracle.com/java/technologies/install-javafx-sdk.html)
- Extract the SDK to a location on your system (e.g., C:\javafx-sdk-15.0.1).
3. **Run the JavaFX Application**:
```sh
mvn javafx:run
```

