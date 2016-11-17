package br.com.pechinchadehoje.modelo.repositorio;

import android.util.Log;



import java.io.IOException;
import java.util.concurrent.TimeUnit;

import br.com.pechinchadehoje.controlador.constantes.ParametrosRequisicao;
import br.com.pechinchadehoje.modelo.repositorio.constantes.ParametrosRecebidosAPI;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpClient {
    private OkHttpClient client;

    public HttpClient(){
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        certificacao();

    }

    private void certificacao(){
        //TODO CERTIFICAÇÂO PARA HTTPS https://github.com/square/okhttp/wiki/HTTPS
        /*client.setCertificatePinner(
                new CertificatePinner.Builder()
                .adicionar("host", "sha1/blabla")
                .build()
        );*/
    }

    public String JSONReceived(String url, RequestBody params, String ... token) throws FalhaDeParametrosException, FalhaInternaException, FalhaSemPermissaoException {

        String userAgent = System.getProperty("http.agent");

        if(url==null)
            throw new FalhaDeParametrosException("URL passada para o metodo e nula.");

        Request.Builder builder = new Request.Builder()
                .url(url);

        if(token != null && token.length > 0){
            builder.addHeader(ParametrosRequisicao.TOKEN_TAG, token[0]);
        }

        if (params != null)
            builder = builder.post(params);

        Request request = builder.build();

        return resultado(request);
    }


    public String JSONReceived(String url) throws FalhaInternaException, FalhaSemPermissaoException, FalhaDeParametrosException {

        String userAgent = System.getProperty("http.agent");

        if(url==null)
            throw new FalhaDeParametrosException("URL passada para o metodo e nula.");

        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", userAgent)
                .addHeader("Content-Type", "application/json");

        Request request = builder.build();

        return resultado(request);
    }




    private String resultado(Request request) throws FalhaInternaException, FalhaSemPermissaoException, FalhaDeParametrosException {

        Response resposta;
        int codeResp = 0;
        try {

            resposta = client.newCall(request).execute();

            if(resposta != null) {
                codeResp = resposta.code();
                Log.i("teste", "Code Received: " + codeResp);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new FalhaInternaException();
        }

        if (resposta.isSuccessful()) {

            if (codeResp == ParametrosRecebidosAPI.FALHA_NOS_PARAMETROS)
                throw new FalhaDeParametrosException();
            else if (codeResp == ParametrosRecebidosAPI.FALHA_INTERNA)
                throw new FalhaInternaException();
            else if (codeResp == ParametrosRecebidosAPI.SEM_PERMISSAO)
                throw new FalhaSemPermissaoException();
            else if (codeResp == ParametrosRecebidosAPI.NAO_HA_RESULTADOS)
                return null;
            else if(codeResp == ParametrosRecebidosAPI.CODE_SUCESSO) {
                try {
                    return resposta.body().string().trim();
                } catch (IOException e) {
                    throw new FalhaInternaException();
                }
            }
        }
        throw new FalhaInternaException();
    }

    public String JSONReceivedHttpUrl(HttpUrl url, String ... token) throws FalhaDeParametrosException, FalhaInternaException, FalhaSemPermissaoException {

        String userAgent = System.getProperty("http.agent");

        if(url==null) {
            throw new FalhaDeParametrosException("URL passada para o metodo e nula.");
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", userAgent)
                .addHeader("Content-Type", "application/json");


        if(token != null && token.length > 0){
            Log.i("token", token[0]);
            builder.addHeader(ParametrosRequisicao.TOKEN_TAG, token[0]);
        }

        Request request = builder.build();

        return resultado(request);
    }

}
