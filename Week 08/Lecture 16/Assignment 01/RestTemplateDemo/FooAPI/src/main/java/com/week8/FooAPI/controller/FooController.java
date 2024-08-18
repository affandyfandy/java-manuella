package com.week8.FooAPI.controller;

import com.week8.FooAPI.model.Foo;
import com.week8.FooAPI.repository.FooRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/foos")
@AllArgsConstructor
public class FooController {
    private final FooRepository fooRepository;

    @GetMapping
    public ResponseEntity<List<Foo>> getAllFoos() {
        List<Foo> foos = fooRepository.findAll();
        return new ResponseEntity<>(foos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Foo> getFoo(@PathVariable Long id) {
        Optional<Foo> fooOptional = fooRepository.findById(id);
        return fooOptional.map(foo -> new ResponseEntity<>(foo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Foo> createFoo(@RequestBody Foo foo) {
        Foo savedFoo = fooRepository.save(foo);
        return new ResponseEntity<>(savedFoo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Foo> updateFoo(@PathVariable Long id, @RequestBody Foo updatedFoo) {
        if (fooRepository.existsById(id)) {
            updatedFoo.setId(id);
            Foo savedFoo = fooRepository.save(updatedFoo);
            return new ResponseEntity<>(savedFoo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFoo(@PathVariable Long id) {
        if (fooRepository.existsById(id)) {
            fooRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/array")
    public ResponseEntity<Foo[]> getFoosArray() {
        List<Foo> foos = fooRepository.findAll();
        Foo[] fooArray = foos.toArray(new Foo[0]);
        return new ResponseEntity<>(fooArray, HttpStatus.OK);
    }

    @GetMapping("/names")
    public ResponseEntity<List<String>> getFooNames() {
        List<Foo> foos = fooRepository.findAll();
        List<String> names = foos.stream()
                .map(Foo::getName)
                .collect(Collectors.toList());
        return new ResponseEntity<>(names, HttpStatus.OK);
    }
}
