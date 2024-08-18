package com.week8.WebClientDemo;

import com.week8.WebClientDemo.controller.FooController;
import com.week8.WebClientDemo.model.Foo;
import com.week8.WebClientDemo.service.FooService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;

@WebFluxTest(FooController.class)
public class FooControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FooService fooService;

    @Test
    public void testCreateFoo() {
        Foo newFoo = new Foo(null, "Test Foo");

        // Mocking the service layer
        Mockito.when(fooService.createFoo(Mockito.any(Foo.class)))
                .thenReturn(Mono.just(newFoo));

        webTestClient.post()
                .uri("/api/test/foos")
                .body(BodyInserters.fromValue(newFoo))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Foo.class)
                .value(foo -> {
                    assert foo.getId() == null;
                    assert foo.getName().equals("Test Foo");
                });
    }

}
