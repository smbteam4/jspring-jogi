package com.assignment.robotapocalypse.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ResourceInventory {

    @NotNull(message = "The water cannot be null.")
    private String water;
    @NotNull(message = "The food cannot be null.")
    private String food;
    @NotNull(message = "The medication cannot be null.")
    private String medication;
    @NotNull(message = "The ammunition cannot be null.")
    private String ammunition;

}
