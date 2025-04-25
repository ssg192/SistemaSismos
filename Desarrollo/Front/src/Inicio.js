import { useEffect, useState } from "react";
import MapaMexico from "./MapaMexico";
import { useTheme } from "./useTheme";
import VistaModuloEducativo from "./VistaModuloEducativo";
import VistaCapacitaciones from "./VistaCapacitaciones";
import "./components/Inicio.css";

function Inicio() {
  const [sismos, setSismos] = useState([]);
  const [menuVisible, setMenuVisible] = useState(false);
  const [sismoSeleccionado, setSismoSeleccionado] = useState(null);
  const [coordenadas, setCoordenadas] = useState(null);
  const [mostrarBusqueda, setMostrarBusqueda] = useState(false);
  const [mostrarModuloEducativo, setMostrarModuloEducativo] = useState(false);
  const [mostrarCapacitaciones, setMostrarCapacitaciones] = useState(false);
  const [seccionActiva, setSeccionActiva] = useState(null);
  const [fechaFiltro, setFechaFiltro] = useState("");
  const [magnitudFiltro, setMagnitudFiltro] = useState("");
  const [noResults, setNoResults] = useState(false);

  const { theme, toggleTheme } = useTheme();
  const URL_API = "http://localhost:8080/inicio";

  useEffect(() => {
    fetchSismos();
  }, []);

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

  const handleSismoClick = (sismo) => {
    setSismoSeleccionado(sismo);
    setCoordenadas({ lat: sismo.latitud, lng: sismo.longitud });
  };

  const handleBuscar = async () => {
    if (!fechaFiltro || !magnitudFiltro) {
      setNoResults(true);
      setSismos([]);
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/inicio/${fechaFiltro}/${magnitudFiltro}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          fecha: fechaFiltro,
          magnitud: parseFloat(magnitudFiltro),
        }),
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
    setFechaFiltro("");
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
            value={fechaFiltro}
            onChange={(e) => setFechaFiltro(e.target.value)}
            className="search-input"
          />
          <input
            type="number"
            min="0"
            step="0.1"
            value={magnitudFiltro}
            onChange={(e) => setMagnitudFiltro(e.target.value)}
            placeholder="Magnitud"
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
        <MapaMexico coordenadas={coordenadas} theme={theme} />
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
