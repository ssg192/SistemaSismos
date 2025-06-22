import { useState } from "react";
import "./components/dropDown.css";
// Componente VolcanesDropDown.js
const SensoresDropDown = ({ 
    sensores,          // [{id, descripcion, lat, lng}, ...]
    sensorSelect, 
    setSensorSelect,
    setSensores        // Función para actualizar los volcanes visibles en el mapa
  }) => {
    const [isOpen, setIsOpen] = useState(false);
  
    const handleSelection = (sensor) => {
      if (!sensor) {
        setSensorSelect(null);
        setSensores(sensores);  // Restablece todos los volcanes
      } else {
        setSensorSelect(sensor.id);
        setSensores([sensor]);  // Muestra solo el volcán seleccionado (como array)
      }
      setIsOpen(false);
    };
  
    return (
      <div className="dropdown-container">
        <h4 className="dropdown-title">Sensores</h4>
        <button 
          className="dropdown-toggle"
          onClick={() => setIsOpen(!isOpen)}
          aria-haspopup="listbox"
          aria-expanded={isOpen}
        >
          {sensorSelect 
            ? sensores.find(v => v.id === sensorSelect)?.nombre 
            : "Seleccionar sensor"}
          <span className={`dropdown-arrow ${isOpen ? 'open' : ''}`}>▼</span>
        </button>
  
        {isOpen && (
          <ul className="dropdown-menu" role="listbox">
            <li
              key="all"
              className="dropdown-item"
              onClick={() => handleSelection(null)}
              role="option"
              aria-selected={!sensorSelect}
            >
              Todos los sensores
              {!sensorSelect && <span className="selected-icon">✓</span>}
            </li>
            
            {sensores.map((sensor) => (
              <li
                key={sensor.id}
                className="dropdown-item"
                onClick={() => handleSelection(sensor)}
                role="option"
                aria-selected={sensorSelect === sensor.id}
              >
                {sensor.nombre}
                {sensorSelect === sensor.id && (
                  <span className="selected-icon">✓</span>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>
    );
  };
  
  export default SensoresDropDown;
  