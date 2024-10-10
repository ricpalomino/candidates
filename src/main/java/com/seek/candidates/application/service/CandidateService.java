package com.seek.candidates.application.service;

import java.util.List;
import java.util.Optional;

import com.seek.candidates.domain.model.Candidate;

public interface CandidateService {

    List<Candidate> getAllCandidates();
    Optional<Candidate> getCandidateById(Long id);
    Candidate createCandidate(Candidate candidate);
    void deleteCandidate(Long id);
    Candidate updateCandidate(Long id, Candidate candidate);
    
}
