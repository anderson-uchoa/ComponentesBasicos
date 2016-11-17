package br.com.pechinchadehoje.modelo.repositorio.constantes;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class ParametrosRecebidosAPI {

    public static final int

            CODE_SUCESSO = 200,
            FALHA_NOS_PARAMETROS = 400,
            NAO_HA_RESULTADOS = 204,
            SEM_PERMISSAO = 401,
            FALHA_INTERNA = 500;


    public static final String CAMINHO_SERVIDOR = "http://ec2-52-38-136-232.us-west-2.compute.amazonaws.com:3000/";
    public static final String CAMINHO_SERVIDOR_LUAN = "http://192.168.1.8:3005";

}