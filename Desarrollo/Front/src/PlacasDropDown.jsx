import { useState } from "react";
import "./components/dropDown.css";
const PlacasDropDown = ({ placas, placaSeleccionada, setPlacaSeleccionada, setPlacas }) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleSelection = (placa) => {
    if (!placa) {
      setPlacaSeleccionada(null);
      setPlacas(placas); // Restaurar todas
    } else {
      const placaId = placa.properties.id.toString();
      setPlacaSeleccionada(placaId);
      setPlacas([placa]); // Solo la seleccionada
    }
    setIsOpen(false); // Cerrar el dropdown
  };

  return (
    <div className="dropdown-container">
        <h4 className="dropdown-title">Placas Tectónicas</h4>
      <button 
        className="dropdown-toggle"
        onClick={() => setIsOpen(!isOpen)}
        aria-haspopup="listbox"
        aria-expanded={isOpen}
      >
        {placaSeleccionada 
          ? placas.find(p => p.properties.id.toString() === placaSeleccionada)?.properties.nombre 
          : "Seleccionar placa"}
        <span className={`dropdown-arrow ${isOpen ? 'open' : ''}`}>▼</span>
      </button>

      {isOpen && (
        <ul className="dropdown-menu" role="listbox">
          <li
            className={`dropdown-item ${!placaSeleccionada ? 'active' : ''}`}
            onClick={() => handleSelection(null)}
            role="option"
            aria-selected={!placaSeleccionada}
          >
            Todas las placas
          </li>
          {placas.map((placa) => (
            <li
              key={placa.properties.id}
              className={`dropdown-item ${
                placaSeleccionada === placa.properties.id.toString() ? 'active' : ''
              }`}
              onClick={() => handleSelection(placa)}
              role="option"
              aria-selected={placaSeleccionada === placa.properties.id.toString()}
            >
              {placa.properties.nombre}
              {placaSeleccionada === placa.properties.id.toString() && (
                <span className="selected-icon">✓</span>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};
// Agrega esta línea al final del archivo:
export default PlacasDropDown;