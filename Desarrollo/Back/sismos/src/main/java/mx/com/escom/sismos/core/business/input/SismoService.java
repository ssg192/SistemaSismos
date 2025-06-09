package mx.com.escom.sismos.core.business.input;
import io.vavr.control.Either;
import mx.com.escom.paginacion.Paginacion;
import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.sismos.core.entity.Volcan;
import mx.com.escom.util.error.ErrorCodesEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SismoService {
    List<Sismo> listaSismos(Paginacion paginacion);
    List<Sismo> busquedaSismo(LocalDate fecha, BigDecimal magnitud);
    List<Sismo> listSismosWithPlacaAndVolcan(Integer idSismos);
    List<Placas> listAllPlacas();
    List<Volcan> listAllVolcanes();
    Either<ErrorCodesEnum, Boolean> create(Sismo entity);
}
