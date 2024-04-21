package ru.maxima.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long id;

    @NotEmpty(message = "Name should not not be empty")
    @Size(min = 2, max = 50, message = "Name should be min 2 symbols and max 50 symbols")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be correct")
    private String email;

    @Min(value = 0, message = "Age should be min 0 years")
    private Integer age;
}
