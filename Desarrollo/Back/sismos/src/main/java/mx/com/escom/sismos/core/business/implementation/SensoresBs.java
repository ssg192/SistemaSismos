package mx.com.escom.sismos.core.business.implementation;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.escom.sismos.core.business.input.SensoresService;
import mx.com.escom.sismos.core.business.output.SensoresRepository;
import mx.com.escom.sismos.core.entity.Sensores;

@ApplicationScoped
public class SensoresBs implements SensoresService {
    @Inject
    SensoresRepository sensoresRepository;
    @Override
    public List<Sensores> getAllSensores() {
        return sensoresRepository.getAllSensores();
    }
}
