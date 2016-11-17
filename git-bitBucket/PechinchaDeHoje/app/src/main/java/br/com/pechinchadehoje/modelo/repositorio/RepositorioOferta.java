package br.com.pechinchadehoje.modelo.repositorio;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.pechinchadehoje.modelo.Oferta;
import br.com.pechinchadehoje.modelo.repositorio.constantes.ParametrosRecebidosAPI;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class RepositorioOferta {

    private static final String URL_BUSCAR_POR_ID = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"";
    private static final String URL_BUSCAR = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"";
    private static final String URL_BUSCAR_POR_GEO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"loja/public/oferta";
    private static final String URL_BUSCAR_GEOLOCALIZACAO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"loja/public/oferta";
    private static final String CAMPO_RESULTADOS_BUSCA = "resultados";


    private HttpClient client;

    public RepositorioOferta(HttpClient client){
        this.client = client;

    }


    public Oferta buscarPorId(RequestBody params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {

        String retorno = client.JSONReceived(URL_BUSCAR_POR_ID, params);

        if(retorno == null) {
            return null;
        }
        Log.i(RepositorioOferta.class.toString(), retorno);

        try {
            Gson gson = new Gson();

            Oferta oferta = gson.fromJson(retorno, Oferta.class);
            return oferta;

        } catch (Exception e) {
            throw new FalhaInternaException(e);
        }
    }

    public List<Oferta> buscar(RequestBody params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {

        String retorno = client.JSONReceived(URL_BUSCAR, params);

        if(retorno == null) {
            return null;
        }

        Log.i(RepositorioOferta.class.toString(), retorno);

        try {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Oferta>>() {}.getType();
        List<Oferta> lista = gson.fromJson(retorno, type);

            return lista;
        } catch (Exception e) {
            throw new FalhaInternaException(e);
        }

    }


    public List<Oferta> buscarLojasPorQuery(HttpUrl.Builder httpUrlBuilder) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {

        HttpUrl url = httpUrlBuilder
                .host(URL_BUSCAR_GEOLOCALIZACAO)
                .build();

        String resp = client.JSONReceivedHttpUrl(url);

        if (resp == null) {
            return null;
        }

        Log.i(RepositorioOferta.class.toString(), resp);

        JSONObject object;
        JSONArray array;
        try {

            object = new JSONObject(resp);

            array = object.getJSONArray(CAMPO_RESULTADOS_BUSCA);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new FalhaInternaException(e);
        }

//        List<Loja> lista = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject objLoja = array.getJSONObject(i);
  //              lista.add(ParseObjectJSONToObject.getLojaOfJSON(objLoja));
            } catch (JSONException e) {
                throw new FalhaInternaException(e);
            }
        }

        return null;
    }

    public List<Oferta> buscarLojasPorGeolocalizacao(HttpUrl.Builder httpUrlBuilder) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {

        HttpUrl url = httpUrlBuilder
                .host(URL_BUSCAR_GEOLOCALIZACAO)
                .build();

        String resp = client.JSONReceivedHttpUrl(url);

        if(resp == null) {
            return null;
        }

        Log.i(RepositorioOferta.class.toString(), resp);

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Oferta>>() {}.getType();
            List<Oferta> lista = gson.fromJson(resp, type);

            return lista;
        } catch (Exception e) {
            throw new FalhaInternaException(e);
        }

    }



}


