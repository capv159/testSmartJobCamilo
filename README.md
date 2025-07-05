# Aplicación Registro de usuarios
El siguiente repositorio tiene la finalidad de brindar una solución al reto propuesto por la compañía smartJob

## Diagramas :computer:
Casos de uso:

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/casos_uso.PNG)

Paquetes:

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/paquetes.PNG)

Secuencia:

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/secuencia.PNG)

## Comenzando 🚀

- Para la solución del reto se planteo utilizar el lenguaje java en su versión 17 la cual es libre para temas de licenciamiento en ambientes productivos.

- El framework utilizado fue springBoot en su versión 3.1.11.

- La herramienta de compilación utilizada fue Apache Maven.

- El servicio de base de datos fue H2

El código final se encuentra en **máster**

### Pre-requisitos 📋

Se debe validar la instalación de java

```
java -version
```

Se debe validar la instalación de maven

```
mvn - version
```

En el archivo application.properties se encuentra la configuración necesaria para la conexión a la base de datos.

#### Instalación 🔧

Con maven instalado en nuestro entorno ejecutamos dentro de la carpeta del proyecto:

```
mvn compile
```

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/mvn_compile.PNG)

```
mvn package
```
![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/mvn_pakcage1.PNG)

se ejectutaran algunos test y descargaran dependencias

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/mvn_pakcage2.PNG)

luego en la carpeta target del proyecto:
```
java -jar testSmartJobCamilo-0.0.1-SNAPSHOT.jar
```
![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/java_jar.PNG)

## Cobertura de código 📋

ejecutar en la carpeta del proyecto:
```
mvn test
```
Luego para validar cobertura de código abrir el archivo index.html de esta ruta
```
\testSmartJobCamilo\target\reporte
```
![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/coverage.PNG)

## Entorno de Base de datos ⚙️
Esta aplicación funciona con una base de datos relacional en memoria llamada h2, para ingresar a la gestión de la misma se debe ingresar por medio de un navegador web con los siguientes datos
```
JDBC URL: jdbc:h2:mem:test_bd
User Name: sa
Password: sa
```
![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/bd_login.PNG)

## Ejecutando los servicios ⚙️

la documentacion de los servicios se encuentra aqui: [Swagger Servicios](https://github.com/capv159/imagenes/blob/main/smartJob/swaggerServicios.yml) esta dicumentacion puede ser validada en: https://editor.swagger.io/

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/swagger.PNG)

### Proyecto Postman  🔧

[Descargar proyecto Postman](https://github.com/capv159/imagenes/blob/main/smartJob/testSmartJobCamilo.postman_collection.json)

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/postman.PNG)

### Seguridad Servicios :space_invader:
Todos los enpoint necesitan un token para funcionar excepto los endpoint de resgitro de ususarios y login, es por esto que primero se debe crear un usuario y luego con el token devuelto se puede consumir los otros servicios d ela siguiente manera:

![Image text](https://github.com/capv159/imagenes/blob/main/smartJob/consumo_jwt.PNG)

## Autores ✒️

* ** Camilo Andres Perez Valderrama ** 

## Gracias 🎁

* gracias a [Villanuevand](https://github.com/Villanuevand) 😊 por su excelente plantilla
