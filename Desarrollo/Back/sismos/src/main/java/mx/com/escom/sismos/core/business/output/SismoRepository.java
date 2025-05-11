package mx.com.escom.sismos.core.business.output;

import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SismoRepository {

    List<Sismo> obtenerSismos();
    List<Sismo>BusquedaSismos(LocalDate fecha, BigDecimal magnitud);
    List<Placas> findPlacaSismoByIdPlaca(Integer idPlaca, Integer idSismos);
    List<Placas> findAllPlacas();

  }

