package Capas_Receptor;

import java.io.FileWriter;
import java.io.IOException;

public class aplicacion {
    public static String mensaje = "";
    public static boolean error_en_ms = false;

    public static void guardarEnArchivo(String contenido, String decoder) {
        try {
            String filePath = "./reconocido.txt"; 
            FileWriter writer = new FileWriter(filePath, true); 
            writer.write(contenido + " "+decoder+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void mostrarMensaje(String mensaje, boolean error, boolean final_oracion, String decoder) {
        System.out.println("---------------------Mostrando mensaje---------------------\n");
        if(!error && !error_en_ms) {
            
            if(!final_oracion) {
                aplicacion.mensaje += mensaje;
            }
            else {
                aplicacion.mensaje += mensaje;
                aplicacion.guardarEnArchivo(aplicacion.mensaje, decoder);
                aplicacion.mensaje = "";
            }
        }
        else {
            aplicacion.error_en_ms = true;
            if (final_oracion) {
                System.out.println("Hubieron errores en la recepcion de mensajes");
                aplicacion.guardarEnArchivo("ERROR", decoder);
                aplicacion.mensaje = "";
                aplicacion.error_en_ms = false;
            }
        }
    }
}