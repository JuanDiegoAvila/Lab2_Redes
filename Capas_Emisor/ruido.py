import random
class Ruido(object):
    def __init__(self, mensaje):
        self._mensaje = mensaje

    def agregar_ruido(self):

        for element in range(len(self._mensaje)):
            prob = random.randint(0, 99)
            
            if prob == 0:
                if self._mensaje[element] == '0':
                    self._mensaje[element] = '1'

                else:
                    self._mensaje[element] = '0'
        


