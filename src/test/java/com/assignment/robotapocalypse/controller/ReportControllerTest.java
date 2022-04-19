package com.assignment.robotapocalypse.controller;

import com.assignment.robotapocalypse.dto.Survivor;
import com.assignment.robotapocalypse.repository.SurvivorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class ReportControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private SurvivorRepository survivorRepository;


    @AfterEach
    public void tearDown() {
        survivorRepository.deleteAll();

    }

    @BeforeEach
    public void setUp() {
        this.mockMvc =
                webAppContextSetup(this.wac).build();
    }



    @Test
    void getInfectedSurvivors() throws Exception {
        // When
        mockMvc.perform(
                        get("/api/report/infectedSurvivors")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getNonInfectedSurvivors() throws Exception {
        // When
        mockMvc.perform(
                        get("/api/report/nonInfectedSurvivors")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getInfectedPercentage() throws Exception {
        // When
        mockMvc.perform(
                        get("/api/report/infectedPercentage")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getNonInfectedPercentage() throws Exception {
        // When
        mockMvc.perform(
                        get("/api/report/nonInfectedPercentage")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void getAllRobotCPU() throws Exception {
        // When
        mockMvc.perform(
                        get("/api/report/getAllRobots")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk()).andReturn();
    }
}