package Capas_Receptor;

public class aplicacion {
    public void mostrarMensaje(String mensaje, boolean error) {
        System.out.println("---------------------Mostrando mensaje---------------------\n");
        if(!error) {
            System.out.println("El mensaje recibido es: " + mensaje);
        }
        else {
            System.out.println("Hubieron errores en la recepcion de mensajes");
        }
    }
}