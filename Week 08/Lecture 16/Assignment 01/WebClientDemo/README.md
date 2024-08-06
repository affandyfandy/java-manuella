# WebClient Demo

## Overview
This project consists of two main components: the [FooAPI](FooAPI), a RESTful web service implemented with Spring Boot for managing Foo entities, and the [WebClient](WebClient) application that interacts with the Foo API. The Foo API provides CRUD operations for Foo entities, while the WebClient demonstrates different approaches to interacting with the API, including handling various response formats and processing data.

WebClient is a non-blocking, reactive HTTP client introduced by Spring WebFlux. It offers a modern alternative to the traditional RestTemplate, allowing for more flexible and scalable handling of HTTP requests and responses. WebClient supports asynchronous operations and is designed for working with reactive streams.

In this project, WebClient is utilized to perform operations such as fetching data, creating new resources, updating existing resources, and deleting resources. It is configured to handle different types of HTTP requests and responses, including support for various response formats like arrays and lists. The project demonstrates how to use WebClient to process JSON data and convert it into Java objects, showcasing methods for handling Object[], Foo[], and List<Foo> responses.

The service layer of the project leverages WebClient to interact with the Foo API. It illustrates how to manage different response types, perform data conversions, and handle various scenarios, such as retrieving a list of Foo entities in different formats. This approach provides a clear example of how to use WebClient effectively for interacting with RESTful services in a reactive programming paradigm.

## Technologies Used
- **Spring Boot**: Framework for creating and running Spring-based applications.
- **Spring Web**: Provides functionalities to create RESTful web services.
- **MySQL**: Open-source relational database management system.
- **WebClient**: A reactive and non-blocking HTTP client for making asynchronous requests and handling responses.
- **Webflux**: To obtain Spring Framework’s Reactive Web.
- **Swagger (Springdoc OpenAPI)**: For API documentation.
- **Lombok**: Java library to reduce boilerplate code.

## Foo Service
The Web Client is used in the [FooService.java](WebClient%2Fsrc%2Fmain%2Fjava%2Fcom%2Fweek8%2FWebClientDemo%2Fservice%2FFooService.java) for many functions.
```java
@Service
public class FooService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FooService(WebClient.Builder webClientBuilder, @Value("${foo.api.url}") String fooApiUrl) {
        if (fooApiUrl == null || fooApiUrl.isEmpty()) {
            throw new IllegalArgumentException("Foo API URL is not set");
        }
        this.webClient = webClientBuilder.baseUrl(fooApiUrl).build();
    }

    public Mono<Foo> getFooById(Long id) {
        return webClient.get()
                .uri("/foos/{id}", id)
                .retrieve()
                .bodyToMono(Foo.class);
    }

    public Mono<Foo> createFoo(Foo foo) {
        return webClient.post()
                .uri("/foos")
                .bodyValue(foo)
                .retrieve()
                .bodyToMono(Foo.class);
    }

    public Mono<Foo> updateFoo(Long id, Foo foo) {
        return webClient.put()
                .uri("/foos/{id}", id)
                .bodyValue(foo)
                .retrieve()
                .bodyToMono(Foo.class);
    }

    public Mono<Void> deleteFoo(Long id) {
        return webClient.delete()
                .uri("/foos/{id}", id)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Flux<Foo> getAllFoos() {
        return webClient.get()
                .uri("/foos")
                .retrieve()
                .bodyToFlux(Foo.class);
    }

    public Mono<List<Foo>> getFoosWithObjectArray() {
        return webClient.get()
                .uri("/foos")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Object[].class)
                .map(objects -> Arrays.stream(objects)
                        .map(object -> objectMapper.convertValue(object, Foo.class))
                        .collect(Collectors.toList()));
    }

    public Mono<List<Foo>> getFoosWithArray() {
        return webClient.get()
                .uri("/foos")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Foo[].class)
                .map(Arrays::asList);
    }

    public Mono<List<Foo>> getFoosWithList() {
        return webClient.get()
                .uri("/foos")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Foo>>() {});
    }
}
```

## Project Structures
### WebClient
```cmd
├───java
│   └───com
│       └───week8
│           └───WebClientDemo
│               │   WebClientDemoApplication.java
│               │
│               ├───config
│               │       WebClientConfig.java
│               │
│               ├───controller
│               │       FooController.java
│               │
│               ├───model
│               │       Foo.java
│               │
│               └───service
│                       FooService.java
│
└───resources
    │   application.properties
    │
    ├───static
    └───templates
```

### FooAPI
```cmd
├───java
│   └───com
│       └───week8
│           └───FooAPI
│               │   FooApiApplication.java
│               │
│               ├───config
│               │       FilterConfig.java
│               │       HeaderFilter.java
│               │
│               ├───controller
│               │       FooController.java
│               │
│               ├───model
│               │       Foo.java
│               │
│               └───repository
│                       FooRepository.java
│
└───resources
        application.properties
```

## Running the Application
### Foo API
- Navigate to the `FooAPI` project directory.
- Execute `mvn spring-boot:run` to start the Foo API application.
- The application will be accessible at http://localhost:8080/api/v1/foos

### Web Client
- Navigate to the `WebClient` project directory.
- Execute `mvn spring-boot:run` to start the Foo API application.
- The application will be accessible at http://localhost:8081/api/test/foos

## API Endpoints
When the Foo API is running, we can access the API list at http://localhost:8081/swagger-ui/index.html

| Action                            | Method | URL                                                    | Params/Body                                     |
|-----------------------------------|--------|--------------------------------------------------------|-------------------------------------------------|
| Get Foo by ID                     | GET    | `http://localhost:8080/api/test/foos/{id}`             | Replace `{id}` with Foo ID                     |
| Create Foo                        | POST   | `http://localhost:8080/api/test/foos`                  | Body: JSON representation of `Foo`              |
| Update Foo                        | PUT    | `http://localhost:8080/api/test/foos/{id}`             | Replace `{id}` with Foo ID; Body: JSON of updated `Foo` |
| Delete Foo                        | DELETE | `http://localhost:8080/api/test/foos/{id}`             | Replace `{id}` with Foo ID                     |
| Get All Foos                      | GET    | `http://localhost:8080/api/test/foos`                  | No parameters; Returns a list of all Foos      |
| Get Foos with Object Array        | GET    | `http://localhost:8080/api/test/foos/object-array`     | No parameters; Returns a list of Foos as Object Array |
| Get Foos with Array               | GET    | `http://localhost:8080/api/test/foos/array`            | No parameters; Returns a list of Foos as Array  |
| Get Foos with List                | GET    | `http://localhost:8080/api/test/foos/list`             | No parameters; Returns a list of Foos as List   |

## Screenshots
