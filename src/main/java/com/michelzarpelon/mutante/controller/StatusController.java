package com.michelzarpelon.mutante.controller;

import com.michelzarpelon.mutante.dto.StatusDto;
import com.michelzarpelon.mutante.service.IStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/stats")
public class StatusController {

    @Autowired
    private IStatusService statusService;


    @GetMapping
    public StatusDto getStatus() {
        return statusService.getStatusLineage();
    }
}
