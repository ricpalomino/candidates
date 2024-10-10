package com.seek.candidates.infrastructure.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seek.candidates.domain.model.Candidate;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface CandidateRepository extends JpaRepository<CandidateJpaEntity, Long> {

    // Convertir una lista de entidades JPA en una lista de modelos de dominio
    default List<Candidate> toDomainList(List<CandidateJpaEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // Convertir una entidad JPA en un modelo de dominio
    default Candidate toDomain(CandidateJpaEntity entity) {
        return new Candidate(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getGender(),
                entity.getCountry(),
                entity.getCurrency(),
                entity.getExpectedSalary(),
                entity.getPositionApplied(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    // Convertir un modelo de dominio en una entidad JPA
    default CandidateJpaEntity toEntity(Candidate candidate) {
        CandidateJpaEntity entity = new CandidateJpaEntity();
        entity.setId(candidate.getId());
        entity.setName(candidate.getName());
        entity.setEmail(candidate.getEmail());
        entity.setGender(candidate.getGender());
        entity.setCountry(candidate.getCountry());
        entity.setCurrency(candidate.getCurrency());
        entity.setExpectedSalary(candidate.getExpectedSalary());
        entity.setPositionApplied(candidate.getPositionApplied());
        entity.setCreatedAt(candidate.getCreatedAt());
        entity.setUpdatedAt(candidate.getUpdatedAt());
        return entity;
    }
}
