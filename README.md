# TODO LIST
## Documentacion
El presente proyecto corresponde al trabajo practico arquitectura web de la universidad de palermo, se dispone de un [informe](./documentacion/Informe%20Trabajo%20Practico%20Arquitectura%20Web%20Lucas%20Javier%20Martinez.pdf)
en el cual se detalla el alcance, tecnologias y otros detalles del proyecto. Por otro lado, tambien se deja disponible una [coleccion de postman](./documentacion/coleccion_postman.json) para que puedan verse todos los endpoints y probar su funcionalidad. Finalmente, se dejan los [requerimientos](enunciado.md) que debia cumplir el presente proyecto
## Ejecucion del proyecto
Para la ejecucion del proyecto se tienen dos caminos, dependiendo si se cuenta con la herramienta [docker](https://www.docker.com/products/docker-desktop/) instalada o no, en esta guia exploraremos ambas alternativas, sin embargo es muy recomendable optar por la opcion de docker ya que simplifica mucho la ejecucion
### Ejecucion con docker
En caso de contar con docker, simplemente debemos correr el comando `docker-compose up` en la raiz del proyecto y veremos la aplicacion disponible en la url http://localhost:5173/
### Ejecucion sin docker
Para poder correr el proyecto sin utilizar docker, se debera tener instaladas las siguientes dependencias
- [mysql](https://www.mysql.com/downloads/)
- [jdk 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [maven 3.8.4](https://maven.apache.org/docs/3.8.4/release-notes.html)
- [nodejs](https://nodejs.org/en)
- [npm](https://docs.npmjs.com/cli/v8/commands/npm-install)

Una vez que esten instaladas todas estas dependencias, debemos asegurarnos que el servicio mysql esta corriendo, cuando hayamos chequeado esto, debemos modificar el archivo [.env.example](backend/.env.example) eliminando .example y verificando que el puerto de la url sea el que efectivamente esta siendo usado por mysql, en caso de que esto no funcione, tambien es posible escribir directamente la url en el archivo [application.properties](backend/src/main/resources/application.properties).
Para poder iniciar la api debemos movernos al directorio backend y ejecutar el comando `mvn clean install` seguido de `mvn spring-boot:run`, esto no seria necesario si se cuenta con un IDE como [intellij](https://www.jetbrains.com/idea/) que ejecuta estos pasos por nosotros
Finalmente, para poder ejecutar el frontend debemos ir al directorio frontend y ejecutar el comando `npm install` seguido de `npm run dev` para ver la aplicacion funcionando en la url http://localhost:5173/
