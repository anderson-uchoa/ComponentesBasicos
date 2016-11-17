package br.com.pechinchadehoje.controlador.excecoes;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class FalhaErroNoRepositorioException  extends  Exception{

        public FalhaErroNoRepositorioException(Exception exception){
            super(exception);
        }
    }



