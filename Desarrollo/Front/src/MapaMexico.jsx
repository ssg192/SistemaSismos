<<<<<<< HEAD
import { MapContainer, TileLayer } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

const MapaMexico = () => {
  return (
    <MapContainer 
      center={[23.6345, -102.5528]} 
      zoom={5} 
      style={{ height: "100vh", width: "100%" }}  // 100vh para ocupar toda la altura de la ventana
    >
  <TileLayer
  url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
  attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
  noWrap={true}
/>

=======
import { useEffect, useState } from "react";
import { MapContainer, TileLayer, CircleMarker, Popup, Circle, useMap } from "react-leaflet";
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
  const [circleScale, setCircleScale] = useState(1); // Inicia en 1 para evitar problemas iniciales
  const [opacity, setOpacity] = useState(0.6);
  const [expanding, setExpanding] = useState(false);

  useEffect(() => {
    if (coordenadas) {
      setExpanding(true); // Inicia la expansión de la onda sísmica
    }
  }, [coordenadas]);

  useEffect(() => {
    let interval;

    if (expanding) {
      interval = setInterval(() => {
        setCircleScale((prev) => prev + 0.05); // Aumenta el tamaño del círculo
        setOpacity((prev) => Math.max(prev - 0.01, 0)); // Disminuye la opacidad

        // Reinicia el efecto cuando el círculo es demasiado grande o la opacidad es 0
        if (circleScale > 3 || opacity <= 0) {
          setCircleScale(1); // Reinicia el tamaño del círculo
          setOpacity(0.6); // Reinicia la opacidad
        }
      }, 50); // Actualiza cada 50ms para una animación más suave
    }

    return () => clearInterval(interval); // Limpia el intervalo al desmontar el componente
  }, [expanding, circleScale, opacity]);

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
        <>
          <CircleMarker
            center={[coordenadas.lat, coordenadas.lng]}
            radius={10} // Tamaño del círculo
            color="red" // Color del círculo
            fillColor="red" // Color del relleno
            fillOpacity={0.7} // Opacidad del relleno
          >
            <Popup>
              <b>Ubicación del Sismo</b> <br />
              Lat: {coordenadas.lat}, Lng: {coordenadas.lng}
            </Popup>
          </CircleMarker>

          {/* Onda sísmica (expansión) */}
          <Circle
            center={[coordenadas.lat, coordenadas.lng]}
            radius={circleScale * 50000} // Escala de expansión (ajusta el multiplicador según sea necesario)
            color="red"
            fillColor="red"
            fillOpacity={opacity} // Opacidad decreciente
            stroke={false} // Sin borde
          />
        </>
      )}
>>>>>>> feat/developer
    </MapContainer>
  );
};

<<<<<<< HEAD
export default MapaMexico;
=======
export default MapaMexico;
>>>>>>> feat/developer
