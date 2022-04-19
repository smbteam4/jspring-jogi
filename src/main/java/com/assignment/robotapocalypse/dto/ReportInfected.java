package com.assignment.robotapocalypse.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "report_infected")
public class ReportInfected {
    @Id
    private String id;
    @NotNull(message = "The infected Survivor's Id is required.")
    private String infectedSurvivorId;
    private LocalDateTime timestamp;
    @NotNull(message = "The reporting Survivor's Id is required.")
    private String reporterId;


}
