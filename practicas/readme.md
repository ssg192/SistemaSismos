# Equipo: Error capa 8

## Integrantes

- Sandoval Garibay Salvador 
- Redondo González Omar 
- Hernández Gutiérrez Gerardo
- Ayala Chacón David 


## Índice
1. [Levantar Dockerfile y Docker-compose](#levantar-dockerfile-y-docker-compose)
2. [Usuario](#usuario)
3. [URL](#url)
4. [Ejecución](#ejecución)
5. [Vista Administrador](#vista-administrador)
6. [Vista Usuario](#vista-usuario)
7. [Vista Docker](#vista-docker)
8. [Pruebas de Conexión](#pruebas-de-conexión)
9. [Pruebas Automáticas](#pruebas-automáticas)
10. [Renderización en Quarkus](#renderización-en-quarkus)



# Levantar Dockerfile y Docker-compose
Para poder levantar los contenedores primero debes de posicionarte en el nivel en donde se encuentran los archivos, una manera de guiarse es si ves las carpetas ```src y target``` estas en la posición correcta.
![imagen](https://github.com/user-attachments/assets/078c9b13-5607-4f78-addb-5ed728813166)
Primero levantas el dockerfile utilizando ```docker build -t tarea3 .```seguido despues el docker compose solo es  ```docker compose up``` EN CASO DE TENER LOS PUERTOS 8080 Y 3306 OCUPADOS FAVOR DE LIBERARLOS 
# Usuario
Por predeterminado viene un usuario como admin el cual su correo es ```ssandovalgaribay@gmail.com``` y su contraseña: ```Salvador@123```
# URL
la url para este caso es http://localhost:8080/inicio/login#
# Ejecución 
De primera entrada cuando entremos a la url lo primero que veremos sera un login en donde tenemos dos opciones iniciar sesión o registrar una persona, en cierta forma en el registro se debe de poner la contraseña con mayusculas, minusculas y un caracter especial.

![img_5.png](images/img_5.png)

![img_6.png](images/img_6.png)

 # Vista Administrador

Al ser administrador la principal vista sera nuestro perfil con los datos de nombre y correo teniendo un menu del lado izquierdo el cual podemos cerrar sesion, registrar un usuario y visualizar los perfiles registrados.
![img_5.png](images/img_5.png)

![img.png](img.png)

![img_1.png](images/img_1.png)

![img_2.png](images/img_2.png)

![img_3.png](images/img_3.png)

![img_4.png](images/img_4.png)

![imagen](https://github.com/user-attachments/assets/c12b64b7-f4c5-4d88-9dcd-5fcc0a998697)

![imagen](https://github.com/user-attachments/assets/0b093305-d343-4ae6-9b5d-aa80a7d874fd)




# Vista Usuario
En caso de ser usuario, la vista cambiara a solo ver los datos de perfil, en este caso el menu no tiene alguna interacción a excepción de cerrar sesión pero  lo de mas esta a sujeto a futuras opciones.

![img_7.png](images/img_7.png)
![img_8.png](images/img_8.png)


# Vista Docker
Cuando ejecutamos el Dockerfile como tal nos va a descargar el jdk 17 y mapear el jar a las carpetas deployment del mismo docker 
![imagen](https://github.com/user-attachments/assets/7684b44d-e1f6-4489-a91e-a2a4fdfdaa18)

Para el segundo que es el docker compose nos va a estar ejecutando el mysql que a su ves le pusimos que buscara en la carpeta src/db/db.sql y mapeara ese archivo en la carpeta de /var/lib/mysql para que ejecute ese archivo y como tal habilitando los puertos 8080 y 3306 para el uso de nuestra aplicación.

![imagen](https://github.com/user-attachments/assets/b3b813cb-2da1-41dc-909a-9bb4762f1fc3)


Algunas interacciones como tal en el sistema gracias al ORM de java nos puede dar una pequeña visualización en cada endpoint que nosotros interactuemos.

![imagen](https://github.com/user-attachments/assets/dda384c2-0b9e-49c8-be13-32590ee6134e)

# Pruebas de conexión 
La base de datos como esta en uno de los contenedores podemos visualizar en primera instancia poniendo el comando ```docker ps``` asi listandonos los contenedores que tenemos ejecutados, para ingresar a ellos basta poniendo ```docker exec -it id-contenedor mysql -u root -p``` este ultimo argumento en este caso es para entrar directo a mysql porque podriamos ingresar usando lo mismo pero al final poniendo bash para el uso del kernel y acceso de las carpetas.

![imagen](https://github.com/user-attachments/assets/d80b120a-ff21-4bc9-8ccc-f71081b433ef)

# Pruebas automáticas
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

# Renderización en Quarkus

Como tal quarkus al no tener una compatibilidad con thymeleaf tiene su propia dependencia el cual puede renderizar los html's el cual se deben de usar como si fueran controladores pero retornando el html creado.

![imagen](https://github.com/user-attachments/assets/9e9458e8-bf05-40e9-b423-a8f49adaa0ee)
