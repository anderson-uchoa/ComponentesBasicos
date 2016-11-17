package br.com.pechinchadehoje.modelo.repositorio;

import com.google.gson.Gson;

import br.com.pechinchadehoje.controlador.excecoes.FalhaErroNoRepositorioException;
import br.com.pechinchadehoje.modelo.Usuario;
import br.com.pechinchadehoje.modelo.repositorio.constantes.ParametrosRecebidosAPI;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class RepositorioUsuario {


    private static final String URL_CADASTRAR_USUARIO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"usuario/public";
    private static final String URL_ATUALIZAR_USUARIO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"usuario/private";


    private HttpClient client;

    public RepositorioUsuario(HttpClient client) {
        this.client = client;
    }

    public Usuario cadastrarUsuario(RequestBody params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {

        String retorno = client.JSONReceived(URL_CADASTRAR_USUARIO, params);

        try {

            int numeroRetornado = Integer.parseInt(retorno);

            if (numeroRetornado == ParametrosRecebidosAPI.FALHA_INTERNA) {
                throw new FalhaInternaException();
            } else if (numeroRetornado == ParametrosRecebidosAPI.CODE_SUCESSO) {

                Gson gson = new Gson();
                Usuario usuario = gson.fromJson(retorno, Usuario.class);

                return usuario;
            } else {
                return null;
            }
        } catch (FalhaInternaException e) {
            return null;
        }

    }

    public Usuario atualizarUsuario(Request params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {


        String retorno = client.JSONReceived(URL_ATUALIZAR_USUARIO);

        try {

            int numeroRetornado = Integer.parseInt(retorno);

            if (numeroRetornado == ParametrosRecebidosAPI.FALHA_INTERNA) {
                throw new FalhaInternaException();
            } else if (numeroRetornado == ParametrosRecebidosAPI.CODE_SUCESSO) {
                Gson gson = new Gson();
                Usuario usuario = gson.fromJson(retorno, Usuario.class);
                return usuario;
            } else {
                return null;
            }
        } catch (FalhaInternaException e) {
            return null;
        }


    }
}