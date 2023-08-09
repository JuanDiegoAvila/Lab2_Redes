from .presentacion import *

class Aplicacion(object):
    def __init__(self):
        self._algoritmos = ['Hamming', 'CRC32']
        self._algoritmo = None
        self._mensaje = None
    
    def solicitar_mensaje(self):
        print(" ----------- Solicitando mensaje -----------\n")
        print("\n Seleccione el algoritmo a utilizar\n")
        for i in range(len(self._algoritmos)):
            print("\t[ "+str(i+1) + " ] " + self._algoritmos[i])
        opcion = int(input("\nOpcion: "))

        mensaje = input("\nIngrese el mensaje a enviar: ")
        self._mensaje = mensaje

        for char in mensaje:
            presentacion = Presentacion(char, algoritmo=self._algoritmos[opcion-1])

