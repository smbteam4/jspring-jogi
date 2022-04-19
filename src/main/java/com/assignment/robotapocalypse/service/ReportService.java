package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.Survivor;

import java.util.List;

public interface ReportService {
    List<Survivor> getInfectedSurvivors();

    List<Survivor> getNonInfectedSurvivors();

    double getInfectedPercentage();

    double getNonInfectedPercentage();
}
