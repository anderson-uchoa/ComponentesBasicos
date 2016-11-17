package br.com.pechinchadehoje.modelo.repositorio;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import br.com.pechinchadehoje.modelo.Oferta;
import br.com.pechinchadehoje.modelo.Voucher;
import br.com.pechinchadehoje.modelo.repositorio.constantes.ParametrosRecebidosAPI;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class RepositorioVoucher {

    private static final String URL_ADICIONAR = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"";
    private static final String URL_BUSCA_VOUCHERS_USUARIO = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"/usuario/private/voucher/query";
    private static final String URL_BUSCA_VOUCHER_POR_ID = ParametrosRecebidosAPI.CAMINHO_SERVIDOR+"/usuario/private/voucher/queryOne";
    private HttpClient client;

    public RepositorioVoucher(HttpClient client){
        this.client = client;

    }

    public boolean adicionarOfertaPorId(RequestBody params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {

    String retorno = client.JSONReceived(URL_ADICIONAR, params);

        try {

            int numeroRetornado = Integer.parseInt(retorno);

            if(numeroRetornado == ParametrosRecebidosAPI.FALHA_NOS_PARAMETROS){
                throw new FalhaDeParametrosException();
            } else if(numeroRetornado == ParametrosRecebidosAPI.CODE_SUCESSO) {
                return true;
            }
            else {
                return false;
            }
        }catch (NumberFormatException e) {
            return false;
        }

    }

    public List<Voucher> buscarVouchersPorUsuario(RequestBody params) throws FalhaInternaException, FalhaDeParametrosException, FalhaSemPermissaoException {


        String retorno = client.JSONReceived(URL_BUSCA_VOUCHERS_USUARIO, params);

        if(retorno == null) {
            return null;
        }

        Log.i(RepositorioVoucher.class.toString(), retorno);

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Voucher>>() {}.getType();
            List<Voucher> lista = gson.fromJson(retorno, type);

            return lista;
        } catch (Exception e) {
            throw new FalhaInternaException(e);
        }

    }



}


