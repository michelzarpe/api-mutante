package com.michelzarpelon.mutante.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.michelzarpelon.mutante.dto.DNADto;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPostIsMutantSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = "http://localhost:"+randomServerPort+"/mutant";
        URI uri = new URI(baseUrl);

        DNADto dnaDto = new DNADto();
        dnaDto.setDna(new String[]{"CTGATA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"});

        ResponseEntity<String> result = restTemplate.postForEntity(uri, dnaDto, String.class);

        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testPostIsNotMutantSuccess() throws Exception {

        DNADto dnaDto = new DNADto();
        dnaDto.setDna(new String[]{"CTGATA", "CTTTGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"});

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dnaDto);
        mockMvc.perform(
                post("/mutant")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }
}
