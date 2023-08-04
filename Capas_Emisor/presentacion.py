class Presentacion(object):
    def __init__(self, mensaje_original):
        self._mensajeOriginal = mensaje_original
        self._mensajeCodificado = None
    
    def codificar_mensaje(self):

        codificacion = ''
        for caracter in self._mensajeOriginal:
            valor_ascii = ord(caracter)
            representacion_binaria = bin(valor_ascii)[2:]
            codificacion += representacion_binaria+' '
    
        self._mensajeCodificado = codificacion
        return codificacion

mensaje = input('Ingrese el mensaje a codificar: ')
presentacion = Presentacion(mensaje)
print(presentacion.codificar_mensaje())
