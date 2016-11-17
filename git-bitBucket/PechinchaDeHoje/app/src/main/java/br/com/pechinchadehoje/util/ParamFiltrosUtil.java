package br.com.pechinchadehoje.util;

import java.util.HashMap;



/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class ParamFiltrosUtil {

    public static String OFERTA = "Oferta";
    public static int NUM_OFERTA = 1;

    private static HashMap<String, Integer> tipos;

    public static HashMap<String, Integer> getTipos() {

        if (tipos == null) {
            tipos = new HashMap<>();
            tipos.put(OFERTA, NUM_OFERTA);
        }

        return tipos;
    }

    public static Integer valueTipo(String tipo) {
        Integer value = getTipos().get(tipo);
        return (value != null) ? value : 1;

    }


}
