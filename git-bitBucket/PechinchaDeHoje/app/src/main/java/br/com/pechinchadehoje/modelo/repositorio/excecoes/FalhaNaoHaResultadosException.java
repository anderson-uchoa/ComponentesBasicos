package br.com.pechinchadehoje.modelo.repositorio.excecoes;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class FalhaNaoHaResultadosException extends  Exception {

    public FalhaNaoHaResultadosException(Exception e){
        super(e);
    }

    public FalhaNaoHaResultadosException(){
        super("Falha não existe o objeto no banco.");
    }
}

