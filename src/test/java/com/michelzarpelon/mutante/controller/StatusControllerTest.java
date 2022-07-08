package com.michelzarpelon.mutante.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.michelzarpelon.mutante.dto.StatusDto;
import com.michelzarpelon.mutante.service.impl.StatusService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatusControllerTest {

    @LocalServerPort
    int randomServerPort;

    @MockBean
    StatusService statusService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetStatusSuccess() throws Exception {


        var expected = new StatusDto(1, 2, 0.5D);

        ObjectMapper mapper = new ObjectMapper();


        when(statusService.getStatusLineage()).thenReturn(expected);

        var response = mockMvc.perform(
                get("/stats")
                        .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var result = response.andReturn().getResponse().getContentAsString();

        Assertions.assertThat(result).contains(mapper.writeValueAsString(expected));

    }
}
