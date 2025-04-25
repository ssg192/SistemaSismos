// src/VistaCapacitaciones.js
import "./components/VistaCapacitaciones.css";

function VistaCapacitaciones({ onVolver }) {
  return (
    <div className="vista-capacitaciones">
      <button className="volver-btn capacitacion" onClick={onVolver}>← Volver</button>
      <h2>Capacitaciones y Simulacros</h2>

      <section>
        <h3>¿Por qué son importantes?</h3>
        <p>
          Las capacitaciones y simulacros ayudan a fomentar una cultura de
          prevención y preparar a la población para actuar de forma adecuada en
          caso de sismos u otras emergencias.
        </p>
      </section>

      <section>
        <h3>Simulacros Recientes</h3>
        <ul>
          <li>19 de Septiembre - Simulacro Nacional 2024</li>
          <li>21 de Marzo - Simulacro de evacuación en escuelas de CDMX</li>
          <li>15 de Mayo - Simulacro en edificios gubernamentales</li>
        </ul>
      </section>

      <section>
        <h3>Capacitaciones Ofrecidas</h3>
        <p>
          Se han impartido talleres en escuelas, empresas y centros comunitarios
          sobre:
        </p>
        <ul>
          <li>Primeros auxilios</li>
          <li>Uso de extintores</li>
          <li>Planes de evacuación</li>
        </ul>
      </section>
    </div>
  );
}

export default VistaCapacitaciones;
