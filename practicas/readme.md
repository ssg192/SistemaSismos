# Equipo: Error capa 8

## Integrantes

- Sandoval Garibay Salvador 
- Redondo González Omar 
- Hernández Gutiérrez Gerardo
- Ayala Chacón David 


## Índice
1. [Propósito del sistema](#propósito del sistema)
2. [Levantar Dockerfile y Docker-compose](#levantar-dockerfile-y-docker-compose)
3. [Usuario](#usuario)
4. [URL](#url)
5. [Ejecución](#ejecución)
6. [Pruebas](#pruebas)



# Propósito del sistema 

# Levantar Dockerfile y Docker-compose
Primero necesitamos clonar el proyecto mediante el cmd ```git clone url_repo ```,para poder levantar los contenedores  primero debes de posicionarte en el nivel en donde se encuentran los archivos que es ```/TareasIngSoftware/Tarea3/Tarea3 ``` , una manera de guiarse es si ves las carpetas ```src y target``` estas en la posición correcta.
![imagen](https://github.com/user-attachments/assets/078c9b13-5607-4f78-addb-5ed728813166)
Primero levantas el dockerfile utilizando ```docker build -t tarea3 .```seguido despues el docker compose solo es  ```docker compose up``` EN CASO DE TENER LOS PUERTOS 8080 Y 3306 OCUPADOS FAVOR DE LIBERARLOS 
# Usuario
Por predeterminado viene un usuario como admin el cual su correo es ```ssandovalgaribay@gmail.com``` y su contraseña: ```Salvador@123```
# URL
la url para este caso es http://localhost:8080/inicio/login# y su prueba para los endpoints es http://localhost:8080/q/swagger-ui/#/ 
# Ejecución 
De primera entrada cuando entremos a la url lo primero que veremos sera un login en donde tenemos dos opciones iniciar sesión o registrar una persona, en cierta forma en el registro se debe de poner la contraseña con mayusculas, minusculas y un caracter especial.

![img_5.png](images/img_5.png)

![img_6.png](images/img_6.png)

# Guia técnica básica


# Pruebas
Como tal para esta ocasión evitarnos el uso de postman o aplicar curl's se uso swagger-ui el cual mediante los ``@restcontrollers`` nos puede dar una visualizacion a cada uno.

![imagen](https://github.com/user-attachments/assets/a1065487-4aed-49d1-a2e3-cb874ab67846)

En este apartado en especifico como se utilizo JWT y roles se deben autorizar por lo que cuando iniciamos sesión se adjunta el token con la informacion del usuario o administrador, en este caso en el swagger al ingresar un nuevo usuario o iniciar sesion solo es copiar el token y ponerlo ahi, OJO algunas funciones estan activas dependiendo las operaciones que pueden ejecutar, en caso de que no solo manda un error 403 el cual no estamos autorizados.

![imagen](https://github.com/user-attachments/assets/ba5e0cce-8311-4bcd-8c4f-8b6a27e36622)

![imagen](https://github.com/user-attachments/assets/6e35b709-d8fc-46da-8bf2-8a4663dd9d4b)

![imagen](https://github.com/user-attachments/assets/9ee78894-78b0-4600-bf72-dc900e3aa462)

![imagen](https://github.com/user-attachments/assets/274d24c6-384d-4c68-958c-d2ed103625cd)

![imagen](https://github.com/user-attachments/assets/e0f72d01-c555-4b45-b6f0-a45d874700a3)

En otras si se puede como la visualización de los perfiles de cada quien
![imagen](https://github.com/user-attachments/assets/1e45d61d-39a7-4c08-8d26-ae5f91bdaf95)

Al ser administrador se nos habilitan los demas metodos que podemos interactuar con ellos
![imagen](https://github.com/user-attachments/assets/8deabbba-0f59-4449-9348-ec24b29b2b87)

![imagen](https://github.com/user-attachments/assets/6385017c-76b4-45a9-b8dc-06908e20df7f)

![imagen](https://github.com/user-attachments/assets/2165349d-3de1-4879-8345-35a490e274d8)

![imagen](https://github.com/user-attachments/assets/a7c9a45f-becb-4d6b-aee4-0a5b53c82fb5)

