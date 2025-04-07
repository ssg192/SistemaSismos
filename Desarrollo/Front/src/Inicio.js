import { useEffect, useState } from "react";
import MapaMexico from "./MapaMexico";
import { useTheme } from "./useTheme";
import "./components/Inicio.css";

function Inicio() {
  const [sismos, setSismos] = useState([]);
  const [menuVisible, setMenuVisible] = useState(false);
  const [sismoSeleccionado, setSismoSeleccionado] = useState(null);
  const [coordenadas, setCoordenadas] = useState(null);
  const { theme, toggleTheme } = useTheme();

  const [fechaFiltro, setFechaFiltro] = useState("");
  const [magnitudFiltro, setMagnitudFiltro] = useState("");
  const [noResults, setNoResults] = useState(false);

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

  return (
    <div className="container" data-theme={theme}>
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

      {/* Cuadro de búsqueda */}
      <div className="search-container">
        <h3 className="search-title">Búsqueda de Sismos</h3>
        <input
          type="date"
          value={fechaFiltro}
          onChange={(e) => setFechaFiltro(e.target.value)}
          className="search-input"
          placeholder="aaaa-mm-dd"
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

      {/* Mapa */}
      <div className="map-container">
        <MapaMexico coordenadas={coordenadas} theme={theme} />
      </div>

      {/* Menú lateral */}
      <div className={`sidebar ${menuVisible ? "visible" : ""}`}>
        <button
          className="close-btn"
          onClick={() => setMenuVisible(false)}
          aria-label="Cerrar menú"
        >
          &times;
        </button>
        <h2>Menú</h2>
        <ul>
          <li>
            <a href="#sismos">Sismos Recientes</a>
          </li>
        </ul>
        <div className="content">
          <h2 id="sismos">Sismos Recientes</h2>
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
                <strong>{sismo.referenciaLocalizacion}</strong> - Magnitud:{" "}
                {sismo.magnitud}
              </li>
            ))}
          </ul>
          {sismoSeleccionado && (
            <div className="sismo-info">
              <h3>Detalles del Sismo</h3>
              <p>
                <b>Ubicación:</b> {sismoSeleccionado.referenciaLocalizacion}
              </p>
              <p>
                <b>Magnitud:</b> {sismoSeleccionado.magnitud}
              </p>
              <p>
                <b>Fecha:</b> {sismoSeleccionado.fecha}{" "}
                {sismoSeleccionado.hora}
              </p>
              <p>
                <b>Profundidad:</b>{" "}
                {sismoSeleccionado.profundidad ?? "N/A"} km
              </p>
              <p>
                <b>Latitud:</b> {sismoSeleccionado.latitud},{" "}
                <b>Longitud:</b> {sismoSeleccionado.longitud}
              </p>
              <p>
                <b>Estatus:</b> {sismoSeleccionado.estatus}
              </p>
            </div>
          )}
        </div>
      </div>

      {/* Botón menú hamburguesa */}
      <button
        className="menu-btn"
        onClick={() => setMenuVisible(!menuVisible)}
        aria-label="Abrir menú"
      >
        &#9776;
      </button>
    </div>
  );
}

export default Inicio;
