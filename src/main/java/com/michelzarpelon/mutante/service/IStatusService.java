package com.michelzarpelon.mutante.service;

import com.michelzarpelon.mutante.dto.StatusDto;

public interface IStatusService {

    public StatusDto getStatusLineage();

    public Double getRatio(Integer totMutant, Integer totHuman);

}
