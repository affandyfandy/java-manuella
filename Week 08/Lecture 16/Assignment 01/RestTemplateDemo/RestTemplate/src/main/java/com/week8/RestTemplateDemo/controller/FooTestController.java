package com.week8.RestTemplateDemo.controller;

import com.week8.RestTemplateDemo.model.Foo;
import com.week8.RestTemplateDemo.service.FooService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test/foos")
@AllArgsConstructor
public class FooTestController {
    private final FooService fooService;

    @GetMapping("/{id}")
    public ResponseEntity<Foo> getFoo(@PathVariable Long id) {
        Foo foo = fooService.getFoo(id);
        if (foo != null) {
            return new ResponseEntity<>(foo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Foo> createFoo(@RequestBody Foo foo) {
        Foo createdFoo = fooService.createFoo(foo);
        if (createdFoo != null) {
            return new ResponseEntity<>(createdFoo, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create/object")
    public ResponseEntity<Foo> createFooUsingPostForObject(@RequestBody Foo foo) {
        Foo createdFoo = fooService.createFooUsingPostForObject(foo);
        return createdFoo != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdFoo) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/create/location")
    public ResponseEntity<URI> createFooUsingPostForLocation(@RequestBody Foo foo) {
        URI location = fooService.createFooUsingPostForLocation(foo);
        return location != null ? ResponseEntity.status(HttpStatus.CREATED).body(location) : ResponseEntity.badRequest().build();
    }

    @PostMapping("/create/exchange")
    public ResponseEntity<Foo> createFooUsingExchange(@RequestBody Foo foo) {
        Foo createdFoo = fooService.createFooUsingExchange(foo);
        return createdFoo != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdFoo) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Foo> updateFoo(@PathVariable Long id, @RequestBody Foo foo) {
        Foo updatedFoo = fooService.updateFoo(id, foo);
        if (updatedFoo != null) {
            return new ResponseEntity<>(updatedFoo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoo(@PathVariable Long id) {
        boolean deleted = fooService.deleteFoo(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/headers")
    public ResponseEntity<String> getFooHeaders() {
        HttpHeaders headers = fooService.getFooHeaders();
        StringBuilder headerInfo = new StringBuilder("Headers:\n");
        for (String headerName : headers.keySet()) {
            headerInfo.append(headerName).append(": ").append(headers.getFirst(headerName)).append("\n");
        }

        return ResponseEntity.ok(headerInfo.toString());
    }

    @GetMapping("/allowed-methods")
    public ResponseEntity<Set<String>> getAllowedMethods() {
        Set<HttpMethod> allowedMethods = fooService.getAllowedMethods();
        Set<String> allowedMethodsAsString = allowedMethods.stream()
                .map(HttpMethod::name)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(allowedMethodsAsString);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getFooNames() {
        List<String> names = fooService.getFooNamesFromFooList();
        return ResponseEntity.ok(names);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Foo>> getFoosList() {
        List<Foo> foosList = fooService.getFoosList();
        return ResponseEntity.ok(foosList);
    }

    @GetMapping("/array")
    public ResponseEntity<Foo[]> getFooArray() {
        Foo[] foosArray = fooService.getFoosArray();
        return ResponseEntity.ok(foosArray);
    }
}