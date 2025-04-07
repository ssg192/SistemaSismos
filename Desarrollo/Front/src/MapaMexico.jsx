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

// Componente para cambiar la vista del mapa al seleccionar un sismo
const ChangeView = ({ coordenadas }) => {
  const map = useMap();
  useEffect(() => {
    if (coordenadas) {
      map.setView([coordenadas.lat, coordenadas.lng], 7);
    }
  }, [coordenadas, map]);
  return null;
};

const MapaMexico = ({ coordenadas, theme }) => {
  const [circleScale, setCircleScale] = useState(1);
  const [opacity, setOpacity] = useState(0.6);

  // Animación del círculo
  useEffect(() => {
    if (!coordenadas) return;

    let scale = 1;
    let opac = 0.6;

    const interval = setInterval(() => {
      scale += 0.05;
      opac = Math.max(opac - 0.01, 0);

      setCircleScale(scale);
      setOpacity(opac);

      if (scale > 3 || opac <= 0) {
        scale = 1;
        opac = 0.6;
      }
    }, 50);

    return () => clearInterval(interval);
  }, [coordenadas]);

  // Elegir capa del mapa según el tema
  const tileLayerURL =
    theme === "dark"
      ? "https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png"
      : "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";

  const tileLayerAttribution =
    theme === "dark"
      ? '&copy; <a href="https://carto.com/">CARTO</a>'
      : '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';

  return (
    <MapContainer
      center={[23.6345, -102.5528]} // Centro de México
      zoom={5}
      style={{ height: "100%", width: "100%" }} // 👈 importante: no usar 100vh aquí
    >
      <TileLayer url={tileLayerURL} attribution={tileLayerAttribution} noWrap={true} />
      <ChangeView coordenadas={coordenadas} />
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
          <Circle
            center={[coordenadas.lat, coordenadas.lng]}
            radius={circleScale * 50000}
            color="red"
            fillColor="red"
            fillOpacity={opacity}
            stroke={false}
          />
        </>
      )}
    </MapContainer>
  );
};

export default MapaMexico;
