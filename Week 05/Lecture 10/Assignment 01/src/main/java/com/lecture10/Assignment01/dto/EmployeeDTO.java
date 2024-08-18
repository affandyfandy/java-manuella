package com.lecture10.Assignment01.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class EmployeeDTO {
    private UUID id;

    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotNull(message = "Date of birth cannot be null")
    private LocalDate dob;

    @NotNull(message = "Address cannot be null")
    private String address;

    @NotNull(message = "Department cannot be null")
    private String department;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Phone cannot be null")
    @Pattern(regexp = "^(\\+62|08)[0-9]{8,}$", message = "Phone number must be valid Indonesian format")
    private String phone;

}
