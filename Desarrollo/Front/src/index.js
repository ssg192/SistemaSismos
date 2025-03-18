import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

  
=======
import React from "react";
import ReactDOM from "react-dom/client";  // Cambiar esta importación
import App from "./App";  // Importa tu componente principal

// Crear un root y renderizar la aplicación
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<App />);
