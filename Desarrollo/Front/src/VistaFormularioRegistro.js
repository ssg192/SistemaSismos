import React, { useState } from 'react'
import {
  MapContainer,
  TileLayer,
  Marker,
  useMapEvent
} from 'react-leaflet'
import 'leaflet/dist/leaflet.css'
import L from 'leaflet'
import './components/VistaFormularioRegistro.css'

// Para arreglar el icono por defecto de Leaflet en React:
delete L.Icon.Default.prototype._getIconUrl
L.Icon.Default.mergeOptions({
  iconRetinaUrl: require('leaflet/dist/images/marker-icon-2x.png'),
  iconUrl:       require('leaflet/dist/images/marker-icon.png'),
  shadowUrl:     require('leaflet/dist/images/marker-shadow.png')
})

// Este pequeño componente captura clicks en el mapa
// y actualiza posición + campos de lat/long
function LocationSelector({ setPosition, setLatitud, setLongitud }) {
  useMapEvent('click', e => {
    const { lat, lng } = e.latlng
    setPosition([lat, lng])
    setLatitud(lat.toFixed(6))
    setLongitud(lng.toFixed(6))
  })
  return null
}

export default function VistaFormularioRegistro({ onVolver }) {
  const hoy = new Date().toISOString().split('T')[0]
  const [fecha, setFecha] = useState(hoy)
  const [hora, setHora] = useState('')
  const [magnitud, setMagnitud] = useState('')
  const [latitud, setLatitud] = useState('')
  const [longitud, setLongitud] = useState('')
  const [profundidad, setProfundidad] = useState('')
  const [referencia, setReferencia] = useState('')
  const [estatus, setEstatus] = useState('')

  const [position, setPosition] = useState(null)
  const [responseData, setResponseData] = useState(null)
  const [errorMsg, setErrorMsg] = useState(null)
  const [isSubmitting, setIsSubmitting] = useState(false)
  const DEFAULT_POSITION = [19.432608, -99.133209]  // CDMX
  const formatFecha = (isoDate) => {
    // isoDate viene como "2025-06-07"
    const [yyyy, mm, dd] = isoDate.split('-')
    return `${dd}/${mm}/${yyyy}`  // -> "07-06-2025"
    }

  const handleSubmit = async e => {
    e.preventDefault()
    setErrorMsg(null)
    setResponseData(null)
    setIsSubmitting(true)

    const payload = {
      fecha: formatFecha(fecha),
      hora,
      magnitud: Number(magnitud),
      latitud: Number(latitud),
      longitud: Number(longitud),
      profundidad: Number(profundidad),
      referenciaLocalizacion: referencia,
      estatus
    }

    try {
      const resp = await fetch(
        'http://localhost:9090/inicio/registroSismo',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            accept: 'application/json'
          },
          body: JSON.stringify(payload)
        }
      )
      if (!resp.ok) {
        let errText = resp.statusText
        const ct = resp.headers.get('content-type') || ''
        if (ct.includes('application/json')) {
          const errJson = await resp.json()
          errText = `${errJson.attributeName} inválido: ${errJson.value}`
        } else {
          errText = await resp.text()
        }
        throw new Error(`Error ${resp.status}: ${errText}`)
      }
      const data = await resp.json()
      setResponseData(data)
    } catch (err) {
      setErrorMsg(err.message)
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <div className="vf-wrapper">
      <div className="vf-card">
        <h2 className="vf-title">Registro de Sismo</h2>
        <form onSubmit={handleSubmit} className="vf-form">

          {/* Fecha y hora */}
          <div className="vf-form-group">
            <label>Fecha</label>
            <input
              type="date"
              max={hoy}
              value={fecha}
              onChange={e => setFecha(e.target.value)}
              required
            />
          </div>
          <div className="vf-form-group">
            <label>Hora</label>
            <input
              type="time"
              value={hora}
              onChange={e => setHora(e.target.value)}
              required
            />
          </div>

          {/* Magnitud */}
          <div className="vf-form-group">
            <label>Magnitud</label>
            <input
              type="number"
              step="0.1"
              min="0"
              value={magnitud}
              onChange={e => setMagnitud(e.target.value)}
              placeholder="p.ej. 4.5"
              required
            />
          </div>

          {/* MAPA para seleccionar lat/lng */}
          <div className="vf-form-group">
            <label>Selecciona ubicación</label>
            <MapContainer
              center={position || DEFAULT_POSITION}
              zoom={position ? 12 : 6}
              className="vf-map"
            >
              <TileLayer
                attribution='&copy; OpenStreetMap contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
              />
              <LocationSelector
                setPosition={setPosition}
                setLatitud={setLatitud}
                setLongitud={setLongitud}
              />
              {position && <Marker position={position} />}
            </MapContainer>
          </div>

          {/* Latitud / Longitud (solo lectura) */}
          <div className="vf-form-group-inline">
            <div>
              <label>Latitud</label>
              <input type="text" value={latitud} readOnly />
            </div>
            <div>
              <label>Longitud</label>
              <input type="text" value={longitud} readOnly />
            </div>
          </div>

          {/* Profundidad, referencia y estatus */}
          <div className="vf-form-group">
            <label>Profundidad (km)</label>
            <input
              type="number"
              step="0.1"
              min="0"
              value={profundidad}
              onChange={e => setProfundidad(e.target.value)}
              placeholder="p.ej. 10"
              required
            />
          </div>
          <div className="vf-form-group">
            <label>Referencia de Localización</label>
            <input
              type="text"
              value={referencia}
              onChange={e => setReferencia(e.target.value)}
              placeholder="p.ej. 5 km al SE de X"
              required
            />
          </div>
          <div className="vf-form-group">
            <label>Estatus</label>
            <select
              value={estatus}
              onChange={e => setEstatus(e.target.value)}
              required
            >
              <option value="">-- Selecciona --</option>
              <option value="CONFIRMADO">CONFIRMADO</option>
              <option value="PENDIENTE">PENDIENTE</option>
              <option value="REVISAR">REVISAR</option>
            </select>
          </div>

          {/* Botones */}
          <div className="vf-buttons">
            <button
              type="submit"
              className="vf-btn vf-btn-primary"
              disabled={isSubmitting}
            >
              {isSubmitting ? 'Enviando...' : 'Guardar'}
            </button>
            <button
              type="button"
              className="vf-btn vf-btn-secondary"
              onClick={onVolver}
              disabled={isSubmitting}
            >
              Volver
            </button>
          </div>
        </form>

        {/* Mensajes */}
        {responseData && (
          <div className="vf-response vf-success">
            <h4>Registro exitoso</h4>
           
          </div>
        )}
        {errorMsg && (
          <div className="vf-response vf-error">
            <h4>Ocurrió un error:</h4>
            <p>{errorMsg}</p>
          </div>
        )}
      </div>
    </div>
  )
}
