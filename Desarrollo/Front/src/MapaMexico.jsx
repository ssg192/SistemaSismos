import React, { useEffect, useState } from "react";
import {
  MapContainer,
  TileLayer,
  CircleMarker,
  Popup,
  Circle,
  useMap,
} from "react-leaflet";
import "leaflet/dist/leaflet.css";

// Componente para cambiar la vista del mapa dinámicamente
const ChangeView = ({ coordenadas }) => {
  const map = useMap();

  useEffect(() => {
    if (coordenadas) {
      map.setView([coordenadas.lat, coordenadas.lng], 7); // Mueve el mapa
    }
  }, [coordenadas, map]);

  return null;
};

const MapaMexico = ({ coordenadas }) => {
  const [circleScale, setCircleScale] = useState(1); // Tamaño inicial del efecto
  const [opacity, setOpacity] = useState(0.6);

  // Manejo del efecto de expansión de la onda sísmica
  useEffect(() => {
    if (!coordenadas) return;

    setCircleScale(1); // Reinicia la escala cuando cambian las coordenadas
    setOpacity(0.6);

    const interval = setInterval(() => {
      setCircleScale((prev) => prev + 0.05); // Aumenta el tamaño gradualmente
      setOpacity((prev) => Math.max(prev - 0.01, 0)); // Reduce la opacidad

      // Detener el efecto si las condiciones se cumplen
      if (circleScale > 3 || opacity <= 0) {
        setCircleScale(1);
        setOpacity(0.6); // Reinicio
      }
    }, 50);

    return () => clearInterval(interval); // Limpia el efecto al desmontar o cambiar coordenadas
  }, [coordenadas, circleScale, opacity]);

  return (
    <MapContainer
      center={[23.6345, -102.5528]} // Centro inicial en México
      zoom={5}
      style={{ height: "100vh", width: "100%" }}
    >
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        noWrap={true}
      />
      <ChangeView coordenadas={coordenadas} />

      {/* Solo muestra el marcador si hay coordenadas */}
      {coordenadas && (
        <>
          <CircleMarker
            center={[coordenadas.lat, coordenadas.lng]}
            radius={10}
            color="red"
            fillColor="red"
            fillOpacity={0.7}
          >
            <Popup>
              <b>Ubicación del Sismo</b> <br />
              Lat: {coordenadas.lat}, Lng: {coordenadas.lng}
            </Popup>
          </CircleMarker>

          {/* Agrega el efecto de onda sísmica */}
          <Circle
            center={[coordenadas.lat, coordenadas.lng]}
            radius={circleScale * 50000} // Multiplicador para ajustar tamaño
            color="red"
            fillColor="red"
            fillOpacity={opacity} // La opacidad varía dinámicamente
            stroke={false}
          />
        </>
      )}
    </MapContainer>
  );
};

export default MapaMexico;
