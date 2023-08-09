from .enlace import *

class Presentacion(object):
    def __init__(self, mensaje_original, algoritmo):
        self._mensajeOriginal = mensaje_original
        self._mensajeCodificado = None
        self._algoritmo = algoritmo
        self.codificar_mensaje()
        self._enlace = Enlace(self._mensajeCodificado, self._algoritmo)


    
    def codificar_mensaje(self):
        print(" ----------- Codificando mensaje -----------\n")
        codificacion = ''
        for caracter in self._mensajeOriginal:
            valor_ascii = ord(caracter)
            representacion_binaria = bin(valor_ascii)[2:]
            codificacion += representacion_binaria+' '

        print('\nMensaje codificado: ', codificacion)
        self._mensajeCodificado = codificacion

