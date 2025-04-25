// src/VistaModuloEducativo.js
import "./components/VistaModuloEducativo.css";

function VistaModuloEducativo({ onVolver }) {
  return (
    <div className="vista-educativa">
      <button className="volver-btn educativo" onClick={onVolver}>← Volver</button>
      <h2>Módulo Educativo Sobre Sismos</h2>

      <section>
        <h3>¿Qué es un sismo?</h3>
        <p>Un sismo es un movimiento del suelo causado por la liberación de energía en la corteza terrestre. Esta energía se libera en forma de ondas sísmicas, que hacen vibrar el terreno.</p>
      </section>

      <section>
        <h3>Causas de los sismos</h3>
        <ul>
          <li>Tectónica de placas</li>
          <li>Fallas geológicas</li>
          <li>Actividad volcánica</li>
        </ul>
      </section>

      <section>
        <h3>Escalas de medición</h3>
        <p>La magnitud mide la energía liberada (Escala de Richter), mientras que la intensidad describe los efectos del sismo en una zona determinada (Escala de Mercalli).</p>
      </section>

      <section>
        <h3>¿Qué hacer antes, durante y después de un sismo?</h3>
        <p><strong>Antes:</strong> Prepara una mochila de emergencia, identifica zonas seguras y rutas de evacuación.</p>
        <p><strong>Durante:</strong> Mantén la calma, aléjate de ventanas y objetos que puedan caer, refúgiate bajo una mesa resistente.</p>
        <p><strong>Después:</strong> Verifica si hay heridos o fugas de gas, mantente informado y sigue las indicaciones de Protección Civil.</p>
      </section>
    </div>
  );
}

export default VistaModuloEducativo;
