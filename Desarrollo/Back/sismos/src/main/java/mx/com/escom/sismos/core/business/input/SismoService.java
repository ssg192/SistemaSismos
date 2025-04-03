package mx.com.escom.sismos.core.business.input;

import io.vavr.control.Either;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.util.error.ErrorCodesEnum;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SismoService {
    List<Sismo> listaSismos();
    List<Sismo> busquedaSismo(LocalDate fecha, BigDecimal magnitud);

}
