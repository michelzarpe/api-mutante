package com.michelzarpelon.mutante.controller;


import com.michelzarpelon.mutante.service.IClassifiesIndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mutant")
public class MutantController {

    @Autowired
    private IClassifiesIndividualService iClassifiesIndividualService;

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    //TODO ARUMAR REQUEST BODY
    public ResponseEntity<?> isMutant(@Validated @RequestBody String[] request) {

        if (iClassifiesIndividualService.isMutant(request)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
