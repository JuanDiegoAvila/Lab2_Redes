package Capas_Receptor;

import java.io.*;
import java.net.*;

public class transmision {
    public void recibir_informacion(){ 
        System.out.println("Recibiendo informacion ...\n");
        try {
            try (ServerSocket servidor = new ServerSocket(5000)) {
                Socket cliente;
                BufferedReader entrada;
                String mensaje;
                while(true){
                    cliente = servidor.accept();
                    entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    
                    mensaje = entrada.readLine();
                    System.out.println("\nEl mensaje recibido es: " + mensaje);

                    String[] recibidos = mensaje.split("\\|");


                    String mensaje_recibido = recibidos[0];
                    boolean final_oracion = Boolean.parseBoolean(recibidos[2]);
                    String algoritmo = recibidos[1];

                    if (mensaje_recibido == "SALIR") {
                        cliente.close();
                    }
                    else{
                        System.out.println("\nEl mensaje recibido es: " + mensaje_recibido);
                        enlace e = new enlace();
                        e.verificar_integridad(mensaje_recibido, algoritmo, final_oracion);
                    }
                    entrada.close();


                    
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}