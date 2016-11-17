package br.com.pechinchadehoje.modelo.repositorio.excecoes;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class FalhaSemPermissaoException extends  Exception {

    public FalhaSemPermissaoException(Exception e){
        super(e);
    }

    public FalhaSemPermissaoException(){
        super("Acesso não autorizado para o servidor.");
    }
}


