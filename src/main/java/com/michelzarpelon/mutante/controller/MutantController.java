package com.michelzarpelon.mutante.controller;


import com.michelzarpelon.mutante.dto.DNADto;
import com.michelzarpelon.mutante.service.IClassifiesIndividualService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mutant")
public class MutantController {

    @Autowired
    private IClassifiesIndividualService iClassifiesIndividualService;

    @PostMapping()
    public ResponseEntity<?> isMutant(@Valid @RequestBody DNADto request) {

        if (iClassifiesIndividualService.isMutant(request.getDna())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }


}
