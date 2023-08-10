package Capas_Receptor;

import Algoritmos.receptorAL;

public class enlace {

    public void verificar_integridad(String mensaje, String decoder, boolean final_oracion){
        System.out.println("Verificando integridad ...\n");
        boolean error = false;
        String mensaje_decoded = "";
        Object[] obj = new Object[2];

        receptorAL rec = new receptorAL();

        switch(decoder) {
            case "CRC32":
                System.out.println("LLamada a crc32 con "+mensaje);
                obj = rec.crc_32(mensaje);
                
                error = (boolean) obj[0];
                mensaje_decoded = (String) obj[1];
                break;

            case "Hamming":
                System.out.println("LLamada a hamming");
                obj = rec.hamming(mensaje);
                for(int i = 0; i < obj.length; i++){
                    System.out.println(obj[i]);
                }
                error = (boolean) obj[0];
                mensaje_decoded = (String) obj[1];
                break;

        }


        presentacion p = new presentacion();
        if(error){
            if (decoder.equals("Hamming")){
                corregir_mensaje(mensaje, final_oracion, decoder);
            }
            else {
                p.decodificar_mensaje(mensaje_decoded, error, final_oracion, decoder);
            }
        }
        else {
            p.decodificar_mensaje(mensaje_decoded, error, final_oracion, decoder);
        }
    }

    public void corregir_mensaje(String mensaje, boolean final_oracion, String decoder){
        presentacion p = new presentacion();
        receptorAL rec = new receptorAL();

        String mensaje_decoded = rec.correccion_hamming(mensaje);
        if(mensaje_decoded.equals("ERROR")){
            p.decodificar_mensaje(mensaje, true, final_oracion, decoder);
        }
        else {
            p.decodificar_mensaje(mensaje_decoded, false, final_oracion, decoder);
        }
    }

}