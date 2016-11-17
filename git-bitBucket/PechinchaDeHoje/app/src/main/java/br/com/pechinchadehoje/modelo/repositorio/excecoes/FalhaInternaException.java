package br.com.pechinchadehoje.modelo.repositorio.excecoes;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class FalhaInternaException extends  Exception {

    public FalhaInternaException(Exception e){
        super(e);
    }

    public FalhaInternaException(){
        super("Falha interna no servidor.");
    }
}

