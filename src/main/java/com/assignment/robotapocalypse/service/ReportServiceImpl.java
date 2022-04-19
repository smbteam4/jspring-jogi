package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.Survivor;
import com.assignment.robotapocalypse.repository.SurvivorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    private final SurvivorRepository survivorRepository;

    public ReportServiceImpl(SurvivorRepository survivorRepository) {
        this.survivorRepository = survivorRepository;
    }

    /**
     *
     * @return list of Infected Survivors
     */
    @Override
    public List<Survivor> getInfectedSurvivors() {
        List<Survivor> survivors = survivorRepository.findAll();
        return survivors.stream().filter(Survivor::isInfected).collect(Collectors.toList());
    }

    /**
     *
     * @return list of non-Infected Survivors
     */

    @Override
    public List<Survivor> getNonInfectedSurvivors() {
        List<Survivor> survivors = survivorRepository.findAll();
        List<Survivor> nonInfected = survivors.stream().filter(survivor -> !survivor.isInfected()).collect(Collectors.toList());
        return nonInfected;
    }


    /**
     *
     * @return Percentage of infected survivors
     */
    @Override
    public double getInfectedPercentage() {
        List<Survivor> survivors = survivorRepository.findAll();
        List<Survivor> infected = survivors.stream().filter(Survivor::isInfected).collect(Collectors.toList());
        return (float) (infected.size() * 100) / survivors.size();
    }

    /**
     *
     * @return Percentage of non-infected survivors
     */

    @Override
    public double getNonInfectedPercentage() {
        List<Survivor> survivors = survivorRepository.findAll();
        List<Survivor> nonInfected = survivors.stream().filter(survivor -> !survivor.isInfected()).collect(Collectors.toList());
        return (float) (nonInfected.size() * 100) / survivors.size();
    }
}
