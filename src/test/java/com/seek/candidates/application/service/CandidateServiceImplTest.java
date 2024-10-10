package com.seek.candidates.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.seek.candidates.domain.model.Candidate;
import com.seek.candidates.infrastructure.repository.CandidateJpaEntity;
import com.seek.candidates.infrastructure.repository.CandidateRepository;

public class CandidateServiceImplTest {

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    private CandidateJpaEntity candidateEntity;
    private Candidate candidate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        candidateEntity = new CandidateJpaEntity(1L, "Richard Palomino", "rpalomino@gmail.com", 
                "Male", "PERU", "PEN", BigDecimal.valueOf(50000), "Software Engineer", null, null);
        candidate = new Candidate(1L, "Richard Palomino", "rpalomino@gmail.com", 
                "Male", "PERU", "PEN", BigDecimal.valueOf(50000), "Software Engineer", null, null);
    }

    @Test
    void testGetAllCandidatesShouldReturnListOfCandidates() {
        when(candidateRepository.findAll()).thenReturn(List.of(candidateEntity));
        when(candidateRepository.toDomainList(any())).thenReturn(List.of(candidate));
        
        List<Candidate> result = candidateService.getAllCandidates();

        assertEquals(1, result.size());
        assertEquals("Richard Palomino", result.get(0).getName());
    }

    @Test
    void testGetCandidateByIdShouldReturnCandidateWhenCandidateExists() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidateEntity));
        when(candidateRepository.toDomain(any())).thenReturn(candidate);

        Optional<Candidate> result = candidateService.getCandidateById(1L);

        assertTrue(result.isPresent());
        assertEquals("Richard Palomino", result.get().getName());
    }

    @Test
    void testGetCandidateByIdShouldReturnEmptyWhenNotFound() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Candidate> result = candidateService.getCandidateById(1L);
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateCandidateShouldReturnCreatedCandidate() {
        when(candidateRepository.toEntity(candidate)).thenReturn(candidateEntity);
        when(candidateRepository.save(candidateEntity)).thenReturn(candidateEntity);
        when(candidateRepository.toDomain(candidateEntity)).thenReturn(candidate);

        Candidate result = candidateService.createCandidate(candidate);

        assertEquals("Richard Palomino", result.getName());
        verify(candidateRepository, times(1)).save(candidateEntity);
    }

    @Test
    void testDeleteCandidateShouldCallRepositoryDelete() {
        when(candidateRepository.existsById(1L)).thenReturn(true);
        candidateService.deleteCandidate(1L);
        verify(candidateRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdateCandidateShouldUpdateAndReturnUpdatedCandidate() {
        when(candidateRepository.findById(1L)).thenReturn(Optional.of(candidateEntity));
        when(candidateRepository.toEntity(any())).thenReturn(candidateEntity);
        candidateEntity.setName("Richard Felix");
        candidate.setName("Richard Felix");
        when(candidateRepository.save(candidateEntity)).thenReturn(candidateEntity);
        when(candidateRepository.toDomain(candidateEntity)).thenReturn(candidate);



        Candidate result = candidateService.updateCandidate(1L, candidate);

        assertEquals("Richard Felix", result.getName());
        verify(candidateRepository, times(1)).save(candidateEntity);
    }

}
