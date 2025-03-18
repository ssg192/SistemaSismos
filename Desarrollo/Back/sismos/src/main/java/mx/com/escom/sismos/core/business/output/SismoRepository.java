package mx.com.escom.sismos.core.business.output;

import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SismoRepository {

    List<Sismo> obtenerSismos();

}

