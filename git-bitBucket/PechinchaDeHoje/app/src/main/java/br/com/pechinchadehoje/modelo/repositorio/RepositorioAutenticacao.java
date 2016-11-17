package br.com.pechinchadehoje.modelo.repositorio;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.pechinchadehoje.modelo.Usuario;
import br.com.pechinchadehoje.modelo.repositorio.constantes.ParametrosRecebidosAPI;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaNaoHaResultadosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class RepositorioAutenticacao {

    private String TOKEN = "token";
    private static final String URL_PEGAR_USUARIO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"usuario/private";
    public static final String URL_LOGIN = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"usuario/public/auth";
    public static final String URL_LOGIN_LUAN = ParametrosRecebidosAPI.CAMINHO_SERVIDOR_LUAN;

    public static final String URL_ALTERAR_SENHA_USUARIO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"usuario/private/auth";

    private HttpClient client;

    public RepositorioAutenticacao(HttpClient client) {
        this.client = client;
    }


    public Usuario logar(RequestBody params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException, FalhaNaoHaResultadosException, JSONException {

        String resposta = client.JSONReceived(URL_LOGIN, params);
        Log.i("token", resposta);

            try {
                JSONObject responseJson = new JSONObject(resposta);
                String token = responseJson.getString(TOKEN);

                return getUsuario(token);

            } catch (JSONException ee) {
                throw new FalhaInternaException(ee);
            }
    }

    public Usuario getUsuario(String token) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException, FalhaNaoHaResultadosException {


        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder()
                .host(URL_PEGAR_USUARIO);

        HttpUrl url = httpUrlBuilder.build();

        String resp = client.JSONReceivedHttpUrl(url, null, token);
        Log.i("resposta", resp);


        if (resp == null) {
            return null;
        }

        int numero = Integer.parseInt(resp);

        if (numero == ParametrosRecebidosAPI.FALHA_NOS_PARAMETROS) {
            throw new FalhaDeParametrosException();
        } else if (numero == ParametrosRecebidosAPI.FALHA_INTERNA) {
            throw new FalhaInternaException();
        } else if (numero == ParametrosRecebidosAPI.SEM_PERMISSAO) {
            throw new FalhaSemPermissaoException();
        } else if (numero == ParametrosRecebidosAPI.NAO_HA_RESULTADOS) {
            throw new FalhaNaoHaResultadosException();
        } else if (numero == ParametrosRecebidosAPI.CODE_SUCESSO) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(resp, Usuario.class);
            return usuario;

        }else{
            return  null;
        }

    }


    public Usuario alterarSenhaUsuario(RequestBody params, String token) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException, FalhaNaoHaResultadosException  {

        HttpUrl.Builder httpUrlBuilder = new HttpUrl.Builder()
                .host(URL_PEGAR_USUARIO);

        HttpUrl url = httpUrlBuilder.build();

        String resp = client.JSONReceivedHttpUrl(url, null, token);


        String retorno = client.JSONReceived(URL_ALTERAR_SENHA_USUARIO, params);


        if (retorno == null) {
            return null;
        }

        int numero = Integer.parseInt(retorno);

        if (numero == ParametrosRecebidosAPI.FALHA_NOS_PARAMETROS) {
            throw new FalhaDeParametrosException();
        } else if (numero == ParametrosRecebidosAPI.FALHA_INTERNA) {
            throw new FalhaInternaException();
        } else if (numero == ParametrosRecebidosAPI.SEM_PERMISSAO) {
            throw new FalhaSemPermissaoException();
        } else if (numero == ParametrosRecebidosAPI.NAO_HA_RESULTADOS) {
            throw new FalhaNaoHaResultadosException();
        } else if (numero == ParametrosRecebidosAPI.CODE_SUCESSO) {
            Gson gson = new Gson();
            Usuario usuario = gson.fromJson(retorno, Usuario.class);
            return usuario;

        }else{
            return  null;
        }


    }
}

