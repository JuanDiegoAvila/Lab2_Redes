import random
from .transmision import *

class Ruido(object):
    def __init__(self, mensaje, algoritmo):
        self._mensaje = mensaje
        self.agregar_ruido()
        Transmision(self._mensaje, algoritmo)
        

    def agregar_ruido(self):
        print(" Agregando ruido ...\n")
        mensaje_list = self._mensaje.split()

        for element in range(len(mensaje_list)):
            prob = random.randint(0, 99)
            if prob == 0:
                print('Se agrego ruido...')
                if mensaje_list[element] == '0':
                    mensaje_list[element] = '1'

                else:
                    mensaje_list[element] = '0'
        
        self._mensaje = ''.join(mensaje_list)


