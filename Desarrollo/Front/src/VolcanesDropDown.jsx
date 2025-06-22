import { useState } from "react";
import "./components/dropDown.css";
// Componente VolcanesDropDown.js
const VolcanesDropDown = ({ 
    volcanes,          // [{id, descripcion, lat, lng}, ...]
    volcanSelect, 
    setVolcanSelect,
    setVolcanes        // Función para actualizar los volcanes visibles en el mapa
  }) => {
    const [isOpen, setIsOpen] = useState(false);
  
    const handleSelection = (volcan) => {
      if (!volcan) {
        setVolcanSelect(null);
        setVolcanes(volcanes);  // Restablece todos los volcanes
      } else {
        setVolcanSelect(volcan.id.toString());
        setVolcanes([volcan]);  // Muestra solo el volcán seleccionado (como array)
      }
      setIsOpen(false);
    };
  
    return (
      <div className="dropdown-container">
        <h4 className="dropdown-title">Volcanes</h4>
        <button 
          className="dropdown-toggle"
          onClick={() => setIsOpen(!isOpen)}
          aria-haspopup="listbox"
          aria-expanded={isOpen}
        >
          {volcanSelect 
            ? volcanes.find(v => v.id.toString() === volcanSelect)?.descripcion 
            : "Seleccionar volcán"}
          <span className={`dropdown-arrow ${isOpen ? 'open' : ''}`}>▼</span>
        </button>
  
        {isOpen && (
          <ul className="dropdown-menu" role="listbox">
            <li
              key="all"
              className="dropdown-item"
              onClick={() => handleSelection(null)}
              role="option"
              aria-selected={!volcanSelect}
            >
              Todos los volcanes
              {!volcanSelect && <span className="selected-icon">✓</span>}
            </li>
            
            {volcanes.map((volcan) => (
              <li
                key={volcan.id}
                className="dropdown-item"
                onClick={() => handleSelection(volcan)}
                role="option"
                aria-selected={volcanSelect === volcan.id}
              >
                {volcan.descripcion}
                {volcanSelect === volcan.id && (
                  <span className="selected-icon">✓</span>
                )}
              </li>
            ))}
          </ul>
        )}
      </div>
    );
  };
  
  export default VolcanesDropDown;
  