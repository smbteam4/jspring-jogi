package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.Location;
import com.assignment.robotapocalypse.dto.ReportInfected;
import com.assignment.robotapocalypse.dto.ResourceInventory;
import com.assignment.robotapocalypse.dto.Survivor;

import java.util.List;

public interface SurvivorService {

    void createSurvivor(Survivor survivor);

    void createSurvivorList(List<Survivor> survivor);

    boolean updateInventory(String survivorId, ResourceInventory resource);

    boolean updateILocation(String survivorId, Location location);

    String checkSurvivorIsInfected(ReportInfected reportInfected);

    List<Survivor> getAllSurvivors();
}
