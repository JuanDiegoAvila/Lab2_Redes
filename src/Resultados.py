import matplotlib.pyplot as plt

path1 = './src/enviado.txt'

enviados = []
with open(path1, 'r') as file:
    for linea in file:
        linea_limpia = linea.strip()
        linea = linea_limpia.split(" ")
        enviados.append(linea)

path2 = './src/reconocido.txt'

reconocidos = []
with open(path2, 'r') as file:
    for linea in file:
        linea_limpia = linea.strip()
        linea = linea_limpia.split(" ")
        reconocidos.append(linea)

aciertos_hamming = 0
aciertos_crc32 = 0
errores_hamming = 0
errores_crc32 = 0
for reconocido in reconocidos:
    if reconocido[1] == "Hamming":
        if reconocido[0] != "ERROR":
            aciertos_hamming += 1
        else:
            errores_hamming += 1
    else:
        if reconocido[0] != "ERROR":
            aciertos_crc32 += 1
        else:
            errores_crc32 += 1

import matplotlib.pyplot as plt

# Datos
metodos = ['Hamming', 'CRC32']
aciertos = [aciertos_hamming, aciertos_crc32]
errores = [errores_hamming, errores_crc32]

barWidth = 0.35
r1 = range(len(aciertos))
r2 = [x + barWidth for x in r1]

plt.bar(r1, aciertos, width=barWidth, color='blue', edgecolor='grey', label='Aciertos')
plt.bar(r2, errores, width=barWidth, color='red', edgecolor='grey', label='Errores')

plt.xlabel('Métodos', fontweight='bold', fontsize=15)
plt.xticks([r + barWidth for r in range(len(aciertos))], metodos)

plt.title('Comparación de Aciertos y Errores entre Hamming y CRC32')
plt.legend()

plt.show()

desempeno_Hamming = {}
desempeno_CRC32 = {}

for enviado, reconocido in zip(enviados, reconocidos):
    if enviado[1] == "Hamming":
        desempeno_Hamming[len(enviado[0])] = desempeno_Hamming.get(len(enviado[0]), 0)
        if reconocido[0] == "ERROR":
            desempeno_Hamming[len(enviado[0])] += 1
    else:
        desempeno_CRC32[len(enviado[0])] = desempeno_CRC32.get(len(enviado[0]), 0)
        if reconocido[0] == "ERROR":
            desempeno_CRC32[len(enviado[0])] += 1



sorted_keys_hamming = sorted(desempeno_Hamming.keys())
sorted_values_hamming = [desempeno_Hamming[key] for key in sorted_keys_hamming]

sorted_keys_crc32 = sorted(desempeno_CRC32.keys())
sorted_values_crc32 = [desempeno_CRC32[key] for key in sorted_keys_crc32]

plt.figure(figsize=(12, 5))
plt.plot(sorted_keys_hamming, sorted_values_hamming, label="Hamming", marker='o', color='blue')
plt.title("Desempeño de Hamming")
plt.xlabel("Tamaño de la cadena")
plt.ylabel("Errores")
plt.legend()
plt.grid(True)
plt.gca().xaxis.set_major_locator(plt.MaxNLocator(integer=True))  # Eje x con solo números enteros
plt.show()


plt.figure(figsize=(12, 5))
plt.plot(sorted_keys_crc32, sorted_values_crc32, label="CRC32", marker='x', color='red')
plt.title("Desempeño de CRC32")
plt.xlabel("Tamaño de la cadena")
plt.ylabel("Errores")
plt.legend()
plt.grid(True)
plt.gca().xaxis.set_major_locator(plt.MaxNLocator(integer=True))  # Eje x con solo números enteros
plt.show()



        