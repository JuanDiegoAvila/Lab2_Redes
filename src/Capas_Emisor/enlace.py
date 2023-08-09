import sys
import os

ruta_padre = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
sys.path.append(ruta_padre)

from Algoritmos.emisorAL import Hamming, CRC32

from .ruido import *

class Enlace(object):
    def __init__(self, mensaje_binario, algoritmo):
        self._algoritmo = algoritmo
        self._mensaje_binario = str(mensaje_binario)
        self.mensaje_nuevo = None
        self.calcular_integridad()
        Ruido(self._mensaje_final, self._algoritmo)

    def calcular_integridad(self):
        print(" Calculando integridad ...\n")
        if self._algoritmo == 'Hamming':
            self._mensaje_final = Hamming(self._mensaje_binario).calcular()

        elif self._algoritmo == 'CRC32':
            self._mensaje_final = CRC32(self._mensaje_binario).calcular()

        