import numpy as np

def hamming(mensaje):
    bits_paridad = 0
    cantidad_bits = len(mensaje)

    # Calculo de bits de paridad
    while 2**bits_paridad < cantidad_bits + bits_paridad + 1:
        bits_paridad += 1

    # print(f"Bits de paridad segun cantidad de bits de {cantidad_bits} : {bits_paridad} ")

    tamaño = bits_paridad + cantidad_bits

    # Definir mensaje final
    mensaje = list(mensaje)
    mensaje_final = np.zeros(tamaño, dtype=int)

    # Calculo de bits de paridad
    paridades = []
    for i in range(bits_paridad):
        paridades.append(2**i)
    
    tabla = []

    for paridad in paridades:
        fila = []
        for i in range(len(mensaje_final)):
            if i+1 & paridad == paridad:
                fila.append(i+1)

        tabla.append(fila)

    # Recorrer mensaje_final al reves
    for indice, elemento in enumerate(reversed(mensaje_final)):
        if indice+1 not in paridades:
            mensaje_final[indice] = mensaje.pop()

    # Ver si las posiciones tienen paridad par
    for paridad in tabla:
        unos = 0
        for indice, elemento in enumerate(mensaje_final):
            if indice+1 in paridad:
                if elemento == 1:
                    unos += 1
        if unos % 2 == 0:
            mensaje_final[paridad[0]-1] = 0
        else:
            mensaje_final[paridad[0]-1] = 1

    mensaje_final = mensaje_final[::-1]
    mensaje_final = " ".join(str(x) for x in mensaje_final)
    print(f"Mensaje con bits de paridad: {mensaje_final}")

    # Guardar mensaje en archivo para el receptor
    with open("./hamming.txt", "w") as f:
        f.write(mensaje_final)
    f.close()


def crc_32(mensaje):
    mensaje = list(mensaje)
    mensaje_final = np.zeros(32 + len(mensaje), dtype=int)

    # Agregar mensaje inicial al comienzo del mensaje final
    for i in range(len(mensaje)):
        mensaje_final[i] = mensaje[i]

    estandar_grados = [1, 2, 4, 5, 7, 8, 10, 11, 12, 16, 22, 23, 26, 32]
    codigo = np.zeros(32, dtype=int)

    for i in range(32):
        if i+1 in estandar_grados:
            codigo[i] = 1

    codigo = codigo[::-1]
    codigo = np.append(codigo, 1)

    print(codigo)
    print(mensaje_final)

    # XOR entre mensaje y codigo
    termino = False
    temp_resultado = []
    temp_mensaje = mensaje_final[:len(codigo)]
    indice = len(codigo)

    while not termino:
        xor = [mensaje ^ codigo for mensaje, codigo in zip(temp_mensaje, codigo)]

        if indice >= len(mensaje_final):
            termino = True
        
        else:
            temp_mensaje = xor

            for element in temp_mensaje:
                if element == 0:
                    temp_mensaje.remove(element)
                else:
                    break
            
            if len(temp_mensaje) < len(codigo):
                faltantes = len(codigo) - len(temp_mensaje)
                for i in range(faltantes):
                    temp_mensaje = np.append(temp_mensaje, mensaje_final[indice])
                    indice += 1
        
        temp_resultado = xor
    
    print(temp_resultado)

def crc_323(mensaje, polinomio = 32):
    mensaje = list(mensaje)
    mensaje_final = np.zeros(polinomio + len(mensaje), dtype=int)

    # Agregar mensaje inicial al comienzo del mensaje final
    for i in range(len(mensaje)):
        mensaje_final[i] = mensaje[i]

    estandar_grados = [1, 2, 4, 5, 7, 8, 10, 11, 12, 16, 22, 23, 26, 32]
    codigo = np.zeros(polinomio, dtype=int)

    for i in range(polinomio):
        if i+1 in estandar_grados:
            codigo[i] = 1

    codigo = codigo[::-1]
    codigo = np.append(codigo, 1)

    # XOR entre mensaje y codigo
    termino = False
    temp_mensaje = mensaje_final[:len(codigo)]
    indice = len(codigo)

    while not termino:
        xor = [mensaje ^ codigo for mensaje, codigo in zip(temp_mensaje, codigo)]
        temp_mensaje = xor
        if indice >= len(mensaje_final):
            termino = True
        
        else:
            hay_uno = False
            for i in range(len(temp_mensaje)):
                if temp_mensaje[i] == 1:
                    hay_uno = True
                if temp_mensaje[i] == 0 and not hay_uno:
                    temp_mensaje[i] = None

            temp_mensaje = [x for x in temp_mensaje if x is not None]
            
            if len(temp_mensaje) < len(codigo):
                faltantes = len(codigo) - len(temp_mensaje)
                for i in range(faltantes):

                    if indice >= len(mensaje_final):
                        break

                    temp_mensaje = np.append(temp_mensaje, mensaje_final[indice])
                    indice += 1
    

    # agarrar los ultimos polinomio bits del mensaje final
    trama = temp_mensaje[-polinomio:]

    # Mensaje final es el mensaje inicial + la trama
    mensaje_final = np.append(mensaje, trama)
    
    mensaje_final = " ".join(str(x) for x in mensaje_final)
    print(f"Mensaje con trama: {mensaje_final}")

    # Guardar mensaje en archivo para el receptor
    with open("./crc_32.txt", "w") as f:
        f.write(mensaje_final)
    f.close()



# Elegir entre hamming o crc_32
opcion = 0
while opcion != "3":
    print(" [ 1 ] Hamming")
    print(" [ 2 ] CRC-32")
    print(" [ 3 ] Salir")
    opcion = input("\nIngrese una opcion: ")

    if opcion == "1":
        print("\n================== Hamming ==================\n")
        in_ = input("Ingrese la cadena de bits: ")
        hamming(in_)
        print("\n=============================================\n")
    
    elif opcion == "2":
        print("\n================== CRC-32 ===================\n")
        in_ = input("Ingrese la cadena de bits: ")
        crc_323(in_)
        print("\n=============================================\n")