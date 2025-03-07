DROP DATABASE IF EXISTS "sismos";
CREATE DATABASE "sismos";

BEGIN;
\c sismos;
\i placas_tectonicas.sql
\i registros_sismos.sql
COMMIT;

BEGIN;
\c sismos;
\dt registros_sismos
UPDATE registros_sismos AS rs
SET placa_id = pt.id
FROM placas_tectonicas AS pt
WHERE ST_Contains(pt.geom, rs.geom);

COMMIT;




