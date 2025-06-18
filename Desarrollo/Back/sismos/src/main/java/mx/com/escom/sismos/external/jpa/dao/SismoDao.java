package mx.com.escom.sismos.external.jpa.dao;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceUnit;
import mx.com.escom.paginacion.Paginacion;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.sismos.core.entity.Volcan;
import mx.com.escom.sismos.external.jpa.model.SismoJpa;
import mx.com.escom.sismos.external.jpa.repository.SismoJpaRepository;
import org.hibernate.query.TypedParameterValue;
import org.hibernate.type.StandardBasicTypes;

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
    private final SismoJpaRepository sismoJpaRepository;

    @Inject
    public SismoDao(EntityManager entityManagerReading, SismoJpaRepository sismoJpaRepository) {
        this.entityManagerReading = entityManagerReading;
        this.sismoJpaRepository = sismoJpaRepository;
    }

    private static final String QUERY_PARAM_CATALOGO_PLACAS = """
            select p.placa_id , p.nombre, p.descripcion,ST_AsText(p.geom) from placas p
            """;

    private static final String QUERY_PARAM_FIND_ALL_SISMOS = """
            SELECT rs.id_registros_sismos,rs.magnitud, rs.hora, rs.fecha, rs.estatus,rs.referencia_localizacion
            FROM registros_sismos rs
            LIMIT :numPaginas OFFSET :cantidadFilas
            """;
    private static final String QUERY_PARAM_BUSQUEDA_SISMOS = """
            select  rs.id_registros_sismos, rs.magnitud ,rs.hora, rs.latitud, rs.longitud, rs.referencia_localizacion, p.nombre,rs.fecha from registros_sismos rs
            join placas p on p.placa_id = rs.placa_id
            where rs.fecha >= :fechaInicio and rs.fecha <= :fechaFin;
            """;

    private static final String QUERY_FIND_SISMOS_WITH_PLACA_AND_VOLCAN = """
            select p.nombre, ST_AsText(p.geom), rs.fecha, rs.magnitud, rs.latitud, rs.longitud, rs.referencia_localizacion, rs.estatus,
            coalesce(GROUP_CONCAT(v.nombre), 'Sin volcanes afectados') as nombre_volcan, coalesce (GROUP_CONCAT(v.latitud), 0) as latitud_volcan,coalesce(GROUP_CONCAT(v.longitud), 0) as longitud_volcan from placas p
            join registros_sismos rs on rs.placa_id=p.placa_id
            left join volcanes_afectados va on va.id_registros_sismos=rs.id_registros_sismos
            left join volcanes v on v.id_volcan=va.id_volcan
            where rs.id_registros_sismos=:idSismo
            group by p.nombre, p.geom, rs.fecha, rs.magnitud, rs.latitud, rs.longitud, rs.referencia_localizacion, rs.estatus;
            """;
    private static final String QUERY_FIND_ALL_VOLCANES = """
            select v.id_volcan, v.nombre, v.latitud,v.longitud from volcanes v;
            """;
    private static final String QUERY_FIND_ALL_SISMOS_VOLCANES_AND_PLACAS = """
            select rs.id_registros_sismos, rs.magnitud ,rs.hora, rs.latitud, rs.longitud, rs.referencia_localizacion,
            coalesce(GROUP_CONCAT(v.nombre), 'Sin volcanes afectados') as volcan_afectado, coalesce (GROUP_CONCAT(v.latitud), 0) as latitud_volcan,
            coalesce(GROUP_CONCAT(v.longitud), 0) as longitud_volcan,
            p.nombre as sismo_naciente from registros_sismos rs
            left join volcanes_afectados va on va.id_registros_sismos = rs.id_registros_sismos
            left join volcanes v on v.id_volcan = va.id_volcan
            left join placas p on p.placa_id = rs.placa_id
            group by rs.id_registros_sismos, rs.magnitud ,rs.hora, rs.latitud, rs.longitud, rs.referencia_localizacion, p.nombre
            """;

    private static final String PARAM_FECHA_INICIO = "fechaInicio";
    private static final String PARAM_FECHA_FIN = "fechaFin";
    private static final String PARAM_ID_SISMOS = "idSismo";
    private static final String PARAM_NUM_PAGINAS = "numPaginas";
    private static final String PARAM_CANTIDAD_FILAS = "cantidadFilas";


    @Override
    @SuppressWarnings("unchecked")
    public List<Sismo> obtenerSismos(Paginacion paginacion) {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_PARAM_FIND_ALL_SISMOS)
                .setParameter(PARAM_NUM_PAGINAS, new TypedParameterValue<>(StandardBasicTypes.INTEGER, paginacion.getNumeroPagina()))
                .setParameter(PARAM_CANTIDAD_FILAS, new TypedParameterValue<>(StandardBasicTypes.INTEGER, paginacion.getCantidadFilas()))
                .getResultStream();
        return result.map(sismos -> Sismo.builder()
                .id((Integer) sismos[0])
                .magnitud((BigDecimal) sismos[1])
                .hora(sismos[2] != null ? ((Time) sismos[2]).toLocalTime() : null)
                .fecha(sismos[3] != null ? ((Date) sismos[3]).toLocalDate() : null)
                .estatus((String) sismos[4])
                .referenciaLocalizacion((String) sismos[5])
                .build()
        ).toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Sismo> BusquedaSismos(LocalDate fechaInicio, LocalDate fechaFin) {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_PARAM_BUSQUEDA_SISMOS)
                .setParameter(PARAM_FECHA_INICIO, fechaInicio)
                .setParameter(PARAM_FECHA_FIN, fechaFin)
                .getResultStream();
        return result.map(busqueda -> Sismo.builder()
                        .id((Integer) busqueda[0])
                        .magnitud((BigDecimal) busqueda[1])
                        .hora(busqueda[2] != null ? ((Time) busqueda[2]).toLocalTime() : null)
                        .latitud((BigDecimal) busqueda[3])
                        .longitud((BigDecimal) busqueda[4])
                        .referenciaLocalizacion((String) busqueda[5])
                        .placas(Placas.builder()
                                .nombre((String)busqueda[6])
                                .build())
                        .fecha(busqueda[7] != null ? ((Date) busqueda[7]).toLocalDate() : null)
                        .build())
                .toList();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Sismo> findSismosWithPlacaAndVolcan(Integer idSismos) {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_FIND_SISMOS_WITH_PLACA_AND_VOLCAN)
                .setParameter(PARAM_ID_SISMOS, idSismos).getResultStream();
        return result.map(sismo -> Sismo.builder()
                .placaNombre((String) sismo[0])
                .geomPlaca((String) sismo[1])
                .fecha(sismo[2] != null ? ((Date) sismo[2]).toLocalDate() : null)
                .magnitud((BigDecimal) sismo[3])
                .latitud((BigDecimal) sismo[4])
                .longitud((BigDecimal) sismo[5])
                .referenciaLocalizacion((String) sismo[6])
                .estatus((String) sismo[7])
                .nombreVolcanes(List.of(((String) sismo[8]).split(",")))
                .latitudVolcanes(Stream.of(((String) sismo[9]).split(",")).map(BigDecimal::new).toList())
                .longitudVolcanes(Stream.of(((String) sismo[10]).split(",")).map(BigDecimal::new).toList())
                .build()
        ).toList();


    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Placas> findAllPlacas() {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_PARAM_CATALOGO_PLACAS)
                .getResultStream();
        return result.map(placa -> Placas.builder()
                .id((Integer) placa[0])
                .nombre((String) placa[1])
                .descripcion((String) placa[2])
                .ubicacion((String) placa[3]).build()

        ).toList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Volcan> findAllVolcans() {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_FIND_ALL_VOLCANES)
                .getResultStream();
        return result.map(volcanes -> Volcan.builder()
                .id((Integer) volcanes[0])
                .nombre((String) volcanes[1])
                .longitud((BigDecimal) volcanes[2])
                .latitud((BigDecimal) volcanes[3])
                .build()).toList();
    }

    @Override
    public Sismo saveSismo(Sismo sismo) {
        return sismoJpaRepository.saveAndFlush(SismoJpa.fromEntity(sismo)).toEntity();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Sismo> obtenerRegistrosCsv() {
        Stream<Object[]> result = entityManagerReading.createNativeQuery(QUERY_FIND_ALL_SISMOS_VOLCANES_AND_PLACAS)
                .getResultStream();
        return result.map(datos -> Sismo.builder()
                .id((Integer) datos[0])
                .magnitud((BigDecimal) datos[1])
                .hora(datos[2] != null ? ((Time) datos[2]).toLocalTime() : null)
                .latitud((BigDecimal) datos[3])
                .longitud((BigDecimal) datos[4])
                .referenciaLocalizacion((String) datos[5])
                .nombreVolcanes(List.of(((String) datos[6]).split(",")))
                .latitudVolcanes(Stream.of(((String) datos[7]).split(",")).map(BigDecimal::new).toList())
                .longitudVolcanes(Stream.of(((String) datos[8]).split(",")).map(BigDecimal::new).toList())
                .placaNombre((String) datos[9])
                .build()).toList();
    }

}
