import React from "react";
import { MapContainer, Marker, Popup } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import L from "leaflet";
import volcanoUrl from "./assets/icons/volcano.svg?url";

const customIcon = new L.Icon({
  iconUrl: volcanoUrl,
  iconRetinaUrl: volcanoUrl,
  iconSize: [32, 32],
  iconAnchor: [16, 32],
  popupAnchor: [0, -32],
});

export default function TestMapaIcon() {
  return (
    <div style={{ width: 200, height: 200, margin: "50px auto", background: "#eee" }}>
      <MapContainer
        center={[0, 0]}
        zoom={1}
        style={{ width: "100%", height: "100%" }}
        attributionControl={false}
        zoomControl={false}
        scrollWheelZoom={false}
        doubleClickZoom={false}
        dragging={false}
      >
        <Marker position={[0, 0]} icon={customIcon}>
          <Popup>¿Ves este icono en el mapa?</Popup>
        </Marker>
      </MapContainer>
    </div>
  );
}
