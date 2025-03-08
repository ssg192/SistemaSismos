import { useEffect, useState } from "react";
import MapaMexico from "./MapaMexico"; 
import "./components/Inicio.css";  

function Inicio() {
  const [sismos, setSismos] = useState([]);
  const [menuVisible, setMenuVisible] = useState(false);
  const [sismoSeleccionado, setSismoSeleccionado] = useState(null);
  const [coordenadas, setCoordenadas] = useState(null); // Estado para coordenadas

  useEffect(() => {
    fetch("http://localhost:8080/inicio", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      }
    })
      .then((response) => {
        if (!response.ok) throw new Error("Error en la respuesta del servidor");
        return response.json();
      })
      .then((data) => {
        console.log("Datos obtenidos: ", data);
        setSismos(data);
      })
      .catch((error) => console.error("Error al obtener datos:", error));
  }, []);

  const seleccionarSismo = (sismo) => {
    setSismoSeleccionado(sismo);
    setCoordenadas({ lat: sismo.latitud, lng: sismo.longitud }); // Guarda coordenadas
  };

  const handleSismoClick = (sismo) => {
    setSismoSeleccionado(sismo);
    setCoordenadas({ lat: sismo.latitud, lng: sismo.longitud });
  };

  return (
    <div className="container">
      {/* Mapa con coordenadas seleccionadas */}
      <div className="map-container">
        <MapaMexico coordenadas={coordenadas} />
      </div>

      {/* Menú lateral */}
      <div className={`sidebar ${menuVisible ? "visible" : ""}`}>
        <button className="close-btn" onClick={() => setMenuVisible(false)}>&times;</button>
        <h2>Menú</h2>
        <ul>
          <li><a href="#sismos">Sismos Recientes</a></li>
        </ul>

        {/* Lista de sismos */}
        <div className="content">
          <h2 id="sismos">Sismos Recientes</h2>
          <ul className="sismos-list">
            {sismos.map((sismo) => (
              <li 
                key={sismo.identificador} 
                className={`sismo-item ${sismoSeleccionado === sismo ? "activo" : ""}`}
                onClick={() => handleSismoClick(sismo)} // Aquí se usa handleSismoClick
              >
                <strong>{sismo.referenciaLocalizacion}</strong> - Magnitud: {sismo.magnitud}
              </li>
            ))}
          </ul>

          {/* Información detallada del sismo seleccionado */}
          {sismoSeleccionado && (
            <div className="sismo-info">
              <h3>Detalles del Sismo</h3>
              <p><b>Ubicación:</b> {sismoSeleccionado.referenciaLocalizacion}</p>
              <p><b>Magnitud:</b> {sismoSeleccionado.magnitud}</p>
              <p><b>Fecha:</b> {sismoSeleccionado.fecha} {sismoSeleccionado.hora}</p>
              <p><b>Profundidad:</b> {sismoSeleccionado.profundidad} km</p>
              <p><b>Latitud:</b> {sismoSeleccionado.latitud}, <b>Longitud:</b> {sismoSeleccionado.longitud}</p>
              <p><b>Estatus:</b> {sismoSeleccionado.estatus}</p>
            </div>
          )}
        </div>
      </div>

      {/* Botón de hamburguesa */}
      <button className="menu-btn" onClick={() => setMenuVisible(!menuVisible)}>
        &#9776;
      </button>
    </div>
  );
}

export default Inicio;
