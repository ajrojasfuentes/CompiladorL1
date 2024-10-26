Cómo ejecutar el programa desde la línea de comandos:

1. Una vez en la línea de comando, navegue entre sus carpetas para ubicarse en la carpeta "app" dentro del proyecto.

2. Compile el programa con el siguiente comando: javac -d . main/miCompilador.java

3. Una vez compilado, ejecute el programa con el siguiente comando (reemplazando la ruta del archivo con la ubicación de su archivo de entrada y su archivo de salida):
   java -cp . main.miCompilador "ruta a su archivo de entrada .txt" "ruta a su archivo de salida .txt"

Ejemplo:
   java -cp . main.miCompilador "/home/val/TEC/Compi/CompiladorL1/sintCorrecto3.txt" "/home/val/TEC/Compi/CompiladorL1/app/PruebaCorrecta3.txt"
   
Con esto va a recibir la respuesta en consola, además se generará su archivo de salida en la ruta indicada y se actualizará el archivo tablaDeSimbolos.txt que se encuentra en la carpeta app.
