import sys
import os

ruta_padre = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.append(ruta_padre)

from Algoritmos.emisorAL import Hamming, CRC32

class Aplicacion(object):
    def __init__(self):
        self._algoritmos = ['Hamming', 'CRC32']
        self._algoritmo = None
        self._mensaje = None
    
    def solicitar_mensaje(self):
        print("\n Seleccione el algoritmo a utilizar\n")
        for i in range(len(self._algoritmos)):
            print("\t[ "+str(i+1) + " ] " + self._algoritmos[i])
        opcion = int(input("\nOpcion: "))

        mensaje = input("\nIngrese el mensaje a enviar: ")
        self._mensaje = mensaje

        if opcion == 1:
            self._algoritmo = Hamming(mensaje)
        elif opcion == 2:
            self._algoritmo = CRC32(mensaje)