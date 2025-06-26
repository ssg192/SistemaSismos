  import "./components/dropDownSismos.css";

  const SismosList = ({ 
      sismos, 
      sismoSeleccionado, 
      setSismoSeleccionado, 
      handleSismoClick,
      onIncrementarPagina,
      onDecrementarPagina, // 👈 Nueva prop
      onResetearPagina, // 👈 Opcional
      cantPagina
    }) => {

      const handleSelection = (sismo) => {
          if (!sismo) {
              setSismoSeleccionado(null);
          } else {
            console.log("Sismo seleccionado:", sismo);
            const sismoId = sismo.id.toString();
            setSismoSeleccionado(sismoId);
            handleSismoClick(sismo);
          }
        };
      
        const handleNextPage = () => {
          console.log("Cargando más sismos...");
          setSismoSeleccionado(null);
          onIncrementarPagina();
        };
      
        const handlePrevPage = () => {
          console.log("Cargando menos sismos...");
          setSismoSeleccionado(null);
          onDecrementarPagina();
        };
      
        const handleReset = () => {
          setSismoSeleccionado(null);
          onResetearPagina();
        };
      
        return (
          <div className="sismos-list">
            <h3>Listado de Sismos Recientes ({sismos?.length} sismos mostrados)</h3>
            
            {/* Controles de paginación superiores */}
            <div className="pagination-controls top">
  <div className="pagination-info">
    Mostrando {cantPagina} sismos más recientes
  </div>
  
  <div className="pagination-buttons">
    {/* Botón para decrementar */}
    {cantPagina > 0 && (
      <div 
        className="pagination-btn prev-btn"
        onClick={handlePrevPage}
        role="button"
        tabIndex={0}
        title="Mostrar menos sismos"
      >
        <span className="btn-icon">←</span>
      </div>
    )}
    
    {/* Botón para incrementar */}
    <div 
      className="pagination-btn next-btn"
      onClick={handleNextPage}
      role="button"
      tabIndex={0}
      title="Cargar más sismos"
    >
      <span className="btn-icon">→</span>
    </div>
    
    {/* Botón reset (opcional) */}
    {onResetearPagina && (
      <div 
        className="pagination-btn reset-btn"
        onClick={handleReset}
        role="button"
        tabIndex={0}
        title="Volver al inicial (10 sismos)"
      >
        <span className="btn-icon">↻</span>
      </div>
    )}
  </div>
</div>
      
            {/* Lista de sismos */}
            <ul className="sismos-list-items">
              {sismos?.map((sismo) => (
                <li
                  key={sismo.id}
                  className={`sismo-item ${sismoSeleccionado === sismo.id.toString() ? 'selected' : ''}`}
                  onClick={() => handleSelection(sismo)}
                  role="button"
                  tabIndex={0}
                >
                  <div className="sismo-info">
                    <div className="sismo-location">
                      <strong>{sismo.referenciaLocalizacion}</strong>
                    </div>
                    <div className="sismo-details">
                      <span className="magnitude">Magnitud: {sismo.magnitud}</span>
                      <span className="date">{new Date(sismo.fechaLocal).toLocaleDateString()}</span>
                    </div>
                  </div>
                  {sismoSeleccionado === sismo.id.toString() && (
                    <span className="selected-icon">✓</span>
                  )}
                </li>
              ))}
            </ul>
      
            {/* Controles de paginación inferiores */}
            
          </div>
        );
      };
      
      export default SismosList;