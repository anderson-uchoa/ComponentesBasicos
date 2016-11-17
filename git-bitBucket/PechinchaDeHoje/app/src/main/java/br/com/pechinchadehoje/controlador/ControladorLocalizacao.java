package br.com.pechinchadehoje.controlador;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.com.pechinchadehoje.controlador.constantes.ParametrosRequisicao;
import br.com.pechinchadehoje.controlador.excecoes.FalhaErroNoRepositorioException;
import br.com.pechinchadehoje.modelo.repositorio.HttpClient;
import br.com.pechinchadehoje.modelo.repositorio.RepositorioLocalizacao;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import br.com.pechinchadehoje.util.DispositivoUtil;
import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class ControladorLocalizacao {

    private static final int MAX_RESULTS = 1;
    private RepositorioLocalizacao mRepositorioLocalizacao;
    private Context context;

    public ControladorLocalizacao(Context context){
        mRepositorioLocalizacao = new RepositorioLocalizacao(new HttpClient());
        this.context = context;
    }

    public Address getLocalizacaoEndereco(Location location, String endereco) {

        Geocoder geocoder = new Geocoder(this.context, Locale.getDefault());
        List<Address> enderecos = null;

        try {
            if (location != null) {
                enderecos = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), MAX_RESULTS);
            } else if (endereco != null && !endereco.trim().equals("")) {
                enderecos = geocoder.getFromLocationName(endereco, MAX_RESULTS);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (enderecos == null || enderecos.size() == 0) {
            return null;
        }
        return enderecos.get(0);
    }

    public boolean enviarLocalizacao(Double longitude, Double latitude) throws FalhaDeParametrosException, FalhaErroNoRepositorioException, FalhaSemPermissaoException {
        String id = null;

        try{
            id = DispositivoUtil.getIdDispositivo(context);
        }catch (Exception e){
            return false;
        }

        if(longitude==null || id == null || latitude==null)
            throw new FalhaDeParametrosException();

        try {

            RequestBody params = new FormBody.Builder()
                    .add(ParametrosRequisicao.LATITUDE_TAG, latitude+"")
                    .add(ParametrosRequisicao.LONGITUDE_TAG, longitude + "")
                    .add(ParametrosRequisicao.DISTANCIA_TAG, ParametrosRequisicao.DISTANCIA+"")
                    .build();

            return mRepositorioLocalizacao.enviarLocalizacao(params);
        } catch (FalhaDeParametrosException|FalhaInternaException e) {
            throw new FalhaErroNoRepositorioException(e);
        }
    }
}


