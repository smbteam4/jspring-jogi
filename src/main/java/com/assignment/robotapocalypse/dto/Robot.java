package com.assignment.robotapocalypse.dto;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Robot {
    private String model;
    private String serialNumber;
    private String manufacturedDate;
    private String category;
}
