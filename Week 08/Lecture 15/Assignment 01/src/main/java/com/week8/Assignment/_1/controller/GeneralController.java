package com.week8.Assignment._1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GeneralController {
    @GetMapping("/public")
    @ResponseBody
    public String publicResource() {
        return "This is a public resource.";
    }
    @PostMapping("/upload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        // File processing...
        return "File " + file.getOriginalFilename() + " uploaded successfully!";
    }
}
