package com.rootapp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    private long id;

    @NotBlank(message = "category title must not be empty")
    @Size(min = 4, max = 50, message = "title must be min of 4 chars and max of 50 chars")
    private String title;

    @NotBlank(message = "category description must not be empty")
    @Size(min = 10, max = 1000, message = "description must be min of 10 chars and max of 1000 chars")
    private String description;

}
