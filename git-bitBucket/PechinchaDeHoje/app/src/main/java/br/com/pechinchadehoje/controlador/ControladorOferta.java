package br.com.pechinchadehoje.controlador;

import android.app.Activity;

import com.google.gson.JsonObject;

import java.util.List;

import br.com.pechinchadehoje.controlador.constantes.ParametrosRequisicao;
import br.com.pechinchadehoje.controlador.excecoes.FalhaErroNoRepositorioException;
import br.com.pechinchadehoje.modelo.Oferta;
import br.com.pechinchadehoje.modelo.Usuario;
import br.com.pechinchadehoje.modelo.repositorio.HttpClient;
import br.com.pechinchadehoje.modelo.repositorio.RepositorioOferta;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import br.com.pechinchadehoje.util.ParamFiltrosUtil;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class ControladorOferta {

    public static final String MAX_DISTANCIA_PADRAO = "40";

    private RepositorioOferta mRepositorioOferta;

    public ControladorOferta(Activity activity){
        mRepositorioOferta = new RepositorioOferta(new HttpClient());
    }

    public Oferta buscarPorId(String idOferta) throws FalhaInternaException, FalhaDeParametrosException, FalhaErroNoRepositorioException, FalhaSemPermissaoException {

        if(idOferta == null) {

            throw new FalhaDeParametrosException();

        }

        RequestBody params = new FormBody.Builder()
                .add(Oferta._ID, idOferta)
                .build();

        try {
            return mRepositorioOferta.buscarPorId(params);
        } catch (FalhaInternaException e) {
            throw new FalhaErroNoRepositorioException(e);
        }
    }

    // TODO: 13/11/16 revisar paramentos
    public List<Oferta> buscarOfertasPorQuery(String query, Double latitude, Double longitude, Integer limite, Integer skip) throws FalhaDeParametrosException {

        if(longitude == null || latitude == null || limite == null || skip == null){
            throw new FalhaDeParametrosException();
        }







        HttpUrl.Builder url = new HttpUrl.Builder()
                .addQueryParameter(ParametrosRequisicao.TIPO_BUSCA, Integer.toString(ParamFiltrosUtil.NUM_OFERTA))
                .addQueryParameter(ParametrosRequisicao.QUERY, query)
                .addQueryParameter(ParametrosRequisicao.LATITUDE, latitude.toString())
                .addQueryParameter(ParametrosRequisicao.LONGITUDE, longitude.toString())
                .addQueryParameter(ParametrosRequisicao.MAX_DISTANCIA, MAX_DISTANCIA_PADRAO)
                .addQueryParameter(ParametrosRequisicao.LIMITE, limite.toString())
                .addQueryParameter(ParametrosRequisicao.SKIP, skip.toString());

        try {
            return mRepositorioOferta.buscarLojasPorGeolocalizacao(url);
        } catch (FalhaInternaException|FalhaDeParametrosException e) {
            e.printStackTrace();
            return null;
        } catch (FalhaSemPermissaoException e) {
            e.printStackTrace();
            return null;
        }

    }

    // TODO: 13/11/16 revisar paramentos
    public List<Oferta> buscaoOfertasPorGeolocalizacao(Double latitude, Double longitude, Integer limite, Integer pulo, Integer distancia) throws FalhaDeParametrosException {

        if (longitude == null || latitude == null || distancia == null) {
            throw new FalhaDeParametrosException();
        }

        HttpUrl.Builder url = new HttpUrl.Builder()
                .addQueryParameter(ParametrosRequisicao.QUERY, "")
                .addQueryParameter(ParametrosRequisicao.LATITUDE, latitude.toString())
                .addQueryParameter(ParametrosRequisicao.LONGITUDE, longitude.toString())
                .addQueryParameter(ParametrosRequisicao.DISTANCIA_TAG, distancia.toString())
                .addQueryParameter(ParametrosRequisicao.SKIP, pulo.toString());

        try {
            return mRepositorioOferta.buscarLojasPorGeolocalizacao(url);
        } catch (FalhaInternaException|FalhaDeParametrosException e) {
            e.printStackTrace();
            return null;
        } catch (FalhaSemPermissaoException e) {
            e.printStackTrace();
            return null;
        }

    }


}
