package com.assignment.robotapocalypse.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Location {
    @NotNull(message = "The latitude is required.")
    private String latitude;
    @NotNull(message = "The longitude is required.")
    private String longitude;
}
