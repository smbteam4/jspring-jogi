package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.*;
import com.assignment.robotapocalypse.exception.InvalidGenderException;
import com.assignment.robotapocalypse.repository.ReportInfectedRepository;
import com.assignment.robotapocalypse.repository.SurvivorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SurvivorServiceImpl implements SurvivorService {

    private final SurvivorRepository survivorRepository;
    private final ReportInfectedRepository reportInfectedRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final static Integer REPORTED_THRESHOLD = 3;

    @Autowired
    public SurvivorServiceImpl(SurvivorRepository survivorRepository,
                               ReportInfectedRepository reportInfectedRepository,
                               SequenceGeneratorService sequenceGeneratorService) {
        this.survivorRepository = survivorRepository;
        this.reportInfectedRepository = reportInfectedRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;

    }

    /**
     * Saves survivor in db
     *
     * @param survivor survivor details
     */
    @Override
    public void createSurvivor(Survivor survivor) {
        survivor.setId(sequenceGeneratorService.getSequenceNumber(Survivor.SEQUENCE_NAME));
        if (!(survivor.getGender().equals("OTHER") || survivor.getGender().equals("FEMALE") || survivor.getGender().equals("MALE"))) {
            throw new InvalidGenderException("Invalid Gender. Gender should be either MALE, FEMALE or OTHER.");
        }
        survivorRepository.save(survivor);
    }

    /**
     * Saves a list of survivors in db
     *
     * @param survivors survivors details
     */
    @Override
    public void createSurvivorList(List<Survivor> survivors) {
        survivors.forEach(survivor ->
                survivor.setId(sequenceGeneratorService.getSequenceNumber(Survivor.SEQUENCE_NAME)));
        survivors.forEach(survivor -> {
            if (!(survivor.getGender().equals("OTHER") || survivor.getGender().equals("FEMALE") || survivor.getGender().equals("MALE"))) {
                throw new InvalidGenderException("Invalid Gender. Gender should be either MALE, FEMALE or OTHER.");
            }
        });


        survivorRepository.saveAll(survivors);
    }

    /**
     * Updates survivors resource inventory
     *
     * @param survivorId survivor's Id
     * @param resource   updated resource inventory
     * @return true if inventory is updated
     */
    @Override
    public boolean updateInventory(String survivorId, ResourceInventory resource) {
        Optional<Survivor> survivorById = survivorRepository.findById(survivorId);

        if (!survivorById.isPresent()) {
            return false;
        }
        Survivor survivorToUpdate = survivorById.get();
        survivorToUpdate.setResource(new ResourceInventory(resource.getWater(),
                resource.getFood(),
                resource.getMedication(),
                resource.getAmmunition()));
        survivorRepository.save(survivorToUpdate);
        return true;
    }

    /**
     * Updates survivors last known location
     *
     * @param survivorId survivor's Id
     * @param location   new location of survivor
     * @return true if new location is updated
     */
    @Override
    public boolean updateILocation(String survivorId, Location location) {
        Optional<Survivor> survivorById = survivorRepository.findById(survivorId);

        if (!survivorById.isPresent()) {
            return false;
        }
        Survivor survivorToUpdate = survivorById.get();
        survivorToUpdate.setLastKnownLocation(new Location(location.getLatitude(),
                location.getLongitude()));
        survivorRepository.save(survivorToUpdate);
        return true;
    }

    /**
     * update survivors infected status
     *
     * @param reportInfected infected survivors and reported survivors details
     * @return status message
     */
    @Override
    public String checkSurvivorIsInfected(ReportInfected reportInfected) {
        Optional<Survivor> survivorById = survivorRepository.findById(reportInfected.getInfectedSurvivorId());
        if (survivorById.isPresent() && survivorById.get().isInfected()) {
            return survivorById.get().getName() + ", survivorId: " + survivorById.get().getId() + ", is already flagged as Infected";
        } else if (survivorById.isPresent()) {
            if (reportInfectedRepository.findByInfectedSurvivorId(reportInfected.getInfectedSurvivorId()).size() < REPORTED_THRESHOLD) {
                reportInfected.setTimestamp(LocalDateTime.now());
                reportInfectedRepository.save(reportInfected);
            }
            if (reportInfectedRepository.findByInfectedSurvivorId(reportInfected.getInfectedSurvivorId()).size() >= REPORTED_THRESHOLD) {
                Survivor survivorToUpdate = survivorById.get();
                survivorToUpdate.setInfected(true);
                survivorRepository.save(survivorToUpdate);
                return survivorToUpdate.getName() + ", survivorId: " + survivorToUpdate.getId() + ", is flagged as Infected";
            }
            return reportInfected.getReporterId() + "'s report has been logged";
        } else
            return "Invalid Survivor Id " + reportInfected.getInfectedSurvivorId() + " reported";

    }

    /**
     * @return list if all survivors
     */
    @Override
    public List<Survivor> getAllSurvivors() {
        return survivorRepository.findAll();
    }


}
