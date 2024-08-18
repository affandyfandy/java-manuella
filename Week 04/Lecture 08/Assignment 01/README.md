## Assignment 01: SimpleApp
SimpleApp is a demo project using Spring Boot to create a simple web application with one controller.

#### Description
This project consists of two main parts:
1. `SimpleAppApplication`: The main class that runs the Spring Boot application.
2. `SimpleController`: A REST controller that provides a simple endpoint.


## Prerequisites

- Java 21
- Maven

## How to Run the Application

1. **Clone this repository**:
    ```sh
    git clone https://github.com/username/SimpleApp.git
    cd SimpleApp
    ```

2. **Build and run the application**:
    ```sh
    mvn spring-boot:run
    ```

3. **Access the endpoint**:
    Open your browser and go to `http://localhost:8080/api/v1/simple`. You will see the response:
    ```
    This is simple controller.
    ```