package com.week8.WebClientDemo;

import com.week8.WebClientDemo.model.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebClientDemoApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetAllFoos() {
        // Assuming initially the list is empty
        webTestClient.get()
                .uri("/api/test/foos")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Foo.class)
                .hasSize(0);
    }

    @Test
    public void testCreateFoo() {
        Foo newFoo = new Foo(null, "Test Foo");

        webTestClient.post()
                .uri("/api/test/foos")
                .body(BodyInserters.fromValue(newFoo))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Foo.class)
                .value(foo -> {
                    // Assert that the Foo object has been created properly
                    assert foo.getId() != null;
                    assert foo.getName().equals("Test Foo");
                });
    }

    @Test
    public void testGetFooById() {
        Foo foo = new Foo(1L, "Test Foo");

        // Ensure Foo is created first
        webTestClient.post()
                .uri("/api/test/foos")
                .body(BodyInserters.fromValue(foo))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.get()
                .uri("/api/test/foos/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Foo.class)
                .value(f -> {
                    assert f.getId().equals(1L);
                    assert f.getName().equals("Test Foo");
                });
    }

    @Test
    public void testGetFooByIdNotFound() {
        webTestClient.get()
                .uri("/api/test/foos/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testUpdateFoo() {
        Foo foo = new Foo(1L, "Test Foo");

        // Ensure Foo is created first
        webTestClient.post()
                .uri("/api/test/foos")
                .body(BodyInserters.fromValue(foo))
                .exchange()
                .expectStatus().isCreated();

        Foo updatedFoo = new Foo(1L, "Updated Foo");

        webTestClient.put()
                .uri("/api/test/foos/1")
                .body(BodyInserters.fromValue(updatedFoo))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Foo.class)
                .value(f -> {
                    assert f.getId().equals(1L);
                    assert f.getName().equals("Updated Foo");
                });
    }

    @Test
    public void testUpdateFooNotFound() {
        Foo updatedFoo = new Foo(999L, "Updated Foo");

        webTestClient.put()
                .uri("/api/test/foos/999")
                .body(BodyInserters.fromValue(updatedFoo))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testDeleteFoo() {
        Foo foo = new Foo(1L, "Test Foo");

        // Ensure Foo is created first
        webTestClient.post()
                .uri("/api/test/foos")
                .body(BodyInserters.fromValue(foo))
                .exchange()
                .expectStatus().isCreated();

        webTestClient.delete()
                .uri("/api/test/foos/1")
                .exchange()
                .expectStatus().isNoContent();

        // Verify that the Foo has been deleted
        webTestClient.get()
                .uri("/api/test/foos/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void testDeleteFooNotFound() {
        webTestClient.delete()
                .uri("/api/test/foos/999")
                .exchange()
                .expectStatus().isNotFound();
    }
}