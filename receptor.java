import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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

    public static void crc_32() {
        System.out.println("crc_32");
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
        String[] mensaje = lines.get(0).split(" ");
        
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
            for (int i = mensaje_original.size() - 1; i >= 0; i--) {
                System.out.print(mensaje_original.get(i) + " ");
            }


        } else {

            String bits = joinArrayList(resultados, "");
            int decimal = Integer.parseInt(bits, 2);
            
            // El error esta en la posicion decimal del mensaje original

            System.out.println("Error en el bit " + decimal + " del mensaje original");

            System.out.println("\nMensaje original: ");
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
                System.out.print(mensaje_original.get(i) + " ");
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
                    System.out.println("CRC");
                    break;
            }

        }

        scanner.close();
    }
    
    
}
