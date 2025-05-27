package mx.com.escom.sismos.core.business.output;

import mx.com.escom.paginacion.Paginacion;
import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SismoRepository {

    List<Sismo> obtenerSismos(Paginacion paginacion);
    List<Sismo>BusquedaSismos(LocalDate fecha, BigDecimal magnitud);
    List<Sismo> findSismosWithPlacaAndVolcan(Integer idSismos);
    List<Placas> findAllPlacas();

  }

