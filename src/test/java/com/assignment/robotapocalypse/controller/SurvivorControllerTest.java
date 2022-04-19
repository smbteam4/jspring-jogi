package com.assignment.robotapocalypse.controller;

import com.assignment.robotapocalypse.dto.*;
import com.assignment.robotapocalypse.repository.ReportInfectedRepository;
import com.assignment.robotapocalypse.repository.SurvivorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
class SurvivorControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private SurvivorRepository survivorRepository;
    @Autowired
    private ReportInfectedRepository reportInfectedRepository;

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    public void tearDown() {
        survivorRepository.deleteAll();
        reportInfectedRepository.deleteAll();

    }

    @BeforeEach
    public void setUp() {
        this.mockMvc =
                webAppContextSetup(this.wac).build();
    }


    @Test
    void addSurvivor() throws Exception {
        // Given
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        // When
        mockMvc.perform(
                        post("/api/survivor/addSurvivor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(survivorOne)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));

        List<Survivor> actual = survivorRepository.findAll();
        survivorOne.setId(actual.get(0).getId());
        Assertions.assertEquals(survivorOne, actual.get(0));
    }

    @Test
    void addSurvivorList() throws Exception {
        // Given
        List<Survivor> survivors = new ArrayList<>();
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        Survivor survivorTwo = Survivor.builder()
                .name("Helen")
                .age(25)
                .gender("MALE")
                .lastKnownLocation(new Location("43.5", "78"))
                .isInfected(false).build();
        survivors.add(survivorOne);
        survivors.add(survivorTwo);

        // When
        mockMvc.perform(
                        post("/api/survivor/addSurvivorList")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(survivors)))
                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)));

        List<Survivor> actual = survivorRepository.findAll();
        survivorOne.setId(actual.get(0).getId());
        survivorTwo.setId(actual.get(1).getId());
        Assertions.assertEquals(survivors, actual);
    }

    @Test
    void getAllSurvivors() throws Exception {
        // When
        mockMvc.perform(
                        get("/api/survivor/")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                //Then
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    void updateInventory() throws Exception {
        // Given
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        survivorOne = survivorRepository.save(survivorOne);

        ResourceInventory resource = ResourceInventory.builder()
                .water("23 Liter")
                .food("40 Kg")
                .medication("40 Strips")
                .ammunition("50 Rounds").build();

        // When
        mockMvc.perform(
                        patch("/api/survivor/updateInventory?survivorId=" + survivorOne.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(resource)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Survivor inventory Updated")));
    }
    @Test
    void updateInventory_invalidSurvivorId() throws Exception {
        // Given
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        survivorRepository.save(survivorOne);

        ResourceInventory resource = ResourceInventory.builder()
                .water("23 Liter")
                .food("40 Kg")
                .medication("40 Strips")
                .ammunition("50 Rounds").build();

        // When
        mockMvc.perform(
                        patch("/api/survivor/updateInventory?survivorId=1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(resource)))

                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid Survivor ID")));
    }

    @Test
    void updateLastKnownLocation() throws Exception {
        // Given
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();

        survivorOne = survivorRepository.save(survivorOne);

        Location location = Location.builder()
                .longitude("33.33")
                .latitude("12.3").build();

        // When
        mockMvc.perform(
                        patch("/api/survivor/updateLastKnownLocation?survivorId=" + survivorOne.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(location)))

                // Then
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is("Survivor Last Known Location Updated")));
    }

    @Test
    void updateLastKnownLocation_invalidSurvivorID() throws Exception {
        // Given
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();

        survivorOne = survivorRepository.save(survivorOne);

        Location location = Location.builder()
                .longitude("33.33")
                .latitude("12.3").build();

        // When
        mockMvc.perform(
                        patch("/api/survivor/updateLastKnownLocation?survivorId=3")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(location)))

                // Then
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.message", is("Invalid Survivor ID")));
    }
    @Test
    void reportSurvivorIfInfected() throws Exception {
        // Given
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        Survivor survivorTwo = Survivor.builder()
                .name("Adam")
                .age(21)
                .gender("MALE")
                .lastKnownLocation(new Location("43", "53"))
                .isInfected(false).build();

        survivorOne = survivorRepository.save(survivorOne);
        survivorTwo = survivorRepository.save(survivorTwo);

        ReportInfected reportInfected = ReportInfected.builder()
                .infectedSurvivorId(survivorOne.getId())
                .reporterId(survivorTwo.getId()).build();

        // When
        mockMvc.perform(
                        patch("/api/survivor/reportSurvivorIfInfected")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(reportInfected)))

                // Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.message", is(reportInfected.getReporterId() + "'s report has been logged")));
    }
}