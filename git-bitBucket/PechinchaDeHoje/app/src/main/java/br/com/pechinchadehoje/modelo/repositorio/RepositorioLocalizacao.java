package br.com.pechinchadehoje.modelo.repositorio;

import br.com.pechinchadehoje.modelo.repositorio.constantes.ParametrosRecebidosAPI;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class RepositorioLocalizacao {

    private static final String URL_ENVIAR_LOCALIZACAO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR;

    private HttpClient client;

    public RepositorioLocalizacao(HttpClient client) {
        this.client = client;
    }


    public boolean enviarLocalizacao(RequestBody params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {

        String resposta = client.JSONReceived(URL_ENVIAR_LOCALIZACAO, params);

        try {
            if(Integer.parseInt(resposta) != ParametrosRecebidosAPI.CODE_SUCESSO) {
                return false;
            }else {
                return true;
            }
        }catch (NumberFormatException e){
            return false;
        }
    }


}
