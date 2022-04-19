package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.Robot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class RobotConnectorService {
    private final RestTemplate restTemplate;
    private final String url;

    public RobotConnectorService(RestTemplate restTemplate, @Value("${robot.url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    /**
     *
     * @return list of land Robots and Flying Robots
     */
    public Map<String, List<Robot>> getAllRobotCPU() {

        ResponseEntity<Robot[]> response = restTemplate.getForEntity(
                url,
                Robot[].class);

        List<Robot> landRobots = new ArrayList<>();
        List<Robot> flyingRobots = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(response.getBody()))
                .parallel()
                .filter(robot -> robot.getCategory().equals("Land"))
                .forEach(landRobots::add);
        Arrays.stream(response.getBody())
                .parallel()
                .filter(robot -> robot.getCategory().equals("Flying"))
                .forEach(flyingRobots::add);


        Map<String, List<Robot>> responseData = new HashMap<>();
        responseData.put("Land Robots", landRobots);
        responseData.put("Flying Robots", flyingRobots);
        return responseData;


    }
}
