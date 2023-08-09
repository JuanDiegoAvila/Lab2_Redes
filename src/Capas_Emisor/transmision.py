import socket

class Transmision(object):
    def __init__(self, mensaje, algoritmo):
        self._direccion = 'localhost'
        self._puerto = 5000
        self.transmitir(mensaje+' | '+algoritmo)

    
    def transmitir(self, mensaje):
        print(" Transmitiendo mensaje ...\n")
        emisor = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        emisor.connect((self._direccion, self._puerto))
        emisor.send(mensaje.encode('utf-8'))
        emisor.close()

        print('\nMensaje enviado a receptor: ', mensaje)
