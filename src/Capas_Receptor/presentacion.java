package Capas_Receptor;



public class presentacion {
    public void decodificar_mensaje(String mensaje, boolean error, boolean final_oracion, String decoder) {
        System.out.println("Decodificando mensaje ...\n");
        System.out.println(mensaje);
        if(!error) {
            System.out.println("El mensaje recibido es: " + mensaje);
            int ascii = Integer.parseInt(mensaje, 2);
            char character = (char) ascii;

            aplicacion.mostrarMensaje(String.valueOf(character), error, final_oracion, decoder);
        }
        else {
            aplicacion.mostrarMensaje(mensaje, error, final_oracion, decoder);
        }

    }
    
}