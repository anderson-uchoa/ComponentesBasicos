package br.com.pechinchadehoje.controlador;

import android.app.Activity;

import java.util.List;

import br.com.pechinchadehoje.SettingsApplication;
import br.com.pechinchadehoje.controlador.constantes.ParametrosRequisicao;
import br.com.pechinchadehoje.controlador.excecoes.FalhaErroNoRepositorioException;
import br.com.pechinchadehoje.modelo.Oferta;
import br.com.pechinchadehoje.modelo.Voucher;
import br.com.pechinchadehoje.modelo.repositorio.HttpClient;
import br.com.pechinchadehoje.modelo.repositorio.RepositorioVoucher;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.FormBody;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class ControladorVoucher {

    private RepositorioVoucher mRepositorioVoucher;
    private Activity activity;

    public ControladorVoucher(Activity activity) {
        mRepositorioVoucher = new RepositorioVoucher(new HttpClient());
    }


    public boolean adicionarVoucher(String idOferta) throws FalhaDeParametrosException, FalhaErroNoRepositorioException, FalhaSemPermissaoException {

        if (idOferta == null) {
            throw new FalhaDeParametrosException();
        }

        RequestBody params = new FormBody.Builder()
                .add(Oferta._ID, idOferta)
                .build();

        try {
            return mRepositorioVoucher.adicionarOfertaPorId(params);
        } catch (FalhaInternaException e) {
            throw new FalhaErroNoRepositorioException(e);
        }
    }

    public List<Voucher> buscarVouchersPorUsuario( Integer limit, Integer skip) throws FalhaDeParametrosException {


        SettingsApplication mSettingsApplication = ((SettingsApplication) activity.getApplication());

        if (limit == null || skip == null) {
            throw new FalhaDeParametrosException();
        }

        try {
        RequestBody params = new FormBody.Builder()
                .add(ParametrosRequisicao.LIMITE, limit.toString())
                .add(ParametrosRequisicao.SKIP, skip.toString())
                .build();

        Request request = new Request.Builder()
                .addHeader(ParametrosRequisicao.TOKEN_TAG, mSettingsApplication.authTokenType)
                .post(params)
                .build();

            return mRepositorioVoucher.buscarVouchersPorUsuario(params);

        } catch (FalhaInternaException|FalhaDeParametrosException e) {
            e.printStackTrace();
            return null;
        } catch (FalhaSemPermissaoException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<Voucher> buscarVouchersPorId(Integer limit, Integer skip) throws FalhaDeParametrosException {


        SettingsApplication mSettingsApplication = ((SettingsApplication) activity.getApplication());

        if (limit == null || skip == null) {
            throw new FalhaDeParametrosException();
        }

        try {
            RequestBody params = new FormBody.Builder()
                    .add(ParametrosRequisicao.LIMITE, limit.toString())
                    .add(ParametrosRequisicao.SKIP, skip.toString())
                    .build();

            Request request = new Request.Builder()
                    .addHeader(ParametrosRequisicao.TOKEN_TAG, mSettingsApplication.authTokenType)
                    .post(params)
                    .build();

            return mRepositorioVoucher.buscarVouchersPorUsuario(params);

        } catch (FalhaInternaException|FalhaDeParametrosException e) {
            e.printStackTrace();
            return null;
        } catch (FalhaSemPermissaoException e) {
            e.printStackTrace();
            return null;
        }

    }



}
