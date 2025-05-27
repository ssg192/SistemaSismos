package mx.com.escom.sismos.core.business.implementation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.escom.paginacion.Paginacion;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@ApplicationScoped
public class SismoBs implements SismoService {

    private final SismoRepository sismoRepository;

    @Inject
    public SismoBs(SismoRepository sismoRepository) {
        this.sismoRepository = sismoRepository;
    }

    @Override
    public List<Sismo> listaSismos(Paginacion paginacion) {
        return sismoRepository.obtenerSismos(paginacion);
    }

    @Override
    public List<Sismo> busquedaSismo(LocalDate fecha, BigDecimal magnitud) {
        return sismoRepository.BusquedaSismos(fecha,magnitud);
    }

    @Override
    public List<Sismo> listSismosWithPlacaAndVolcan(Integer idSismos) {
        return sismoRepository.findSismosWithPlacaAndVolcan(idSismos);
    }
    
    @Override
    public List<Placas> listAllPlacas() {
        return sismoRepository.findAllPlacas();
    }

}
