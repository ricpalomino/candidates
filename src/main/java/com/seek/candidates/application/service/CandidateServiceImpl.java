package com.seek.candidates.application.service;

import org.springframework.stereotype.Service;

import com.seek.candidates.domain.model.Candidate;
import com.seek.candidates.infrastructure.repository.CandidateRepository;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.toDomainList(candidateRepository.findAll());
    }

    @Override
    public Optional<Candidate> getCandidateById(Long id) {
        return candidateRepository.findById(id).map(candidateRepository::toDomain);
    }

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.toDomain(candidateRepository.save(candidateRepository.toEntity(candidate)));
    }

    @Override
    public void deleteCandidate(Long id) {
        if(!candidateRepository.existsById(id)){
            throw new EntityNotFoundException("Candidate not found with id " + id);
        }
        candidateRepository.deleteById(id);
    }

    @Override
    public Candidate updateCandidate(Long id, Candidate candidate) {
        return getCandidateById(id)
            .map(existingCandidate -> candidateRepository.toEntity(mergeCandidates(existingCandidate, candidate)))
            .map(candidateRepository::save)
            .map(candidateRepository::toDomain)
            .orElseThrow(() -> new EntityNotFoundException("Candidate not found with id " + id));
    }

    private Candidate mergeCandidates(Candidate existing, Candidate updatedData) {
        return new Candidate(
            existing.getId(),
            updatedData.getName() != null ? updatedData.getName() : existing.getName(),
            updatedData.getEmail() != null ? updatedData.getEmail() : existing.getEmail(),
            updatedData.getGender() != null ? updatedData.getGender() : existing.getGender(),
            updatedData.getCountry() != null ? updatedData.getCountry() : existing.getCountry(),
            updatedData.getCurrency() != null ? updatedData.getCurrency() : existing.getCurrency(),
            updatedData.getExpectedSalary() != null ? updatedData.getExpectedSalary() : existing.getExpectedSalary(),
            updatedData.getPositionApplied() != null ? updatedData.getPositionApplied() : existing.getPositionApplied(),
            existing.getCreatedAt(),
            LocalDateTime.now()
        );
    }
}
