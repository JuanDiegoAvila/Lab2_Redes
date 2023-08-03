import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class receptor {

    public static String joinArrayList(ArrayList<Integer> list, String separator) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i < list.size() - 1) {
                result.append(separator);
            }
        }

        return result.toString();
    }

    public static boolean contains(int[] array, int value) {
        for (int num : array) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }

    public static boolean decode_crc32( List<Integer> temp_mensaje, int[] gradosInvertidos, int tamano, int indice, int[] mensajeOriginal){
        while(temp_mensaje.size() >= tamano){
            for (int i = 0; i < gradosInvertidos.length; i++) {
                temp_mensaje.set(i, temp_mensaje.get(i) ^ gradosInvertidos[i]);
            }
            //System.out.println("Mensaje: " + temp_mensaje.toString());

            while(temp_mensaje.get(0) == 0){
                temp_mensaje.remove(0);
                if(temp_mensaje.size() == 0){
                    //System.out.println("Retornando en true 1");
                    return true;
                }
            }


            if(temp_mensaje.size() < tamano && indice < mensajeOriginal.length){
                while(temp_mensaje.size() < tamano && indice < mensajeOriginal.length){
                    temp_mensaje.add(mensajeOriginal[indice]);
                    indice++;
                }
            }else{
                //System.out.println("Retornando en false 1");
                return false;
            }

            
        }
        if (temp_mensaje.size() != 0){
            //System.out.println("Retornando en false 2");
            return false;
        }
        //System.out.println("Retornando en true 2");
        return true;
    }

    public static void crc_32() {
        String fileName = "./crc_32.txt"; 

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Como solo hay una linea, se puede acceder a ella con el indice 0
        String[] leido = lines.get(0).split("");
        int[] mensaje = new int[leido.length];

        for (int i = 0; i < leido.length; i++) {
            mensaje[i] = Integer.parseInt(leido[i]);
            //System.out.print(mensaje[i]);
        }


        int[] estandar_grados = {0, 1, 2, 4, 5, 7, 8, 10, 11, 12, 16, 22, 23, 26, 32};
        int pol = 33; // Ejemplo: ajusta esto al tamaño necesario para tu caso
        int[] grados = new int[pol];
        for (int i = 0; i < pol; i++) {
            if (contains(estandar_grados, i)) {
                grados[i] = 1;
            } else {
                grados[i] = 0;
            }
        }

        int[] gradosInvertidos = new int[grados.length];
        int lastIndex = grados.length - 1;
        for (int i = 0; i < grados.length; i++) {
            gradosInvertidos[i] = grados[lastIndex - i];
        }

        for (int i = 0; i < gradosInvertidos.length; i++) {
            System.out.print(gradosInvertidos[i]);
        }
        System.out.println("\n");

        
        int tamano = gradosInvertidos.length;
        int indice = tamano;
        List<Integer> temp_mensaje = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            temp_mensaje.add(mensaje[i]);
        }
        System.out.println(temp_mensaje.size());
        boolean not_error = decode_crc32(temp_mensaje, gradosInvertidos, tamano, indice, mensaje);
        if(not_error){
            System.out.println("No hay error en el mensaje");
            System.out.println("\nMensaje original: ");
            int tamaño_original = mensaje.length - (gradosInvertidos.length-1);
            for (int i = 0; i < tamaño_original; i++) {
                System.out.print(mensaje[i]);
            }
        }else{
            System.out.println("Error en el mensaje");
        }
        
    } 

    public static void hamming() {
        String fileName = "./hamming.txt"; 

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Como solo hay una linea, se puede acceder a ella con el indice 0
        String[] mensaje = lines.get(0).split("");
        
        int tamaño = mensaje.length;

        String[] mensaje_r = new String[tamaño];
        for (int i = tamaño - 1; i >= 0; i--) {
            mensaje_r[i] = mensaje[tamaño - i - 1];
        }
        mensaje = mensaje_r;

        // Se obtiene el numero de bits de paridad
        int bits_paridad = 0;
        while(Math.pow(2, bits_paridad) < mensaje.length){
            bits_paridad++;
        }
        

        List<Integer> paridades = new ArrayList<>();
        for (int i = 0; i < bits_paridad; i++) {
            paridades.add(1 << i);
        }

        List<List<Integer>> tabla = new ArrayList<>();

        for (int paridad : paridades) {
            List<Integer> fila = new ArrayList<>();
            for (int i = 0; i < tamaño; i++) {
                if ((i + 1 & paridad) == paridad) {
                    fila.add(i + 1);
                }
            }
            tabla.add(fila);
        }

        String[] mensaje_revertido = new String[tamaño];
        for (int i = tamaño - 1; i >= 0; i--) {
            mensaje_revertido[i] = mensaje[tamaño - i - 1];
        }

        // imprimir la tabla
        List<List<Integer>> tabla_paridad = new ArrayList<>();
        for (int j = 0; j < tabla.size(); j++) {
            List<Integer> fila = new ArrayList<>();
            for (int k = 0; k < tabla.get(j).size(); k++) {
                for (int i = 0; i < mensaje_revertido.length; i++) {
                    if (tabla.get(j).get(k) == i + 1) {
                        fila.add(Integer.parseInt(mensaje_revertido[i]));
                    }
                }
            }   
            tabla_paridad.add(fila);
        }

        ArrayList<Integer> resultados = new ArrayList<>();
        for (int i = 0; i < tabla_paridad.size(); i++) {
            int unos = 0;

            for (int j = 0; j < tabla_paridad.get(i).size(); j++) {
                if (tabla_paridad.get(i).get(j) == 1) {
                    unos++;
                }
            }

            if (unos % 2 == 0) {
                resultados.add(0);
            } else {
                resultados.add(1);
            }
        }

        // volteamos el resultado
        ArrayList<Integer> resultados_revertidos = new ArrayList<>();
        for (int i = resultados.size() - 1; i >= 0; i--) {
            resultados_revertidos.add(resultados.get(i));
        }
        resultados = resultados_revertidos;


        int error = 0;
        for (int i = 0; i < resultados.size(); i++) {
            error += resultados.get(i);
        }

        if (error == 0) {
            System.out.println("No hay error en el mensaje");
            
            int tamaño_original = tamaño - bits_paridad;
            int tamaño_actual = 0;
            List<String> mensaje_original = new ArrayList<>();

            // recorrer el mensaje revertido
            // y solo agregar los bits que no son de paridad
            for (int i = 0; i < mensaje_revertido.length; i++) {
                boolean es_paridad = false;
                for (int j = 0; j < paridades.size(); j++) {
                    if (i + 1 == paridades.get(j)) {
                        es_paridad = true;
                    }
                }

                if (!es_paridad && tamaño_actual < tamaño_original) {
                    mensaje_original.add(mensaje_revertido[i]);
                    tamaño_actual++;
                }
            }

            System.out.println("\nMensaje original: ");
            // for (int i = mensaje_original.size() - 1; i >= 0; i--) {
            //     System.out.print(mensaje_original.get(i) + " ");
            // }
            for (int i = 0; i < mensaje_original.size(); i++) {
                System.out.print(mensaje_original.get(i) + " ");
            }


        } else {

            String bits = joinArrayList(resultados, "");
            int decimal = Integer.parseInt(bits, 2);
            
            // El error esta en la posicion decimal del mensaje original

            System.out.println("Error en el bit " + decimal + " del mensaje original");

            System.out.println("\nMensaje recibido: ");
            for (int i = mensaje_revertido.length - 1; i >= 0; i--) {
                System.out.print(mensaje_revertido[i] + " ");
            }

            // Se corrige el mensaje
            if (mensaje_revertido[decimal - 1].equals("1")) {
                mensaje_revertido[decimal - 1] = "0";
            } else {
                mensaje_revertido[decimal - 1] = "1";
            }

            System.out.println("\n\nMensaje corregido: ");
            for (int i = mensaje_revertido.length - 1; i >= 0; i--) {
                System.out.print(mensaje_revertido[i] + " ");
            }

            int tamaño_original = tamaño - bits_paridad;
            int tamaño_actual = 0;
            List<String> mensaje_original = new ArrayList<>();

            for (int i = 0; i < mensaje_revertido.length; i++) {
                boolean es_paridad = false;
                for (int j = 0; j < paridades.size(); j++) {
                    if (i + 1 == paridades.get(j)) {
                        es_paridad = true;
                    }
                }

                if (!es_paridad && tamaño_actual < tamaño_original) {
                    mensaje_original.add(mensaje_revertido[i]);
                    tamaño_actual++;
                }
            }

            System.out.println("\n\nMensaje recibido y codificado: ");
            for (int i = mensaje_original.size() - 1; i >= 0; i--) {
                System.out.print(mensaje_original.get(i) + "");
            }
        }

    }


    public static void main(String[] args){

        int opcion = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n");
        
        while(opcion != 3){
            
            System.out.println(" [ 1 ] Hamming");
            System.out.println(" [ 2 ] CRC");
            System.out.println(" [ 3 ] Salir");
            System.out.print("\nOpcion: ");
            opcion = scanner.nextInt();
            // scanner.close();

            switch(opcion){
                case 1:
                    System.out.println("\n================== Hamming ==================\n");
                    hamming();
                    System.out.println("\n\n==============================================\n");
                    break;
                case 2:
                    System.out.println("\n================== CRC_32 ==================\n");
                    crc_32();
                    System.out.println("\n\n==============================================\n");
                    break;
            }

        }

        scanner.close();
    }
    
    
}
