package Algoritmos;

import java.util.ArrayList;
import java.util.List;

public class receptorAL {

    public String joinArrayList(ArrayList<Integer> list, String separator) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i < list.size() - 1) {
                result.append(separator);
            }
        }

        return result.toString();
    }

    public boolean contains(int[] array, int value) {
        for (int num : array) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }

    public boolean decode_crc32( List<Integer> temp_mensaje, int[] gradosInvertidos, int tamano, int indice, int[] mensajeOriginal){
        while(temp_mensaje.size() >= tamano){
            for (int i = 0; i < gradosInvertidos.length; i++) {
                temp_mensaje.set(i, temp_mensaje.get(i) ^ gradosInvertidos[i]);
            }

            while(temp_mensaje.get(0) == 0){
                temp_mensaje.remove(0);
                if(temp_mensaje.size() == 0){
                    return true;
                }
            }


            if(temp_mensaje.size() < tamano && indice < mensajeOriginal.length){
                while(temp_mensaje.size() < tamano && indice < mensajeOriginal.length){
                    temp_mensaje.add(mensajeOriginal[indice]);
                    indice++;
                }
            }else{
                return false;
            }

            
        }
        if (temp_mensaje.size() != 0){
            return false;
        }
        return true;
    }

    public Object[] crc_32(String m) {

        boolean hay_error = false;
        String[] leido = m.split("");
        int[] mensaje = new int[leido.length];

        for (int i = 0; i < leido.length; i++) {
            mensaje[i] = Integer.parseInt(leido[i]);
        }


        int[] estandar_grados = {0, 1, 2, 4, 5, 7, 8, 10, 11, 12, 16, 22, 23, 26, 32};
        int pol = 33; 
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
        
        int tamano = gradosInvertidos.length;
        int indice = tamano;
        List<Integer> temp_mensaje = new ArrayList<>();
        for (int i = 0; i < tamano; i++) {
            temp_mensaje.add(mensaje[i]);
        }

        String mensaje_original_String = "";
        
        boolean not_error = decode_crc32(temp_mensaje, gradosInvertidos, tamano, indice, mensaje);
        if(not_error){
            System.out.println("No hay error en el mensaje");
            int tamano_original = mensaje.length - (gradosInvertidos.length-1);
            for (int i = 0; i < tamano_original; i++) {
                mensaje_original_String += mensaje[i];
            }
        }else{
            hay_error = true;
            System.out.println("Error en el mensaje");
        }

        Object[] Object = new Object[2];
        Object[0] = hay_error;
        Object[1] = mensaje_original_String;

        return Object;
    } 

    public String correccion_hamming(String m){
        String[] mensaje = m.split("");
        
        int tamano = mensaje.length;

        String[] mensaje_r = new String[tamano];
        for (int i = tamano - 1; i >= 0; i--) {
            mensaje_r[i] = mensaje[tamano - i - 1];
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
            for (int i = 0; i < tamano + 1; i++) {
                if ((i + 1 & paridad) == paridad) {
                    fila.add(i + 1);
                }
            }
            tabla.add(fila);
        }

        String[] mensaje_revertido = new String[tamano];
        for (int i = tamano - 1; i >= 0; i--) {
            mensaje_revertido[i] = mensaje[tamano - i - 1];
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

        String bits = joinArrayList(resultados, "");
        int decimal = Integer.parseInt(bits, 2);
        
        // El error esta en la posicion decimal del mensaje original

        // System.out.println("Error en el bit " + decimal + " del mensaje original");

        if (mensaje_revertido[decimal - 1].equals("1")) {
            mensaje_revertido[decimal - 1] = "0";
        } else {
            mensaje_revertido[decimal - 1] = "1";
        }

        System.out.println("\n\nMensaje corregido: ");
        for (int i = mensaje_revertido.length - 1; i >= 0; i--) {
            System.out.print(mensaje_revertido[i] + " ");
        }

        int tamano_original = tamano - bits_paridad;
        int tamano_actual = 0;
        List<String> mensaje_original = new ArrayList<>();

        for (int i = 0; i < mensaje_revertido.length; i++) {
            boolean es_paridad = false;
            for (int j = 0; j < paridades.size(); j++) {
                if (i + 1 == paridades.get(j)) {
                    es_paridad = true;
                }
            }

            if (!es_paridad && tamano_actual < tamano_original) {
                mensaje_original.add(mensaje_revertido[i]);
                tamano_actual++;
            }
        }

        // System.out.println("\n\nMensaje recibido y codificado: ");
        // for (int i = mensaje_original.size() - 1; i >= 0; i--) {
        //     System.out.print(mensaje_original.get(i) + "");
        // }

        String mensaje_corregido = "";
        for (int i = mensaje_original.size() - 1; i >= 0; i--) {
            mensaje_corregido += mensaje_original.get(i);
        }

        return mensaje_corregido;
    }
    
    public Object[] hamming(String m) {
        
        boolean hay_error = false;
        String[] mensaje = m.split("");
        String mensaje_original_string = "";
        
        int tamano = mensaje.length;

        String[] mensaje_r = new String[tamano];
        for (int i = tamano - 1; i >= 0; i--) {
            mensaje_r[i] = mensaje[tamano - i - 1];
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
            for (int i = 0; i < tamano + 1; i++) {
                if ((i + 1 & paridad) == paridad) {
                    fila.add(i + 1);
                }
            }
            tabla.add(fila);
        }

        String[] mensaje_revertido = new String[tamano];
        for (int i = tamano - 1; i >= 0; i--) {
            mensaje_revertido[i] = mensaje[tamano - i - 1];
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
            
            int tamano_original = tamano - bits_paridad;
            int tamano_actual = 0;
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

                if (!es_paridad && tamano_actual < tamano_original) {
                    mensaje_original.add(mensaje_revertido[i]);
                    tamano_actual++;
                }
            }

            // guardar el mensaje_original reversed
            for (int i = mensaje_original.size() - 1; i >= 0; i--) {
                mensaje_original_string += mensaje_original.get(i);
            }


        } else {

            hay_error = true;
            System.out.println("Error en el mensaje");
        }

        Object[] Object = new Object[2];

        Object[0] = hay_error;
        Object[1] = mensaje_original_string;

        return Object;

    }
}
