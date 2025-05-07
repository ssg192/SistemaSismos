package mx.com.escom.sismos.core.business.input;
import mx.com.escom.sismos.core.entity.Placas;
import mx.com.escom.sismos.core.entity.Sismo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface SismoService {
    List<Sismo> listaSismos();
    List<Sismo> busquedaSismo(LocalDate fecha, BigDecimal magnitud);
    List<Placas> listaPlacas();
}
