package mx.com.escom.sismos.external.jpa.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.sismos.external.jpa.model.SismoJpa;
import mx.com.escom.sismos.external.jpa.repository.SismoJpaRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@ApplicationScoped
public class SismoDao implements SismoRepository {
    @Inject
    SismoJpaRepository sismoJpaRepository;

    @Inject
    EntityManager entityManager;

    private static final String PARAM_BUSQUEDA="select" +
            " rs.fecha, rs.magnitud, rs.estatus, rs.hora, rs.latitud, rs.longitud, rs.referencia_localizacion " +
            "from registros_sismos rs where rs.fecha = :fecha and  rs.magnitud = :magnitud";

    private static final String PARAM_FECHA= "fecha";
    private static final String PARAM_MAGNITUD= "magnitud";

    @Override
    public List<Sismo> obtenerSismos() {
        return sismoJpaRepository.findAll().stream().map(SismoJpa::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<Sismo> BusquedaSismos(LocalDate fecha, BigDecimal magnitud) {
        Stream<Object[]>busqueda = entityManager.createNativeQuery(PARAM_BUSQUEDA).setParameter(PARAM_FECHA,fecha).setParameter(PARAM_MAGNITUD,magnitud).getResultStream();
        return busqueda.map(sismo->Sismo.builder()
                .fecha(sismo[0] != null ? ((Date) sismo[0]).toLocalDate() : null)
                .magnitud((BigDecimal) sismo[1])
                .estatus((String) sismo[2])
                .hora(sismo[3] != null ? ((Time) sismo[3]).toLocalTime() : null)
                .latitud((BigDecimal) sismo[4])
                .longitud((BigDecimal) sismo[5])
                .referenciaLocalizacion((String) sismo[6])
                .build()

        ).collect(Collectors.toList());
    }

}
