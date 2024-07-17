package com.week6.Assignment01.controller;

import com.week6.Assignment01.model.Title;
import com.week6.Assignment01.model.TitleId;
import com.week6.Assignment01.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/titles")
public class TitleController {

    @Autowired
    private TitleService titleService;

    @PostMapping
    public ResponseEntity<Title> createTitle(@RequestBody Title title) {
        return new ResponseEntity<>(titleService.saveTitle(title), HttpStatus.CREATED);
    }

    @PutMapping("/{empNo}/{title}/{fromDate}")
    public ResponseEntity<Title> updateTitle(@PathVariable int empNo, @PathVariable String title, @PathVariable Date fromDate, @RequestBody Title titleDetails) {
        TitleId titleId = new TitleId(empNo, title, fromDate);
        Optional<Title> existingTitle = titleService.getTitle(titleId);
        if (existingTitle.isPresent()) {
            Title updatedTitle = existingTitle.get();
            updatedTitle.setToDate(titleDetails.getToDate());
            return new ResponseEntity<>(titleService.saveTitle(updatedTitle), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
