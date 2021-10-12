package com.sofkau.biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceDTO {
    private String id;
    private String name;
    private LocalDate loanDate;
    private int quantityAvailable;
    private int quantityBorrowed;
    private String type;
    private String thematic;
    private boolean available;
}

