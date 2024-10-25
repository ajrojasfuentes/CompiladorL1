Cómo ejecutar el programa desde la línea de comandos:

1. Una vez en la línea de comando, navegue entre sus carpetas para ubicarse en la carpeta "app" dentro del proyecto.

2. Compile el programa con el siguiente comando: javac -cp . main/*.java

3. Una vez compilado, ejecute el programa con el siguiente comando (reemplazando la ruta del archivo con la ubicación de su archivo de entrada):
   java -cp . main.miCompilador "ruta a su archivo.txt"

Ejemplo:
   java -cp . main.miCompilador "C:\Users\Vale\Documents\TEC\IIS 2024\Compi\ProyectoCompi\Proyecto1_AnalisisLexico_Subgrupo 1_IIS2024\lexCorrecto1.txt"
   
Con esto va a recibir ya sea la tokenización o el mensaje de error según corresponda.