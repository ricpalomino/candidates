package com.seek.candidates.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.seek.candidates.domain.model.Candidate;

@DataJpaTest
public class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    private CandidateJpaEntity candidateEntity;

    @BeforeEach
    void setUp() {
        candidateEntity = new CandidateJpaEntity();
        candidateEntity.setId(1L);
        candidateEntity.setName("Richard Palomino");
        candidateEntity.setEmail("rpalomino@gmail.com");
        candidateEntity.setGender("Male");
        candidateEntity.setCountry("PERU");
        candidateEntity.setCurrency("PEN");
        candidateEntity.setExpectedSalary(new BigDecimal(80000));
        candidateEntity.setPositionApplied("Software Engineer");
        candidateEntity.setCreatedAt(LocalDateTime.now());
        candidateEntity.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testSaveShouldPersistCandidate() {
        CandidateJpaEntity savedCandidate = candidateRepository.save(candidateEntity);
        assertNotNull(savedCandidate.getId());
        assertEquals("Richard Palomino", savedCandidate.getName());
    }

    @Test
    void testFindByIdShouldReturnCandidateWhenExists() {
        // Arrange
        CandidateJpaEntity savedCandidate = candidateRepository.save(candidateEntity);

        // Act
        Optional<CandidateJpaEntity> foundCandidate = candidateRepository.findById(savedCandidate.getId());

        // Assert
        assertTrue(foundCandidate.isPresent());
        assertEquals("Richard Palomino", foundCandidate.get().getName());
    }

    @Test
    void testToDomain() {

        Candidate candidate = candidateRepository.toDomain(candidateEntity);

        assertEquals(candidateEntity.getId(), candidate.getId());
        assertEquals(candidateEntity.getName(), candidate.getName());
        assertEquals(candidateEntity.getEmail(), candidate.getEmail());
        assertEquals(candidateEntity.getGender(), candidate.getGender());
        assertEquals(candidateEntity.getCountry(), candidate.getCountry());
        assertEquals(candidateEntity.getCurrency(), candidate.getCurrency());
        assertEquals(candidateEntity.getExpectedSalary(), candidate.getExpectedSalary());
        assertEquals(candidateEntity.getPositionApplied(), candidate.getPositionApplied());
        assertNotNull(candidate.getCreatedAt());
        assertNotNull(candidate.getUpdatedAt());

    }

    @Test
    void testToDomainList() {
        List<CandidateJpaEntity> entities = List.of(candidateEntity);
        List<Candidate> candidates = candidateRepository.toDomainList(entities);

        assertEquals(1, candidates.size());
        assertEquals(candidateEntity.getId(), candidates.get(0).getId());
        assertEquals(candidateEntity.getName(), candidates.get(0).getName());
    }

    @Test
    void testToEntity() {
        Candidate candidate = new Candidate(
                1L,
                "Richard Palomino",
                "rpalomino@gmail.com",
                "Male",
                "PERU",
                "PEN",
                new BigDecimal(80000),
                "Software Engineer",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        CandidateJpaEntity entity = candidateRepository.toEntity(candidate);

        assertEquals(candidate.getId(), entity.getId());
        assertEquals(candidate.getName(), entity.getName());
        assertEquals(candidate.getEmail(), entity.getEmail());
        assertEquals(candidate.getGender(), entity.getGender());
        assertEquals(candidate.getCountry(), entity.getCountry());
        assertEquals(candidate.getCurrency(), entity.getCurrency());
        assertEquals(candidate.getExpectedSalary(), entity.getExpectedSalary());
        assertEquals(candidate.getPositionApplied(), entity.getPositionApplied());
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getUpdatedAt());
    }
}
