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

    </MapContainer>
  );
};

export default MapaMexico;
