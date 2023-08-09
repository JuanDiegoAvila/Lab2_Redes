package Capas_Receptor;

public class presentacion {
    public void decodificar_mensaje(String mensaje, boolean error) {
        System.out.println("Decodificando mensaje ...\n");
        if(!error) {
            System.out.println("El mensaje recibido es: " + mensaje);
            int ascii = Integer.parseInt(mensaje, 2);
            char character = (char) ascii;

            aplicacion ap = new aplicacion();
            ap.mostrarMensaje(String.valueOf(character), error);
        }

    }
    
}