package com.week10.authentication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ApiKey {
    @Id
    private String apiKey;
}
