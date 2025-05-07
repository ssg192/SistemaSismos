package mx.com.escom.sismos.core.business.output;

import java.util.List;

import mx.com.escom.sismos.core.entity.Sensores;

public interface SensoresRepository {
    List<Sensores> getAllSensores();
}
