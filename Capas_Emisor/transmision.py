import socket

class Transmision(object):
    def __init__(self):
        self._direccion = 'localhost'
        self._puerto = 5000

    
    def transmitir(self, mensaje):
        
        emisor = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        emisor.connect((self._direccion, self._puerto))
        emisor.send(mensaje.encode('utf-8'))
        emisor.close()

        print('\nMensaje enviado a receptor: ', mensaje)