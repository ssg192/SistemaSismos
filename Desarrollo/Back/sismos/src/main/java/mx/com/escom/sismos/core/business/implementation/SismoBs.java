package mx.com.escom.sismos.core.business.implementation;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mx.com.escom.paginacion.Paginacion;
import mx.com.escom.sismos.core.business.input.SismoService;
import mx.com.escom.sismos.core.business.output.SismoRepository;
import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;
import mx.com.escom.sismos.core.entity.Volcan;
import mx.com.escom.util.error.ErrorCodesEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class SismoBs implements SismoService {

    private final SismoRepository sismoRepository;

    @Inject
    public SismoBs(SismoRepository sismoRepository) {
        this.sismoRepository = sismoRepository;
    }

    @Override
    public List<Sismo> listaSismos(Paginacion paginacion) {
        return sismoRepository.obtenerSismos(paginacion);
    }

    @Override
    public List<Sismo> busquedaSismo(LocalDate fechaInicio, LocalDate fechaFin) {
        return sismoRepository.BusquedaSismos(fechaInicio,fechaFin);
    }

    @Override
    public List<Sismo> listSismosWithPlacaAndVolcan(Integer idSismos) {
        return sismoRepository.findSismosWithPlacaAndVolcan(idSismos);
    }
    
    @Override
    public List<Placas> listAllPlacas() {
        return sismoRepository.findAllPlacas();
    }

    @Override
    public List<Volcan> listAllVolcanes() {
        return sismoRepository.findAllVolcans();
    }

    @Override
    public Either<ErrorCodesEnum, Boolean> create(Sismo entity) {
        if(entity == null) {
            return Either.left(ErrorCodesEnum.RNS001);
        }
        sismoRepository.saveSismo(entity);
        return Either.right(true);
    }

    @Override
    public String listAllRegistros() {
        List<Sismo> datos = sismoRepository.obtenerRegistrosCsv();
        return convertirListaASCsv(datos);
    }

    private String convertirListaASCsv(List<Sismo> lista) {
        StringBuilder sb = new StringBuilder();

        sb.append("id,magnitud,hora,latitud,longitud,referencia_localizacion,volcan_nombre,volcan_latitud,volcan_longitud,placa_nombre\n");

        for (Sismo s : lista) {
            sb.append(s.getId()).append(",");
            sb.append(formatear(s.getMagnitud())).append(",");
            sb.append(s.getHora() != null ? s.getHora() : "").append(",");
            sb.append(formatear(s.getLatitud())).append(",");
            sb.append(formatear(s.getLongitud())).append(",");
            sb.append(escaparCsv(s.getReferenciaLocalizacion())).append(",");

            List<String> nombres = s.getNombreVolcanes();
            List<BigDecimal> lats = s.getLatitudVolcanes();
            List<BigDecimal> lons = s.getLongitudVolcanes();

            if (nombres != null && !nombres.isEmpty()) {
                sb.append(escaparCsv(String.join("|", nombres))).append(",");
                sb.append(escaparCsv(
                        lats.stream()
                                .map(this::formatear)
                                .collect(Collectors.joining("|"))
                )).append(",");
                sb.append(escaparCsv(
                        lons.stream()
                                .map(this::formatear)
                                .collect(Collectors.joining("|"))
                )).append(",");
            } else {
                sb.append(",,,");

            }

            sb.append(escaparCsv(s.getPlacaNombre())).append("\n");
        }

        return sb.toString();
    }


    private String formatear(BigDecimal valor) {
        return valor != null ? valor.toPlainString() : "";
    }

    private String escaparCsv(String valor) {
        if (valor == null) return "";
        if (valor.contains(",") || valor.contains("\"") || valor.contains("\n")) {
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }
        return valor;
    }
}
