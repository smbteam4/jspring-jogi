package com.assignment.robotapocalypse.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "survivor")
public class Survivor {

    @Transient
    public static final String SEQUENCE_NAME = "survivor_sequence";

    @Id
    private String id;

    @NotEmpty(message = "The name is required.")
    @Size(min = 4, max = 100, message = "The length of name must be between 4 and 100 characters.")
    private String name;

    @NotNull(message = "The age cannot be null.")
    private Integer age;

    @NotNull(message = "The gender is required.")
    private String gender;

    @NotNull(message = "The Location  cannot be null.")
    private Location lastKnownLocation;

    private ResourceInventory resource;
    private boolean isInfected;
}
