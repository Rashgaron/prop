Para poder correr la aplicación, hay que poner en la raiz de nuestro ordenador
la librería javafx correspondiente con nuestro sistema operativo.
Estas pueden encontrarse en la carpeta : /LibreriasJavafx


Una vez hecho esto, dentro de intellij: File/ProjectStructure
Entramos en Libraries, hacemos click en el icono de + y agregamos la ruta de
la carpeta lib dentro de la librería que hemos copiado a nuestra raiz.
Ejemplo: C:\javafx-sdk-11.0.2\lib


Una vez hecho esto, iremos a Run/Edit Configurations

Haremos click en Modify options / add vm options

y dentro del campo de vm, introduciremos esta línea: 

--module-path "Sustituye por la ruta a la carpeta lib de la libreria" --add-modules
javafx.controls,javafx.fxml


En caso de que la ruta tenga espacios en blanco, deberás ponerla entre
comillas como en el ejemplo.

En caso de que esto falle o sea confuso, adjunto un link donde se explica:


https://www.jetbrains.com/help/idea/javafx.html
