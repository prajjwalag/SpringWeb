package com.prajjwal.SpringWebDemo.dto;

import com.prajjwal.SpringWebDemo.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Name is missing")
    @Size(min=3, max=10, message = "Name should be of length 3 to 10")
    private String name;

    @NotBlank(message = "Email is missing")
    @Email(message = "Email is not correct")
    private String email;

    @NotNull(message = "Please provide age")
    @Max(value = 80, message = "Employee's Age cannot be greater than 80")
    @Min(value = 18, message = "Employee's Age cannot be less than 18.")
    private Integer age;

    @NotBlank(message = "Please enter correct role of the user.")
    @EmployeeRoleValidation
    private String role;

    @NotNull(message = "Salary cannot be null")
    @Positive(message = "Salary of Employee should be positive")
    @Digits(integer =6, fraction = 2, message = "The salary should be less than 10 lakh and should have 2 decimal points only")
    private Integer salary;


    @PastOrPresent(message = "Date cannot be future")
    private LocalDate dateOfJoining;

    @AssertTrue(message = "Employee should be active")
    private Boolean isActive;
}
