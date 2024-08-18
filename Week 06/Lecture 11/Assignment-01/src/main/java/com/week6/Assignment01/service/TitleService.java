package com.week6.Assignment01.service;

import com.week6.Assignment01.model.Title;
import com.week6.Assignment01.model.TitleId;
import com.week6.Assignment01.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TitleService {
    @Autowired
    private TitleRepository titleRepository;

    public Title saveTitle(Title title) {
        return titleRepository.save(title);
    }

    public Optional<Title> getTitle(TitleId id) {
        return titleRepository.findById(id);
    }

}
