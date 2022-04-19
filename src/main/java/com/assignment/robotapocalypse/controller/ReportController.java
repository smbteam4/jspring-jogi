package com.assignment.robotapocalypse.controller;

import com.assignment.robotapocalypse.dto.Response;
import com.assignment.robotapocalypse.dto.Robot;
import com.assignment.robotapocalypse.dto.Survivor;
import com.assignment.robotapocalypse.service.ReportServiceImpl;
import com.assignment.robotapocalypse.service.RobotConnectorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/api/report")
public class ReportController {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    @Autowired
    private ReportServiceImpl reportService;

    @Autowired
    private RobotConnectorService robotConnectorService;

    /**
     * List all infected survivors
     */

    @GetMapping("/infectedSurvivors")
    public List<Survivor> getInfectedSurvivors() {
        return reportService.getInfectedSurvivors();
    }

    /**
     * List all non-infected survivors
     */

    @GetMapping("/nonInfectedSurvivors")
    public List<Survivor> getNonInfectedSurvivors() {
        return reportService.getNonInfectedSurvivors();
    }

    /**
     * Percentage of infected survivors
     */

    @GetMapping("/infectedPercentage")
    public ResponseEntity<Response> getInfectedPercentage() {
        double infectedPercentage = reportService.getInfectedPercentage();
        df.setRoundingMode(RoundingMode.DOWN);
        return new ResponseEntity<>(Response.builder()
                .message("Infected survivors are " + df.format(infectedPercentage) + " percentage.")
                .success(true).build(), HttpStatus.OK);

    }

    /**
     * Percentage of non-infected survivors
     */

    @GetMapping("/nonInfectedPercentage")
    public ResponseEntity<Response> getNonInfectedPercentage() {
        double nonInfectedPercentage = reportService.getNonInfectedPercentage();
        df.setRoundingMode(RoundingMode.DOWN);
        return new ResponseEntity<>(Response.builder()
                .message("Non-Infected survivors are " + df.format(nonInfectedPercentage) + " percentage.")
                .success(true).build(), HttpStatus.OK);
    }

    /**
     * List of all robotCPU's
     */

    @GetMapping("/getAllRobots")
    public Map<String, List<Robot>> getAllRobotCPU() {
        return robotConnectorService.getAllRobotCPU();
    }
}
