package mx.com.escom.sismos.external.jpa.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnit;
import mx.com.escom.paginacion.Paginacion;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;


@ApplicationScoped
public class SismoDao implements SismoRepository {

    @PersistenceUnit()
    private final EntityManager entityManagerReading;

    @Inject
    public SismoDao(EntityManager entityManagerReading) {
        this.entityManagerReading = entityManagerReading;
    }

    private static final String QUERY_PARAM_CATALOGO_PLACAS= """
            select p.placa_id , p.nombre, p.descripcion,ST_AsText(p.geom) from placas p
            """;

    private static final String QUERY_PARAM_FIND_ALL_SISMOS = """
            SELECT rs.id, rs.fecha, rs.hora, rs.magnitud, rs.latitud, rs.longitud, rs.profundidad, rs.referencia_localizacion,rs.estatus,rs.placa_id
            FROM registros_sismos rs 
            LIMIT :numPaginas OFFSET :cantidadFilas;
            """;
    private static final String PARAM_BUSQUEDA = """
            select rs.fecha, rs.magnitud, rs.estatus, rs.hora, rs.latitud, rs.longitud, rs.referencia_localizacion 
            from registros_sismos rs where rs.fecha = :fecha and  rs.magnitud = :magnitud;
        """;

    private static final String QUERY_PARAM_PLACA_SISMO_BY_ID_PLACA= """
            select p.nombre, p.descripcion, ST_AsText(p.geom) from placas p
            join registros_sismos rs on rs.placa_id = p.placa_id
            where p.placa_id = :idPlaca and rs.id = :idSismo;
            """;

    private static final String PARAM_FECHA = "fecha";
    private static final String PARAM_MAGNITUD = "magnitud";
    private static final String PARAM_ID_PLACA = "idPlaca";
    private static final String PARAM_ID_SISMOS = "idSismo";
    private static final String PARAM_NUM_PAGINAS = "numPaginas";
    private static final String PARAM_CANTIDAD_FILAS = "cantidadFilas";

    @Override
    @SuppressWarnings("unchecked")
    public List<Sismo> obtenerSismos(Paginacion paginacion) {
        int offset = (paginacion.getNumeroPagina() - 1) * paginacion.getCantidadFilas();
        Stream<Object[]>result= entityManagerReading.createNativeQuery(QUERY_PARAM_FIND_ALL_SISMOS)
                .setParameter(PARAM_NUM_PAGINAS,paginacion.getNumeroPagina() )
                .setParameter(PARAM_CANTIDAD_FILAS,offset)
                .getResultStream();
        return result.map(sismos->Sismo.builder()
                .id((Integer) sismos[0])
                .fecha(sismos[1] != null ? ((Date) sismos[1]).toLocalDate() : null)
                .hora(sismos[2] != null ? ((Time) sismos[2]).toLocalTime() : null)
                .magnitud((BigDecimal) sismos[3])
                .latitud((BigDecimal) sismos[4])
                .longitud((BigDecimal) sismos[5])
                .profundidad((BigDecimal) sismos[6])
                .referenciaLocalizacion((String) sismos[7])
                .estatus((String) sismos[8])
                .placaId((Integer) sismos[9])
                .build()
        ).toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Sismo> BusquedaSismos(LocalDate fecha, BigDecimal magnitud) {
        Stream<Object[]> busqueda = entityManagerReading.createNativeQuery(PARAM_BUSQUEDA).setParameter(PARAM_FECHA, fecha).setParameter(PARAM_MAGNITUD, magnitud).getResultStream();
        return busqueda.map(sismo -> Sismo.builder()
                .fecha(sismo[0] != null ? ((Date) sismo[0]).toLocalDate() : null)
                .magnitud((BigDecimal) sismo[1])
                .estatus((String) sismo[2])
                .hora(sismo[3] != null ? ((Time) sismo[3]).toLocalTime() : null)
                .latitud((BigDecimal) sismo[4])
                .longitud((BigDecimal) sismo[5])
                .referenciaLocalizacion((String) sismo[6])
                .build()

        ).toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Placas> findPlacaSismoByIdPlaca(Integer idPlaca, Integer idSismos) {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_PARAM_PLACA_SISMO_BY_ID_PLACA)
                .setParameter(PARAM_ID_PLACA, idPlaca)
                .setParameter(PARAM_ID_SISMOS, idSismos)
                .getResultStream();
        return result.map(placa-> Placas.builder()
                .nombre((String) placa[0])
                .descripcion((String)placa[1])
                .ubicacion((String) placa[2])
                .build()).toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Placas> findAllPlacas() {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_PARAM_CATALOGO_PLACAS)
                .getResultStream();
        return result.map(placa->Placas.builder()
                .id((Integer) placa[0])
                .nombre((String) placa[1])
                .descripcion((String) placa[2])
                .ubicacion((String)placa[3]).build()

        ).toList();
    }

}
