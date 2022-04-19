package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.Survivor;
import com.assignment.robotapocalypse.repository.SurvivorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    SurvivorRepository survivorRepository;
    private ReportServiceImpl reportService;

    @BeforeEach
    void init() {
        reportService = new ReportServiceImpl(survivorRepository);
    }


    @Test
    void getInfectedSurvivors() {
        // Given
        List<Survivor> survivors = new ArrayList<>();
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .isInfected(true).build();
        Survivor survivorTwo = Survivor.builder()
                .name("Adam")
                .isInfected(false).build();
        survivors.add(survivorOne);
        survivors.add(survivorTwo);
        List<Survivor> expectedResult=new ArrayList<>();
        expectedResult.add(survivorOne);
        when(survivorRepository.findAll()).thenReturn(survivors);

        // When
        List<Survivor> actualResult = reportService.getInfectedSurvivors();

        // Then
        verify(survivorRepository, times(1)).findAll();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getNonInfectedSurvivors() {
        // Given
        List<Survivor> survivors = new ArrayList<>();
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .isInfected(true).build();
        Survivor survivorTwo = Survivor.builder()
                .name("Adam")
                .isInfected(false).build();
        survivors.add(survivorOne);
        survivors.add(survivorTwo);
        List<Survivor> expectedResult=new ArrayList<>();
        expectedResult.add(survivorTwo);
        when(survivorRepository.findAll()).thenReturn(survivors);

        // When
        List<Survivor> actualResult = reportService.getNonInfectedSurvivors();

        // Then
        verify(survivorRepository, times(1)).findAll();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getInfectedPercentage() {
        // Given
        List<Survivor> survivors = new ArrayList<>();
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .isInfected(true).build();
        Survivor survivorTwo = Survivor.builder()
                .name("Adam")
                .isInfected(false).build();
        survivors.add(survivorOne);
        survivors.add(survivorTwo);

        when(survivorRepository.findAll()).thenReturn(survivors);

        // When
        double actualResult = reportService.getInfectedPercentage();

        // Then
        verify(survivorRepository, times(1)).findAll();
        assertEquals(50.00, actualResult);
    }

    @Test
    void getNonInfectedPercentage() {
        // Given
        List<Survivor> survivors = new ArrayList<>();
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .isInfected(true).build();
        Survivor survivorTwo = Survivor.builder()
                .name("Adam")
                .isInfected(false).build();
        survivors.add(survivorOne);
        survivors.add(survivorTwo);

        when(survivorRepository.findAll()).thenReturn(survivors);

        // When
        double actualResult = reportService.getNonInfectedPercentage();

        // Then
        verify(survivorRepository, times(1)).findAll();
        assertEquals(50.00, actualResult);
    }
}