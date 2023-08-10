from .presentacion import *

class Aplicacion(object):
    def __init__(self):
        self._algoritmos = ['Hamming', 'CRC32']
        self._algoritmo = None
        self._mensaje = None
    
    def solicitar_mensaje(self, algoritmo, mensaje):
        self._mensaje = mensaje

        n_char = 0
        for char in mensaje:
            
            n_char += 1
            if n_char == len(mensaje):
                Presentacion(char, algoritmo=algoritmo, final=True)
                break

            Presentacion(char, algoritmo=algoritmo)
            

