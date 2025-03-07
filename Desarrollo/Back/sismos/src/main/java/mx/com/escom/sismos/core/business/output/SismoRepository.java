package mx.com.escom.sismos.core.business.output;

import mx.com.escom.sismos.core.entity.Sismo;

import java.util.List;

public interface SismoRepository {

    List<Sismo> obtenerSismos();
}
