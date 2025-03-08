import { useEffect } from "react";
import { MapContainer, TileLayer, CircleMarker, Popup, useMap } from "react-leaflet";
import "leaflet/dist/leaflet.css";

// Componente para cambiar la vista del mapa al hacer clic en el marcador
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

      {/* Muestra un CircleMarker solo si hay coordenadas */}
      {coordenadas && (
        <CircleMarker
          center={[coordenadas.lat, coordenadas.lng]}
          radius={10} // Tamaño del círculo
          color="red"  // Color del círculo
          fillColor="red" // Color del relleno
          fillOpacity={0.6} // Opacidad del relleno
        >
          <Popup>
            <b>Ubicación del Sismo</b> <br />
            Lat: {coordenadas.lat}, Lng: {coordenadas.lng}
          </Popup>
        </CircleMarker>
      )}
    </MapContainer>
  );
};

export default MapaMexico;
