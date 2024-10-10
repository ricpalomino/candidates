package com.seek.candidates.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seek.candidates.infrastructure.dto.AuthResponse;
import com.seek.candidates.infrastructure.repository.CandidateJpaEntity;
import com.seek.candidates.infrastructure.repository.CandidateRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CandidateControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CandidateRepository candidateJpaRepository;

    private String jwtToken;

    private String generateJwtToken() throws Exception {
        String loginJson = """
                {
                    "username": "admin",
                    "password": "123"
                }
                """;

        MvcResult result = mockMvc.perform(post("/api/authenticate/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        AuthResponse authResponse = objectMapper.readValue(responseBody, AuthResponse.class);
        
        return "Bearer " + authResponse.jwt();
    }

    @BeforeEach
    void setUpBefore() throws Exception {
        candidateJpaRepository.deleteAll();
        jwtToken = generateJwtToken();
    }

    @Test
    void getAllCandidatesShouldReturnForbiddenIfNotSendJwt() throws Exception {
        mockMvc.perform(get("/api/candidates"))
                        .andExpect(status().isForbidden());
    }

    @Test
    void getAllCandidatesShouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/candidates")
                        .header("Authorization", jwtToken)) // Include JWT token in header
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createCandidateShouldReturnCreatedCandidate() throws Exception {
        String candidateJson = """
                {
                    "name": "Richard Palomino",
                    "email": "rpalomino@gmail.com",
                    "gender": "Male",
                    "country": "PERU",
                    "currency": "PEN",
                    "expectedSalary": 8000,
                    "positionApplied": "Software Engineer"
                }
                """;

        mockMvc.perform(post("/api/candidates")
                        .header("Authorization", jwtToken) // Include JWT token in header
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(candidateJson))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.name").value("Richard Palomino"))
                        .andExpect(jsonPath("$.email").value("rpalomino@gmail.com"));
    }

   @Test
    void getCandidateByIdShouldReturnCandidateWhenExists() throws Exception {
        CandidateJpaEntity candidate = new CandidateJpaEntity();
        candidate.setName("Richard Palomino");
        candidate.setEmail("rpalomino@gmail.com");
        candidate.setGender("Male");
        candidate.setCountry("PERU");
        candidate.setCurrency("PEN");
        candidate.setExpectedSalary(new BigDecimal(80000));
        candidate.setPositionApplied("Software Engineer");
        candidateJpaRepository.save(candidate);

        mockMvc.perform(get("/api/candidates/{id}", candidate.getId())
                        .header("Authorization", jwtToken) // Include JWT token in header
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())  // Expect HTTP 200 OK
                        .andExpect(jsonPath("$.name").value("Richard Palomino"))
                        .andExpect(jsonPath("$.email").value("rpalomino@gmail.com"))
                        .andExpect(jsonPath("$.gender").value("Male"))
                        .andExpect(jsonPath("$.country").value("PERU"));
    }

}
