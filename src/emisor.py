from Capas_Emisor.aplicacion import *

emisor = Aplicacion()

opcion = 0
while opcion != 2:
    print('[ 1 ] Enviar mensaje')
    print('[ 2 ] Salir')
    opcion = int(input('Opcion: '))

    if opcion == 1:
        mensaje = input('Mensaje: ')
        algoritmos = ['Hamming', 'CRC32']
        for i in range(len(algoritmos)):
            print(f'[ {i+1} ]  {algoritmos[i]}')
        algoritmo = int(input('Algoritmo: '))
        emisor.solicitar_mensaje(algoritmos[algoritmo-1], mensaje)

    elif opcion == 2:
        break
    else:
        print('Opcion no valida')
emisor.solicitar_mensaje()

