import random
import string
from Capas_Emisor.aplicacion import *

def generar_cadena_aleatoria(longitud_maxima=20):
    longitud = random.randint(1, longitud_maxima)

    return ''.join(random.choice(string.ascii_letters) for _ in range(longitud))

algoritmos = ['Hamming', 'CRC32']

aplicacion = Aplicacion()


with open("./enviado.txt", "w") as f:
    for i in range(10000):
        str = generar_cadena_aleatoria()
        algoritmo = random.choice(algoritmos)
        aplicacion.solicitar_mensaje(algoritmo, str)
        f.write(str + " " + algoritmo+"\n")
f.close()

