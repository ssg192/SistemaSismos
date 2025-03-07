package mx.com.escom.sismos.core.business.implementation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Sismo;

import java.util.List;


@ApplicationScoped
public class SismoBs implements SismoService {

    @Inject
    SismoRepository sismoRepository;

    @Override
    public List<Sismo> listaSismos() {
        return sismoRepository.obtenerSismos();
    }
}
