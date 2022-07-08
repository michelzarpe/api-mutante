package com.michelzarpelon.mutante.service.impl;

import com.michelzarpelon.mutante.config.exception.CalculateException;
import com.michelzarpelon.mutante.dto.StatusDto;
import com.michelzarpelon.mutante.repository.ILineageRepository;
import com.michelzarpelon.mutante.model.Lineage;
import com.michelzarpelon.mutante.service.IStatusService;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService implements IStatusService {

    private static final Logger LOGGER = Logger.getLogger(StatusService.class.getName());

    @Autowired
    private ILineageRepository iLineageRepository;

    @Override
    public StatusDto getStatusLineage() {

        LOGGER.info("Iniciando calculo de proporção");

        Integer totMutant = 0;
        Integer totHuman = 0;

        try {
            List<Lineage> lineageList = this.iLineageRepository.findAll();

            LOGGER.info("Quantidade de registro LineageList = " + lineageList.size());

            List<Lineage> mutantList = lineageList.stream().filter(lineage -> lineage.getMutant() == true).toList();

            LOGGER.info("Quantidade de registro mutantList = " + mutantList.size());

            List<Lineage> humanList = lineageList.stream().filter(lineage -> lineage.getMutant() == false).toList();

            LOGGER.info("Quantidade de registro humanList = " + humanList.size());

            totMutant = mutantList.size();
            totHuman = humanList.size();

            return new StatusDto(totMutant, totHuman, getRatio(totMutant, totHuman));
        } catch (Exception e) {
            LOGGER.severe("Erro ao realizar calculo da proporção total humando = " + totHuman + " total mutante = " + totMutant);
            throw new CalculateException("Erro ao realizar calculo da proporção");
        }
    }

    @Override
    public Double getRatio(Integer totMutant, Integer totHuman) {
        return (totHuman > 0) ? Math.floor((totMutant / (double) totHuman) * 100) / 100 : 0;
    }
}
