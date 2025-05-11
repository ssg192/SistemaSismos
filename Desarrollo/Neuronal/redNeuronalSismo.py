from fastapi import FastAPI
from fastapi.responses import JSONResponse
import pandas as pd
import tensorflow as tf
import requests

app = FastAPI()

url = 'http://localhost:8080/inicio'
response = requests.get(url)
data = response.json()

df = pd.DataFrame(data)
df['año'] = pd.to_datetime(df['fecha']).dt.year
df['mes'] = pd.to_datetime(df['fecha']).dt.month

X = df[['latitud', 'longitud', 'profundidad']]
y = df[['año', 'mes', 'magnitud']]

modelo = tf.keras.Sequential([
    tf.keras.layers.Dense(8, activation='relu', input_shape=[3]),
    tf.keras.layers.Dense(3)
])

modelo.compile(optimizer=tf.keras.optimizers.Adam(0.1), loss='mean_squared_error')
modelo.fit(X, y, epochs=200, verbose=False)

@app.get("/predicciones")
def get_predicciones():
    predicciones = modelo.predict(X)
    resultados = pd.DataFrame(predicciones, columns=['año', 'mes', 'magnitud'])
    resultados['año'] = resultados['año'].round().astype(int)
    resultados['mes'] = resultados['mes'].round().astype(int)
    resultados['magnitud'] = resultados['magnitud'].round(2)
    return JSONResponse(content=resultados.to_dict(orient='records'))
