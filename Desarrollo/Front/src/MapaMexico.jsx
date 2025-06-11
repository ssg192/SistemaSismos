import React, { useEffect, useState } from "react";
import {
  MapContainer,
  TileLayer,
  Marker,
  Popup,
  CircleMarker,
  Circle,
  GeoJSON,
  useMap
} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import volcanoUrl from './assets/icons/volcano.svg';
import sensorUrl from './assets/icons/volcano.svg';
// Componente auxiliar para centrar el mapa
const ChangeView = ({ coordenadas }) => {
  const map = useMap();
  useEffect(() => {
    if (coordenadas) {
      map.setView([coordenadas.lat, coordenadas.lng], 7);
    }
  }, [coordenadas, map]);
  return null;
};

// Icono personalizado para los volcanes
const volcanoIcon = new L.Icon({
  iconUrl: volcanoUrl,
  iconRetinaUrl: volcanoUrl,
  iconSize:     [44, 44],
  iconAnchor:   [16, 32],
  popupAnchor:  [0, -32],
});

//icono personallizado para los sensores
const sensorIcon = new L.Icon({
  iconUrl:   sensorUrl,    // pon la ruta a tu icono
  iconRetinaUrl: sensorUrl,
  iconSize:   [25, 25],
  iconAnchor: [12, 12],
  popupAnchor:[0, -12],
});

const MapaMexico = ({
  coordenadas,
  theme = "light",
  placas = [],     // default props
  volcanes = [],   // default props
  sensores = []    // default props
}) => {
  const [circleScale, setCircleScale] = useState(1);
  const [opacity, setOpacity] = useState(0.6);

  // Animación del círculo del sismo
  useEffect(() => {
    if (!coordenadas) return;
    let scale = 1, opac = 0.6;
    const timer = setInterval(() => {
      scale += 0.05;
      opac = Math.max(opac - 0.01, 0);
      setCircleScale(scale);
      setOpacity(opac);
      if (scale > 3 || opac <= 0) {
        scale = 1; opac = 0.6;
      }
    }, 50);
    return () => clearInterval(timer);
  }, [coordenadas]);

  const tileURL = theme === "dark"
    ? "https://{s}.basemaps.cartocdn.com/dark_all/{z}/{x}/{y}{r}.png"
    : "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png";

  const tileAttr = theme === "dark"
    ? '&copy; <a href="https://carto.com/">CARTO</a>'
    : '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors';

  const placaStyle = {
    color: "#689c58",
    weight: 2,
    opacity: 0.8,
    fillOpacity: 0.1,
    fillColor: "#b3dda6"
  };

  const onEachPlaca = (feature, layer) => {
    if (feature.properties) {
      layer.bindPopup(`
        <strong>${feature.properties.nombre || "Placa"}</strong><br/>
        ${feature.properties.descripcion || ""}
      `);
    }
  };

  const onEachVolcan = (feature, layer) => {
    if (feature.properties) {
      layer.bindPopup(`
        <strong>${feature.properties.descripcion || "Volcan"}</strong><br/>
        ${feature.properties.descripcion || ""}
      `);
    }
  };

  return (
    <MapContainer
      center={[23.6345, -102.5528]}
      zoom={5}
      style={{ height: "100%", width: "100%" }}
    >
      <TileLayer url={tileURL} attribution={tileAttr} noWrap />

      {/*
        1) Pinto volcanes FILTRANDO lat/lng válidos
        2) Uso zIndexOffset para que estén sobre GeoJSON
      */}
      {volcanes.map(v=>{
        const lat = Number(v.lat), lng = Number(v.lng);
        if (!isFinite(lat)||!isFinite(lng)) return null;
        return (
          <Marker
            key={v.id}
            position={[lat,lng]}
            icon={volcanoIcon}
            zIndexOffset={1000}
          >
            <Popup>
              <strong>{v.descripcion}</strong><br/>
            </Popup>
          </Marker>
        )
      })}

      {sensores.map(s => (
        <Marker
          key={s.id}
          position={[s.latitud, s.longitud]}
          opacity={0.5}   // 0 = invisible, 1 = opaco
        >
          <Popup>
            <strong>{s.nombre} ({s.codigo})</strong><br/>
            Estado: {s.estado}<br/>
            Red: {s.red}
          </Popup>
        </Marker>
      ))}

      {/*
        Ahora dibujo las placas GEOSJSON
      */}
      {placas.map((placa, i) => (
        <GeoJSON
          key={placa.properties?.id || i}
          data={placa}
          style={placaStyle}
          onEachFeature={onEachPlaca}
        />
      ))}

      <ChangeView coordenadas={coordenadas} />

      {coordenadas && (
        <>
          <CircleMarker
            center={[coordenadas.lat, coordenadas.lng]}
            radius={10}
            color="red"
            fillColor="red"
            fillOpacity={0.8}
          >
            <Popup>
              <b>Ubicación del Sismo</b><br/>
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
