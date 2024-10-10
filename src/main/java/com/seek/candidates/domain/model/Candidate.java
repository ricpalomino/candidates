package com.seek.candidates.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    private Long id;
    private String name;
    private String email;
    private String gender;
    private String country;
    private String currency;
    private BigDecimal expectedSalary;
    private String positionApplied;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
