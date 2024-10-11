package com.seek.candidates.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.seek.candidates.application.service.CandidateService;
import com.seek.candidates.domain.model.Candidate;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/candidates")
@Tag(name = "Candidatos", description = "CRUD for Candidates")
public class CandidateController {

    private CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public ResponseEntity<List<Candidate>> getAllCandidates() {
        return new ResponseEntity<>(candidateService.getAllCandidates(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        return candidateService.getCandidateById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) {
        return new ResponseEntity<>(candidateService.createCandidate(candidate), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Candidate> updateCandidate(@PathVariable long id, @RequestBody Candidate candidate) {
        return new ResponseEntity<>(candidateService.updateCandidate(id, candidate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }

}
