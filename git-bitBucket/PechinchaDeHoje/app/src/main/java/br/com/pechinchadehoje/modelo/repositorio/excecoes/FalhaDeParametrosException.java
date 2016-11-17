package br.com.pechinchadehoje.modelo.repositorio.excecoes;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class FalhaDeParametrosException extends  Exception {

    public FalhaDeParametrosException(String e){
        super(e);
    }

    public FalhaDeParametrosException(){
        super("Falha nos parametros passados para o servidor.");
    }


}
