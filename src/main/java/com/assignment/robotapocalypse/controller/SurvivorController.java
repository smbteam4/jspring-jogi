package com.assignment.robotapocalypse.controller;

import com.assignment.robotapocalypse.dto.*;
import com.assignment.robotapocalypse.service.RobotConnectorService;
import com.assignment.robotapocalypse.service.SurvivorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(path = "/api/survivor")
public class SurvivorController {
    @Autowired
    private SurvivorServiceImpl survivorService;

    @Autowired
    private RobotConnectorService robotConnectorService;

    /**
     * create/add Survivor
     */

    @PostMapping("/addSurvivor")
    public ResponseEntity<Response> addSurvivor(@Valid @RequestBody Survivor survivor) {
        log.info("Adding a Survivor");
        survivorService.createSurvivor(survivor);
        return new ResponseEntity<>(Response.builder()
                .message("Survivor added")
                .success(true).build(), HttpStatus.CREATED);
    }

    /**
     * create/add a list of Survivors
     */

    @PostMapping("/addSurvivorList")
    public ResponseEntity<Response> addSurvivorList(@Valid @RequestBody List<Survivor> survivors) {
        log.info("Adding a list of Survivor");
        survivorService.createSurvivorList(survivors);
        return new ResponseEntity<>(Response.builder()
                .message("Survivors added")
                .success(true).build(), HttpStatus.CREATED);
    }

    /**
     * get details of All Survivors
     */

    @GetMapping("/")
    public List<Survivor> getAllSurvivors() {
        return survivorService.getAllSurvivors();
    }

    /**
     * survivors resource inventory update
     */

    @PatchMapping("/updateInventory")
    public ResponseEntity<Response> updateInventory(@RequestParam String survivorId,
                                                    @Valid @RequestBody ResourceInventory resource) {
        log.info("Updating inventory of the Survivor {}", survivorId);
        if (survivorService.updateInventory(survivorId, resource)) {
            return new ResponseEntity<>(Response.builder()
                    .message("Survivor inventory Updated")
                    .success(true).build(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(Response.builder()
                    .message("Invalid Survivor ID")
                    .success(false).build(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * survivors last known location Update
     */

    @PatchMapping("/updateLastKnownLocation")
    public ResponseEntity<Response> updateLastKnownLocation(@RequestParam String survivorId,
                                                            @Valid @RequestBody Location location) {
        log.info("Updating Location of the Survivor {}", survivorId);
        if (survivorService.updateILocation(survivorId, location)) {
            return new ResponseEntity<>(Response.builder()
                    .message("Survivor Last Known Location Updated")
                    .success(true).build(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(Response.builder()
                    .message("Invalid Survivor ID")
                    .success(false).build(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * report if survivors is Infected
     */

    @PatchMapping("/reportSurvivorIfInfected")
    public ResponseEntity<Response> reportSurvivorIfInfected(@Valid @RequestBody ReportInfected reportInfected) {
        String message = survivorService.checkSurvivorIsInfected(reportInfected);
        return new ResponseEntity<>(Response.builder()
                .message(message)
                .success(true).build(), HttpStatus.OK);
    }
}
