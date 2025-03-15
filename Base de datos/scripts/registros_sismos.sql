-- Tabla de registros de sismos
CREATE TABLE registros_sismos (
    id SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    magnitud DECIMAL(3,1) NOT NULL,
    latitud DECIMAL(7,4) NOT NULL,
    longitud DECIMAL(7,4) NOT NULL,
    profundidad DECIMAL(5,1) NOT NULL,
    referencia_localizacion TEXT,
    fecha_utc DATE NOT NULL,
    hora_utc TIME NOT NULL,
    estatus VARCHAR(20),
    identificador UUID DEFAULT uuid_generate_v4(),
    placa_id INT REFERENCES placas_tectonicas(id), -- Relación con la tabla de placas tectónicas
    geom GEOMETRY(Point, 4326)-- Relación con la tabla de placas tectónicas
);

INSERT INTO registros_sismos (
    id, fecha, hora, magnitud, latitud, longitud, profundidad, referencia_localizacion,
    fecha_utc, hora_utc, estatus, identificador, placa_id, geom
) VALUES
(1, '2025-02-01', '00:09:39', 3.8, 16.1260, -94.2510, 93.9, 
    '39 km al SUROESTE de ARRIAGA, CHIS', '2025-02-01', '06:09:39', 'verificado',
    uuid_generate_v4(), NULL, ST_SetSRID(ST_MakePoint(-94.2510, 16.1260), 4326)),

(2, '2025-02-01', '00:16:48', 3.2, 16.5790, -98.4740, 5.0, 
    '14 km al SUROESTE de OMETEPEC, GRO', '2025-02-01', '06:16:48', 'verificado',
    uuid_generate_v4(), NULL, ST_SetSRID(ST_MakePoint(-98.4740, 16.5790), 4326)),

(3, '2025-02-01', '00:32:58', 1.5, 23.0600, -109.6700, 9.8, 
    '4 km al ESTE de SAN JOSE DEL CABO, BCS', '2025-02-01', '06:32:58', 'verificado',
    uuid_generate_v4(), NULL, ST_SetSRID(ST_MakePoint(-109.6700, 23.0600), 4326)),

(4, '2025-02-01', '00:52:48', 3.7, 18.5910, -106.3110, 9.7, 
    '197 km al SUROESTE de CIHUATLAN, JAL', '2025-02-01', '06:52:48', 'verificado',
    uuid_generate_v4(), NULL, ST_SetSRID(ST_MakePoint(-106.3110, 18.5910), 4326)),

(5, '2025-02-01', '00:57:22', 3.8, 17.2820, -94.4270, 144.1, 
    '78 km al SUROESTE de LAS CHOAPAS, VER', '2025-02-01', '06:57:22', 'verificado',
    uuid_generate_v4(), NULL, ST_SetSRID(ST_MakePoint(-94.4270, 17.2820), 4326));

