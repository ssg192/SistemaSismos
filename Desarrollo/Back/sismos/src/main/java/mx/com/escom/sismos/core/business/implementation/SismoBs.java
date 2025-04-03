package mx.com.escom.sismos.core.business.implementation;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.util.error.ErrorCodesEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@ApplicationScoped
public class SismoBs implements SismoService {

    @Inject
    SismoRepository sismoRepository;

    @Override
    public List<Sismo> listaSismos() {
        return sismoRepository.obtenerSismos();
    }

    @Override
    public List<Sismo> busquedaSismo(LocalDate fecha, BigDecimal magnitud) {
        return sismoRepository.BusquedaSismos(fecha,magnitud);
    }


}
