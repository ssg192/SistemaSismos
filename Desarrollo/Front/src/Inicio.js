import { useEffect, useState } from "react";
import MapaMexico from "./MapaMexico";
import { useTheme } from "./useTheme";
import VistaModuloEducativo from "./VistaModuloEducativo";
import VistaCapacitaciones from "./VistaCapacitaciones";
import "./components/Inicio.css";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDownload } from '@fortawesome/free-solid-svg-icons'; 

function Inicio() {
  const [sismos, setSismos] = useState([]);
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
  const [volcanes, setVolcanes] = useState([]);
  const [sensores, setSensores] = useState([]);
  const { theme, toggleTheme } = useTheme();
  const URL_API = "http://localhost:8080/inicio";

  useEffect(() => {
    fetchSismos();
    
    fetch("http://localhost:8080/catalogos/placas")
    .then((res) => res.json())
    .then((data) => {
      console.log("Datos del servidor:", data); // 👈 Debug
      
      const geojsonFeatures = data?.map((placa) =>
        wktMultiPolygonToGeoJSON(placa.ubicacion, {
          id: placa.id,
          nombre: placa.nombre,
          descripcion: placa.descripcion
        })
      ).filter(Boolean);
      
      console.log("Placas convertidas a GeoJSON:", geojsonFeatures); // 👈 Debug
      setPlacas(geojsonFeatures);
    })
    .catch(error => {
      console.error("Error cargando placas:", error); // 👈 Debug
    });

    fetch("http://localhost:8080/catalogos/volcanes")
    .then(r => r.json())
    .then(data => {
      // parsea y filtra SOLO objetos con lat y lng válidos
      const parsed = data
        .map(v => ({
          id:          v.id,
          descripcion: v.descripcion,
          // fíjate bien en el nombre real de los campos que te devuelve tu API:
          lat:         parseFloat(v.longitud),    
          lng:         parseFloat(v.latitud)
        }))
        .filter(v => !isNaN(v.lat) && !isNaN(v.lng));
      console.log("Volcanes válidos:", parsed);
      setVolcanes(parsed);
    })
    .catch(console.error);


    //fetch a los sensores
    fetch('http://localhost:8080/inicio/sensores')
      .then(res => {
        if (!res.ok) throw new Error('Error al cargar sensores');
        return res.json();
      })
      .then(data => {
        // Imaginamos que el endpoint devuelve un array:
        setSensores(data);
      })
      .catch(err => console.error(err));
  }, []);

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
      const response = await fetch('http://localhost:8080/inicio/descargar-csv');
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

  const fetchSismos = async () => {
    try {
      const response = await fetch(URL_API);
      if (!response.ok) throw new Error("Error en la respuesta del servidor");
      const data = await response.json();
      setSismos(data);
      setNoResults(false);
    } catch (error) {
      console.error("Error al obtener datos:", error);
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
    setSismoSeleccionado(sismo);
    setCoordenadas({ lat: sismo.latitud, lng: sismo.longitud });
  
    // traigo el detalle enriquecido
    const detail = await consultarSismo(sismo.id);
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

    try {
      const response = await fetch(`http://localhost:8080/inicio/Busqueda-by-periodo?FechaFin=${fechaFin}&FechaInicio=${fechaInicio}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!response.ok) throw new Error("Error en la respuesta del servidor");

      const data = await response.json();
      setSismos(data);
      setNoResults(data.length === 0);
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
        <div className="search-results">
          <h3>Resultados de la Búsqueda</h3>
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
        <ul>
          <li>
            <a href="#" onClick={(e) => { e.preventDefault(); setSeccionActiva("sismos"); }}>
              Sismos Recientes
            </a>
          </li>
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
            <div>
              <button className="btn-descargar" onClick={descargarCSV}>
                <FontAwesomeIcon icon={faDownload} /> Exportar Sismos
              </button>
            </div>
          </li>
        </ul>

        {/* Contenido condicional por sección */}
        <div className="content">
          {seccionActiva === "sismos" && (
            <>
              <h2>Sismos Recientes</h2>
              <ul className="sismos-list">
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
              {sismoSeleccionado && (
                <div className="sismo-info">
                  <h3>Detalles del Sismo</h3>
                  <p><b>Ubicación:</b> {sismoSeleccionado.referenciaLocalizacion}</p>
                  <p><b>Magnitud:</b> {sismoSeleccionado.magnitud}</p>
                  <p><b>Fecha:</b> {sismoSeleccionado.fecha} {sismoSeleccionado.hora}</p>
                  <p><b>Profundidad:</b> {sismoSeleccionado.profundidad ?? "N/A"} km</p>
                  <p><b>Latitud:</b> {sismoSeleccionado.latitud}, <b>Longitud:</b> {sismoSeleccionado.longitud}</p>
                  <p><b>Estatus:</b> {sismoSeleccionado.estatus}</p>
                </div>
              )}
            </>
          )}
        </div>
      </div>

      {/* Botón menú hamburguesa */}
      <button
        className="menu-btn"
        onClick={() => {
          setMenuVisible(!menuVisible);
          setSeccionActiva(null);
        }}
        aria-label="Abrir menú"
      >
        &#9776;
      </button>
    </div>
  );
}

export default Inicio;
