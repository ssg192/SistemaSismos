import React from 'react';
import MapaMexico from './MapaMexico';
import Inicio from "./Inicio";  // Sin ./src/

function App() {
  return (
    <div>
      <MapaMexico />
      <Inicio />  {/* Aquí se muestran los sismos */}
    </div>
  );
}

export default App;
