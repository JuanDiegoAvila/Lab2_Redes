import random
from .transmision import *

class Ruido(object):
    def __init__(self, mensaje, algoritmo, final = False):
        self._mensaje = mensaje
        self.agregar_ruido()
        Transmision(self._mensaje, algoritmo, final)
        

    def agregar_ruido(self):
        print(" Agregando ruido ...\n")
        mensaje_list = []
        # mensaje_list = self._mensaje.split("")
        for c in self._mensaje:
            mensaje_list.append(c)
            
        for element in range(len(mensaje_list)):
            prob = random.randint(0, 99)
            if prob == 0:
                print('Se agrego ruido...')
                if mensaje_list[element] == '0':
                    mensaje_list[element] = '1'

                else:
                    mensaje_list[element] = '0'
        
        nuevo = ''.join(mensaje_list)
        self._mensaje = nuevo


