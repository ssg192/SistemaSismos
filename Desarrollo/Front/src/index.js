import React from "react";
import ReactDOM from "react-dom/client"; // Importación de React y ReactDOM
import App from "./App"; // Importación del componente principal

// Crear un root y renderizar la aplicación
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
