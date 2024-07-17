package com.week6.Assignment01.repository;

import com.week6.Assignment01.model.Title;
import com.week6.Assignment01.model.TitleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, TitleId> {
}
