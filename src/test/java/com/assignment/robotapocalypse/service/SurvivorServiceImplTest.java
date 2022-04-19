package com.assignment.robotapocalypse.service;

import com.assignment.robotapocalypse.dto.*;
import com.assignment.robotapocalypse.repository.ReportInfectedRepository;
import com.assignment.robotapocalypse.repository.SurvivorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SurvivorServiceImplTest {
    @Mock
    SurvivorRepository survivorRepository;
    @Mock
    ReportInfectedRepository reportInfectedRepository;
    @Mock
    SequenceGeneratorService sequenceGeneratorService;
    private SurvivorServiceImpl survivorService;

    @BeforeEach
    void init() {
        survivorService = new SurvivorServiceImpl(survivorRepository, reportInfectedRepository, sequenceGeneratorService);
    }

    @Test
    void createSurvivor() {
        // Given
        Survivor survivorOne = Survivor.builder()
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();

        when(survivorRepository.save(ArgumentMatchers.eq(survivorOne))).thenReturn(survivorOne);

        // When
        survivorService.createSurvivor(survivorOne);

        // Then
        ArgumentCaptor<Survivor> userArgumentCaptor = ArgumentCaptor.forClass(Survivor.class);
        verify(survivorRepository, times(1)).save(userArgumentCaptor.capture());
        Survivor actual = userArgumentCaptor.getValue();
        assertEquals(survivorOne, actual);
    }

    @Test
    void createSurvivorList() {
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

        when(survivorRepository.saveAll(ArgumentMatchers.eq(survivors))).thenReturn(survivors);

        // When
        survivorService.createSurvivorList(survivors);

        // Then
        ArgumentCaptor<List<Survivor>> userArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(survivorRepository, times(1)).saveAll(userArgumentCaptor.capture());
        List<Survivor> actual = userArgumentCaptor.getValue();
        assertEquals(survivors, actual);


    }

    @Test
    void updateInventory_success() {
        // Given
        String survivorId = "1";
        Survivor survivorOne = Survivor.builder()
                .id("1")
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        ResourceInventory resource = ResourceInventory.builder()
                .water("23 Liter")
                .food("40 Kg")
                .medication("40 Strips")
                .ammunition("50 Rounds").build();


        when(survivorRepository.findById(anyString())).thenReturn(Optional.of(survivorOne));
        when(survivorRepository.save(any(Survivor.class))).thenReturn(survivorOne);

        // When
        boolean actual = survivorService.updateInventory(survivorId, resource);

        // Then
        verify(survivorRepository, times(1)).save(any(Survivor.class));
        verify(survivorRepository, times(1)).findById(anyString());
        assertTrue(actual);

    }

    @Test
    void updateInventory_invalidSurvivorId() {
        // Given
        String survivorId = "6";

        ResourceInventory resource = ResourceInventory.builder()
                .water("23 Liter")
                .food("40 Kg")
                .medication("40 Strips")
                .ammunition("50 Rounds").build();


        when(survivorRepository.findById(anyString())).thenReturn(Optional.empty());


        // When
        boolean actual = survivorService.updateInventory(survivorId, resource);

        // Then
        verify(survivorRepository, times(0)).save(any(Survivor.class));
        verify(survivorRepository, times(1)).findById(anyString());
        assertFalse(actual);

    }

    @Test
    void updateILocation_success() {
        // Given
        String survivorId = "1";
        Survivor survivorOne = Survivor.builder()
                .id("1")
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        Location location = Location.builder()
                .latitude("23.87")
                .longitude("40.21")
                .build();

        when(survivorRepository.findById(anyString())).thenReturn(Optional.of(survivorOne));
        when(survivorRepository.save(any(Survivor.class))).thenReturn(survivorOne);

        // When
        boolean actual = survivorService.updateILocation(survivorId, location);

        // Then
        verify(survivorRepository, times(1)).save(any(Survivor.class));
        verify(survivorRepository, times(1)).findById(anyString());
        assertTrue(actual);
    }

    @Test
    void updateILocation_invalidSurvivorId() {
        // Given
        String survivorId = "3";

        Location location = Location.builder()
                .latitude("23.87")
                .longitude("40.21")
                .build();

        when(survivorRepository.findById(anyString())).thenReturn(Optional.empty());


        // When
        boolean actual = survivorService.updateILocation(survivorId, location);

        // Then
        verify(survivorRepository, times(0)).save(any(Survivor.class));
        verify(survivorRepository, times(1)).findById(anyString());
        assertFalse(actual);
    }

    @Test
    void checkSurvivorIsInfected_success() {
        // Given
        Survivor survivorOne = Survivor.builder()
                .id("4")
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        ReportInfected reportInfected = ReportInfected.builder()
                .infectedSurvivorId("4")
                .reporterId("2").build();
        List<ReportInfected> reportList=new ArrayList<>();
        reportList.add(reportInfected);
        reportList.add(new ReportInfected());
        reportList.add(new ReportInfected());
        when(survivorRepository.findById(anyString())).thenReturn(Optional.of(survivorOne));
        when(reportInfectedRepository.findByInfectedSurvivorId(anyString())).thenReturn(reportList);
        when(survivorRepository.save(any(Survivor.class))).thenReturn(survivorOne);
        String expected = survivorOne.getName() + ", survivorId: " + survivorOne.getId() + ", is flagged as Infected";

        // When
        String message = survivorService.checkSurvivorIsInfected(reportInfected);

        // Then
        verify(reportInfectedRepository, times(0)).save(any(ReportInfected.class));
        verify(survivorRepository, times(1)).save(any(Survivor.class));
        assertEquals(expected, message);
    }

    @Test
    void checkSurvivorIsInfected_loggingReported() {
        // Given
        Survivor survivorOne = Survivor.builder()
                .id("4")
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(false).build();
        ReportInfected reportInfected = ReportInfected.builder()
                .infectedSurvivorId("4")
                .reporterId("2").build();
        List<ReportInfected> reportList=new ArrayList<>();
        reportList.add(reportInfected);
        when(survivorRepository.findById(anyString())).thenReturn(Optional.of(survivorOne));
        when(reportInfectedRepository.findByInfectedSurvivorId(anyString())).thenReturn(reportList);
        when(reportInfectedRepository.save(any(ReportInfected.class))).thenReturn(reportInfected);
        String expected = reportInfected.getReporterId() + "'s report has been logged";

        // When
        String message = survivorService.checkSurvivorIsInfected(reportInfected);

        // Then
        verify(reportInfectedRepository, times(1)).save(any(ReportInfected.class));
        verify(survivorRepository, times(0)).save(any(Survivor.class));
        assertEquals(expected, message);
    }


    @Test
    void checkSurvivorIsInfected_true() {
        // Given
        Survivor survivorOne = Survivor.builder()
                .id("4")
                .name("Jack")
                .age(23)
                .gender("MALE")
                .lastKnownLocation(new Location("123", "214"))
                .isInfected(true).build();
        ReportInfected reportInfected = ReportInfected.builder()
                .infectedSurvivorId("4")
                .reporterId("2").build();
        when(survivorRepository.findById(anyString())).thenReturn(Optional.of(survivorOne));
        String expected = survivorOne.getName() + ", survivorId: " + survivorOne.getId() + ", is already flagged as Infected";

        // When
        String message = survivorService.checkSurvivorIsInfected(reportInfected);

        // Then
        verify(reportInfectedRepository, times(0)).save(any(ReportInfected.class));
        verify(survivorRepository, times(0)).save(any(Survivor.class));
        assertEquals(expected, message);
    }


    @Test
    void checkSurvivorIsInfected_invalidSurvivorId() {
        // Given
        ReportInfected reportInfected = ReportInfected.builder()
                .infectedSurvivorId("4")
                .reporterId("2").build();
        when(survivorRepository.findById(anyString())).thenReturn(Optional.empty());
        String expected = "Invalid Survivor Id " + reportInfected.getInfectedSurvivorId() + " reported";

        // When
        String message = survivorService.checkSurvivorIsInfected(reportInfected);

        // Then
        verify(reportInfectedRepository, times(0)).save(any(ReportInfected.class));
        verify(survivorRepository, times(0)).save(any(Survivor.class));
        assertEquals(expected, message);
    }
    @Test
    void getAllSurvivors() {
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
        List<Survivor> actualResult = survivorService.getAllSurvivors();

        // Then
        verify(survivorRepository, times(1)).findAll();
        assertEquals(survivors, actualResult);
    }
}