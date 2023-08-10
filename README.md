Para ejecutar el codigo se seguir el siguiente orden:
Dirigirse a la carpeta src y ejecutar el emisor:
```sh
$ cd src
$ python emisor.py
```

Elegir el algoritmo y la trama que va a utilizar para probar.

Luego se ejecuta el receptor:
```sh
$ cd src
$ javac receptor.py
$ java receptor.py
```
Si desea correr el script de pruebas unicamente ejecutar:
```sh
$ cd src
$ python generador.py
```
 
Recordar de ejecutar de primero el receptor y luego el emisor.
Se guardan los mensajes de salida en ./src/enviado.txt
Se guardan los mensajes de entrada de receptor en ./src/reconocido.txt

