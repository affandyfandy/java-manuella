package com.week8.WebClientDemo.controller;

import com.week8.WebClientDemo.model.Foo;
import com.week8.WebClientDemo.service.FooService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/test/foos")
public class FooController {

    private final FooService fooService;

    public FooController(FooService fooService) {
        this.fooService = fooService;
    }

    @GetMapping("/{id}")
    public Mono<Foo> getFooById(@PathVariable Long id) {
        return fooService.getFooById(id);
    }

    @PostMapping
    public Mono<Foo> createFoo(@RequestBody Foo foo) {
        return fooService.createFoo(foo);
    }

    @PutMapping("/{id}")
    public Mono<Foo> updateFoo(@PathVariable Long id, @RequestBody Foo foo) {
        return fooService.updateFoo(id, foo);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteFoo(@PathVariable Long id) {
        return fooService.deleteFoo(id);
    }

    @GetMapping
    public Flux<Foo> getAllFoos() {
        return fooService.getAllFoos();
    }
}