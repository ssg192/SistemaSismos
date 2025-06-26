import { useEffect, useState } from "react";
import MapaMexico from "./MapaMexico";
import { useTheme } from "./useTheme";
import VistaModuloEducativo from "./VistaModuloEducativo";
import VistaCapacitaciones from "./VistaCapacitaciones";
import VistaFormularioRegistro from './VistaFormularioRegistro'
import "./components/Inicio.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDownload, faTowerCell, faSignal } from '@fortawesome/free-solid-svg-icons'; 
import PlacasDropdown from './PlacasDropDown';
import VolcanesDropdown from './VolcanesDropDown';
import SensoresDropdown from './SensoresDropDown';
import SismosDropdown from './SismosDropDown';

function Inicio() {
  const [sismos, setSismos] = useState([]);
  const [sismosPaginate, setSismosPaginate] = useState([]);
  const [menuVisible, setMenuVisible] = useState(false);
  const [sismoSeleccionado, setSismoSeleccionado] = useState(null);
  const [coordenadas, setCoordenadas] = useState(null);
  const [mostrarBusqueda, setMostrarBusqueda] = useState(false);
  const [mostrarModuloEducativo, setMostrarModuloEducativo] = useState(false);
  const [mostrarCapacitaciones, setMostrarCapacitaciones] = useState(false);
  const [seccionActiva, setSeccionActiva] = useState(null);
  const [fechaInicio, setFechaInicio] = useState("");
  const [fechaFin, setFechaFin] = useState("");
  const [magnitudFiltro, setMagnitudFiltro] = useState("");
  const [noResults, setNoResults] = useState(false);
  const [placas, setPlacas] = useState([]);
  const [constPlacas, setConstPlacas] = useState([]); //placas originales sin filtrar
  const [volcanes, setVolcanes] = useState([]);
  const [constVolcanes, setConstVolcanes] = useState([]);
  const [volcanSelect, setVolcanSelect] = useState([]);
  const [sensores, setSensores] = useState([]);
  const [sensorSelect, setSensorSelect] = useState([]);
  const [constSensores, setConstSensores] = useState([]);
  const { theme, toggleTheme } = useTheme();
  const [isLoading, setIsLoading] = useState(true);
  const [loaderFadeOut, setLoaderFadeOut] = useState(false);
  const [mostrarSelectPlacas, setMostrarSelectPlacas] = useState(false); // Controla visibilidad
  const [placaSeleccionada, setPlacaSeleccionada] = useState(null); // Filtro activo
  const [sismosOriginales, setSismosOriginales] = useState([]); // Guarda una copia sin filtrar
  const [sismosFiltrados, setSismosFiltrados] = useState([]); // Nuevo estado para sismos filtrados
  const [isVisible, setIsVisible] = useState(true);
  const [mostrarFormularioRegistro, setMostrarFormularioRegistro] = useState(false);
  // En tu componente principal
  const [mostrarSoloSismosDropdown, setMostrarSoloSismosDropdown] = useState(false);
  const [cantPagina, setCantPagina] = useState(0); // o el valor inicial que tengas
  const URL_API = "http://localhost:9090/inicio";
  //const cantPagina = 0; // Cantidad de filas por página
  const cantFilas = 5; // Número de página (0 para la primera página
  const fetchSismos = async () => {
    try {
      const response = await fetch(`http://localhost:9090/inicio?cantidadFilas=${cantPagina}&numeroPagina=${cantFilas}`);
      if (!response.ok) throw new Error("Error en la respuesta del servidor");
      const data = await response.json();
      console.log("Datos obtenidos del endpoint principal:", data); // 👈 Debug
      setSismosPaginate(data);
      setSismosOriginales(data); // 👈 Guarda la copia original
      setNoResults(false);
      console.log("Sismos obtenidos:", data); // 👈 Debug
    } catch (error) {
      console.error("Error al obtener datos:", error);
    }
  };
  // Función que filtra los sismos según la placa seleccionada
  // Función para incrementar cantPagina
  const incrementarPagina = () => {
    setCantPagina(prevCant => prevCant + 5);
    //volver a consultar al endpoint fetchSismos

  }; 
  const decrementarPagina = () => {
    setCantPagina(prevCant => Math.max(prevCant - 5, 0)); // Evita que sea negativo
    // volver a consultar al endpoint fetchSismos
  };
  

  // Función para resetear (opcional)
  const resetearPagina = () => {
    setCantPagina(0); // o tu valor inicial
  };
  // useEffect que se ejecuta cuando cantPagina cambia
  useEffect(() => {
    fetchSismos();
  }, [cantPagina]); // 👈 Se ejecuta cada vez que cantPagina cambie

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        // Hacemos fetch en paralelo
        await Promise.all([
          fetchSismos(),
          fetch("http://localhost:9090/catalogos/placas")
            .then((res) => res.json())
            .then((data) => {
              const geojsonFeatures = data?.map((placa) =>
                wktMultiPolygonToGeoJSON(placa.ubicacion, {
                  id: placa.id,
                  nombre: placa.nombre,
                  descripcion: placa.descripcion
                })
              ).filter(Boolean);
              console.log("Placas convertidas a GeoJSON:", geojsonFeatures); // 👈 Debug
              setPlacas(geojsonFeatures);
              setConstPlacas(geojsonFeatures); // Guardar las placas originales sin filtrar 
            }),
          
          fetch("http://localhost:9090/catalogos/volcanes")
            .then(r => r.json())
            .then(data => {
              const parsed = data
                .map(v => ({
                  id: v.id,
                  descripcion: v.descripcion,
                  lat: parseFloat(v.longitud),
                  lng: parseFloat(v.latitud)
                }))
                .filter(v => !isNaN(v.lat) && !isNaN(v.lng));
              setVolcanes(parsed);
              setConstVolcanes(parsed); // Guardar los volcanes originales sin filtrar
              console.log("Volcanes obtenidos:", parsed); // 👈 Debug
            }),
          
          fetch('http://localhost:9090/inicio/sensores')
            .then(res => {
              if (!res.ok) throw new Error('Error al cargar sensores');
              return res.json();
            })
            .then(data => {
              console.log("Sensores obtenidos:", data); // 👈 Debug
              setSensores(data);
              setConstSensores(data); // Guardar los sensores originales sin filtrar
            }),
        ]);
      } catch (error) {
        console.error("Error al cargar datos:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);
  // if (isLoading) {
  //   return (
  //     <div className="page-loader">
  //       <div className="loader-content">
  //         <FontAwesomeIcon icon={faSpinner} spin size="3x" />
  //         <p>Cargando datos...</p>
  //       </div>
  //     </div>
  //   );
  // }
  // Justo antes de renderizar el contenido principal:
  if (isLoading || loaderFadeOut) {
    return (
      <div className={`page-loader ${!isLoading ? 'fade-out' : ''}`}>
        <div className="loader-content">
          {/* Ícono de torre celular con animación */}
          <div className="signal-tower">
            <FontAwesomeIcon icon={faTowerCell} className="tower-icon" />
            <div className="signal-bars">
              {[1, 2, 3].map((bar) => (
                <div key={bar} className="signal-bar" style={{ '--delay': bar * 0.2 + 's' }} />
              ))}
            </div>
          </div>
          <p>Cargando sismos...</p>
        </div>
      </div>
    );
  }
  // Si quieres que se actualice al cambiar el tema, puedes agregarlo como dependencia
    // Función para convertir WKT MultiPolygon a GeoJSON
    function wktMultiPolygonToGeoJSON(wkt, props = {}) {
      if (!wkt.startsWith("MULTIPOLYGON")) return null;
    
      // Eliminar MULTIPOLYGON(( y )) al inicio/final
      const content = wkt
        .replace("MULTIPOLYGON(((", "")
        .replace(")))", "");
    
      // Cada polígono puede tener múltiples anillos separados por ')), (('
      // Pero en muchos casos solo hay uno, así que asumimos eso por ahora
      const rings = content.split(")), ((");
    
      const coordinates = rings.map(ring => {
        return [
          ring.split(",").map(coord => {
            const [lonStr, latStr] = coord.trim().split(/\s+/);
            const lat = parseFloat(latStr);
            const lon = parseFloat(lonStr);
            return [lon, lat]; // Leaflet espera [lat, lon]
          })
        ];
      });
    
      return {
        type: "Feature",
        properties: props,
        geometry: {
          type: "MultiPolygon",
          coordinates: coordinates
        }
      };
    }
  
  
  const descargarCSV = async () => {
    try {
      const response = await fetch('http://localhost:9090/inicio/descargar-csv');
      if (!response.ok) {
        throw new Error('Error al descargar el archivo');
      }
      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'datos.csv'; // Nombre del archivo a descargar
      a.click();
      window.URL.revokeObjectURL(url);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  

  //consultar toda la informacion de un sismo al endpoint http://localhost:8080/inicio/{id_sismo}
  // y mostrarla en el mapa
 // devuelve el objeto del sismo (data[0]) o null
  const consultarSismo = async (id) => {
    try {
      const response = await fetch(`${URL_API}/${id}`);
      if (!response.ok) throw new Error("Error en la respuesta del servidor");
      const data = await response.json();
      // asumimos que es un array con un solo elemento:
      return data[0] || null;
    } catch (error) {
      console.error("Error al consultar sismo:", error);
      return null;
    }
  };

  const handleSismoClick = async (sismo) => {
    // sigo guardando el sismo “básico” para la lista y detalles:
    // traigo el detalle enriquecido
    const detail = await consultarSismo(sismo.id);
    setSismoSeleccionado(detail);
    setCoordenadas({ lat: detail.latitud, lng: detail.longitud });
    if (!detail) return;
    console.log("Detalle del sismo:", detail); // 👈 Debug
    // 1) Transformar la placa a GeoJSON
    const featurePlaca = wktMultiPolygonToGeoJSON(detail.geomPlaca, {
      id: detail.nombrePlaca,
      nombre: detail.nombrePlaca,
      descripcion: detail.descripcion
    });
    if (featurePlaca) {
      setPlacas([featurePlaca]);  // si solo quieres mostrar esa placa
    } else {
      setPlacas([]);              // o limpiar si no viene WKT válido
    }
  
    // 2) Transformar volcanes
    // detail.nombreVolcanes, detail.latitudVolcanes, detail.longitudVolcanes → [{…},…]
    const volcanesDetalle = detail.nombreVolcanes
      .map((nombre, i) => {
        const lat = parseFloat(detail.latitudVolcanes[i]);
        const lng = parseFloat(detail.longitudVolcanes[i]);
        if (isNaN(lat) || isNaN(lng)) return null;
        return {
          id:      `${nombre}-${i}`,
          descripcion: nombre,
          lat: lat,
          lng: lng
        };
      })
      .filter(Boolean);
      console.log("Volcanes del sismo:", volcanesDetalle); // 👈 Debug
    setVolcanes(volcanesDetalle);
  };

  const handleBuscar = async () => {
    if (!fechaInicio || !fechaFin) {
      setNoResults(true);
      setSismos([]);
      return;
    }

    // Separar por los dos puntos
    const [anio, mes, dia] = fechaFin.split('-');

    // Reorganizar al formato deseado
    const fechaFinFormateada = `${dia}/${mes}/${anio}`;
    const [anio2, mes2, dia2] = fechaInicio.split('-');
    const fechaInicioFormateada = `${dia2}/${mes2}/${anio2}`;
    try {
      const response = await fetch(`http://localhost:9090/inicio/Busqueda-by-periodo?fin=${fechaFinFormateada}&inicio=${fechaInicioFormateada}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) throw new Error("Error en la respuesta del servidor");

      const data = await response.json();
      console.log("Datos filtrados de los sismos segun el periodo:", data); // 👈 Debug
      setSismos(data);
      setNoResults(data.length === 0);
      setMostrarSelectPlacas(true);
      console.log("Sismos filtrados:", data); // 👈 Debug
    } catch (error) {
      console.error("Error al filtrar datos:", error);
      setNoResults(true);
      setSismos([]);
    }
  };

  const handleLimpiar = async () => {
    setFechaInicio("");
    setMagnitudFiltro("");
    await fetchSismos();
  };

  // Vistas independientes
  if (mostrarModuloEducativo) {
    return <VistaModuloEducativo onVolver={() => setMostrarModuloEducativo(false)} />;
  }

  if (mostrarCapacitaciones) {
    return <VistaCapacitaciones onVolver={() => setMostrarCapacitaciones(false)} />;
  }

  // Si se activa el módulo de formulario
  if (mostrarFormularioRegistro) {
    return (
      <VistaFormularioRegistro
        onVolver={() => setMostrarFormularioRegistro(false)}
      />
    )
  }
  return (
    <div className="container" data-theme={theme}>
      {/* Botón lupa para mostrar búsqueda */}
      <button
        className="search-toggle-btn"
        onClick={() => setMostrarBusqueda(!mostrarBusqueda)}
        aria-label="Mostrar búsqueda"
      >
        🔍
      </button>

      {/* Switch del Tema */}
      <div className="theme-switch-container">
        <label className="switch" aria-label="Cambiar tema">
          <input
            type="checkbox"
            onChange={toggleTheme}
            checked={theme === "dark"}
          />
          <span className="slider round"></span>
        </label>
      </div>

      {/* Cuadro de búsqueda desplegable */}
      {mostrarBusqueda && (
        <div className="search-container">
          <h3 className="search-title">Búsqueda de Sismos</h3>
          {mostrarSelectPlacas && (
          <div className="placas-select-container">
          <select
            value={placaSeleccionada || ""}
            onChange={(e) => {
              const placaNombre = e.target.value || null; // ✅ Correcto: `e.target.value` devuelve el ID seleccionado
              setPlacaSeleccionada(placaNombre);
              console.log("Placa seleccionada:", placaNombre); // 👈 Debug
              if (!placaNombre) {
                setSismos(sismosOriginales); // Mostrar todos si no hay selección
                return;
              }
    
              
    
              // 2. Filtrar sismos cuyo `nombrePlaca` coincida
              if (placaNombre) {
                console.log("Filtrando sismos por placa:", sismos); // 👈 Debug
                const filtrados = sismos.filter(sismo => 
                  sismo.nombrePlaca == placaNombre // Comparación exacta (incluye mayúsculas)
                );
                setSismos(filtrados);
              }
            }}
            className="placas-select"
          >
            <option value="">-- Todas las placas --</option>
            {constPlacas.map((placa) => (
              <option key={placa.properties.nombre} value={placa.properties.nombre}>
                {placa.properties.nombre}
              </option>
            ))}
          </select>
        </div>
        )}

          <input
            type="date"
            value={fechaInicio}
            onChange={(e) => setFechaInicio(e.target.value)}
            className="search-input"
          />
          <input
            type="date"
            value={fechaFin}
            onChange={(e) => setFechaFin(e.target.value)}
            className="search-input"
          />
          <div className="button-group">
            <button onClick={handleBuscar} className="search-button">
              Buscar
            </button>
            <button onClick={handleLimpiar} className="search-button clear-button">
              Limpiar
            </button>
          </div>
          {noResults && (
            <p className="no-results">No se encontraron sismos con estos criterios</p>
          )}
        </div>
      )}

      {/* Resultados debajo del cuadro de búsqueda */}
      {!noResults && sismos.length > 0 && mostrarBusqueda && (
        <div className={`search-results ${isVisible ? 'visible' : 'hidden'}`}>
        <div className="results-header" onClick={() => setIsVisible(!isVisible)}>
          <h3>Resultados de la Búsqueda</h3>
          <span className="toggle-icon">
            {isVisible ? '▼' : '▶'}
          </span>
        </div>
        
        {isVisible && (
          <ul className="sismos-resultados">
            {sismos.map((sismo) => (
              <li
                key={`${sismo.fecha}-${sismo.hora}`}
                className={`sismo-item ${
                  sismoSeleccionado?.fecha === sismo.fecha &&
                  sismoSeleccionado?.hora === sismo.hora
                    ? "activo"
                    : ""
                }`}
                onClick={() => handleSismoClick(sismo)}
              >
                <strong>{sismo.referenciaLocalizacion}</strong> - Magnitud: {sismo.magnitud}
              </li>
            ))}
          </ul>
        )}
      </div>
      )}

      {/* Mapa */}
      <div className="map-container">
        <MapaMexico 
          coordenadas={coordenadas} 
          theme={theme} 
          placas={placas}  // 👈 Esta línea es crucial
          volcanes={volcanes}
          sensores={sensores} // 👈 Agregar volcanes
        />
      </div>

      {/* Menú lateral */}
      <div className={`sidebar ${menuVisible ? "visible" : ""}`}>
        <h2>Menú</h2>
  
  {/* Mostrar menú normal cuando NO está en modo solo sismos */}
  {!mostrarSoloSismosDropdown && (
    <ul>
      <li>
        <a 
          href="#" 
          onClick={(e) => { 
            e.preventDefault(); 
            setSeccionActiva("sismos");
            setMostrarSoloSismosDropdown(true); // Activar modo solo sismos
          }}
        >
          Sismos Recientes
        </a>
      </li>
      
      {/* Todos los otros elementos del menú */}
      <PlacasDropdown 
        placas={constPlacas}
        placaSeleccionada={placaSeleccionada}
        setPlacaSeleccionada={setPlacaSeleccionada}
        setPlacas={setPlacas}
      />
      
      <VolcanesDropdown 
        volcanes={constVolcanes}
        volcanSelect={volcanSelect}
        setVolcanSelect={setVolcanSelect}
        setVolcanes={setVolcanes}
      />
      
      <SensoresDropdown 
        sensores={constSensores}
        sensorSelect={sensorSelect}
        setSensorSelect={setSensorSelect}
        setSensores={setSensores}
      />
      
      <li>
        <a
          href="#"
          onClick={(e) => {
            e.preventDefault();
            setMenuVisible(false);
            setMostrarCapacitaciones(true);
          }}
        >
          Capacitaciones y Simulacros
        </a>
      </li>
      
      <li>
        <a
          href="#"
          onClick={(e) => {
            e.preventDefault();
            setMenuVisible(false);
            setMostrarModuloEducativo(true);
          }}
        >
          Módulo Educativo sobre Sismos
        </a>
      </li>
      
      <li>
        <a
          href="#"
          onClick={e => {
            e.preventDefault()
            setMostrarFormularioRegistro(true)
          }}
        >
          Nuevo Registro
        </a>
      </li>
      
      <li>
        <button className="btn-descargar" onClick={descargarCSV}>
          <FontAwesomeIcon icon={faDownload} /> Exportar Sismos
        </button>
      </li>
    </ul>
  )}

  {/* Mostrar solo el dropdown de sismos cuando está en modo solo sismos */}
  {mostrarSoloSismosDropdown && (
    <div className="sismos-only-section">
      {/* Botón para volver al menú principal */}
      <button 
        className="btn-volver"
        onClick={() => setMostrarSoloSismosDropdown(false)}
      >
        ← Volver al menú
      </button>
      
      <SismosDropdown 
        sismos={sismosPaginate}
        sismoSeleccionado={sismoSeleccionado}
        setSismoSeleccionado={setSismoSeleccionado}
        handleSismoClick={handleSismoClick}
        onIncrementarPagina={incrementarPagina}
        onDecrementarPagina={decrementarPagina} // 👈 Nueva prop
        onResetearPagina={resetearPagina} // 👈 Opcional
        cantPagina={cantPagina}
      />
    </div>
  )}

  {/* Contenido de sismos (se mantiene igual) */}
  <div className="content">
    {/* ... tu código existente ... */}
  </div>
</div>



      {/* Botón menú hamburguesa */}
      <button
        className={`menu-btn ${menuVisible ? 'active' : ''}`}
        onClick={() => {
          setMenuVisible(!menuVisible);
          setSeccionActiva(null);
        }}
        aria-label={menuVisible ? "Cerrar menú" : "Abrir menú"}
      >
        <div className="hamburger-icon">
          <span className="menu-btn-bar"></span>
          <span className="menu-btn-bar"></span>
          <span className="menu-btn-bar"></span>
        </div>
      </button>
    </div>
  );
}

export default Inicio;
