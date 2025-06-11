import React from "react";
import ReactDOM from "react-dom/client"; // Importación de React y ReactDOM
import App from "./App"; // Importación del componente principal
// Asegurarte de que Leaflet cargue bien sus imágenes de icono:
import iconUrl    from 'leaflet/dist/images/marker-icon.png'
import iconRetina from 'leaflet/dist/images/marker-icon-2x.png'
import shadowUrl  from 'leaflet/dist/images/marker-shadow.png'

delete L.Icon.Default.prototype._getIconUrl
L.Icon.Default.mergeOptions({
  iconRetinaUrl: iconRetina,
  iconUrl:       iconUrl,
  shadowUrl:     shadowUrl,
})
// Crear un root y renderizar la aplicación
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
