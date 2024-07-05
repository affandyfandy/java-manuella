package com.example.SimpleApp.controllers;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simple")
@AllArgsConstructor
public class SimpleController {
    @GetMapping
    @ResponseBody
    public String simpleController(){
        return "This is simple controller.";
    }
}