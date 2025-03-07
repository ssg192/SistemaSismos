DROP DATABASE IF EXISTS "sismos";
CREATE DATABASE "sismos";

BEGIN
\c sismos;
CREATE EXTENSION postgis;
\i registros-sismos.sql
\i placas-tectonicas.sql

UPDATE registros_sismos AS rs
SET placa_id = pt.id
FROM placas_tectonicas AS pt
WHERE ST_Contains(pt.geom, rs.geom);

COMMIT;




