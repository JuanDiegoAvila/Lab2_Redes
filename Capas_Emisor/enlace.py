
from aplicacion import Aplicacion

class Enlace(object):
    def __init__(self, mensaje_binario, algoritmo):
        self._algoritmo = algoritmo
        self._mensaje_binario = mensaje_binario
        self.mensaje_nuevo = None

    def calcular_integridad(self):
        self.mensaje_nuevo = self.algoritmo.calcular()
        