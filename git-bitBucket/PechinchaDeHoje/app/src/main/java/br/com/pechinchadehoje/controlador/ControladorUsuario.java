package br.com.pechinchadehoje.controlador;

import android.app.Activity;
import android.util.Log;


import com.google.gson.JsonObject;


import br.com.pechinchadehoje.SettingsApplication;
import br.com.pechinchadehoje.controlador.constantes.ParametrosRequisicao;
import br.com.pechinchadehoje.controlador.excecoes.FalhaErroNoRepositorioException;
import br.com.pechinchadehoje.modelo.Usuario;
import br.com.pechinchadehoje.modelo.repositorio.HttpClient;
import br.com.pechinchadehoje.modelo.repositorio.RepositorioUsuario;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class ControladorUsuario {


    private RepositorioUsuario mRepositorioUsuario;
    private Activity activity;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ControladorUsuario(Activity activity){

        mRepositorioUsuario = new RepositorioUsuario(new HttpClient());
        this.activity = activity;
    }


    public boolean cadastrarUsuario (String nome, String email, String senha) throws FalhaDeParametrosException, FalhaErroNoRepositorioException, FalhaSemPermissaoException {


        if(nome==null || email==null || senha==null ) {
            throw new FalhaDeParametrosException();
        }
        Log.i("teste", nome+" "+email+" "+senha);

        SettingsApplication mSettingsApplication = ((SettingsApplication) activity.getApplication());

        JsonObject innerObject = new JsonObject();
        innerObject.addProperty(Usuario.NOME, nome);
        innerObject.addProperty(Usuario.EMAIL, email);
        innerObject.addProperty(Usuario.SENHA, senha);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("usuario", innerObject);

        try {

            RequestBody params = RequestBody.create(JSON, jsonObject.toString());


          Usuario usuario = mRepositorioUsuario.cadastrarUsuario(params);

            if(usuario == null) {
                return false;
            }

            if(usuario.email != null && usuario.nome != null && usuario.senha != null && usuario.avatar != null) {

                ControleUsuarioRealm mControleUsuarioRealm = new ControleUsuarioRealm(activity.getBaseContext());
                mControleUsuarioRealm.adicionar(usuario);
                mSettingsApplication.setUsuario(usuario);
                mSettingsApplication.accountName = usuario.email;
                return true;
            }

        } catch (FalhaDeParametrosException|FalhaInternaException e) {
            throw new FalhaErroNoRepositorioException(e);
        }
        return false;
    }


    public boolean atualizarUsuario (String nome, String email, String senha, String id) throws FalhaDeParametrosException, FalhaErroNoRepositorioException {

        if(nome==null || email==null || id == null) {
            throw new FalhaDeParametrosException();
        }
        Log.i("teste", nome+" "+email+" "+senha +" " + id );

        SettingsApplication mSettingsApplication = ((SettingsApplication) activity.getApplication());

        try {
            RequestBody params = new FormBody.Builder()
                    .add(Usuario.EMAIL, email)
                    .add(Usuario.ID, id)
                    .build();

            Request request = new Request.Builder()
                    .addHeader(ParametrosRequisicao.TOKEN_TAG, mSettingsApplication.authTokenType)
                    .post(params)
                    .build();


            Usuario usuario = mRepositorioUsuario.atualizarUsuario(request);

            if(usuario == null) {
                return false;
            }

            if(usuario.email != null && usuario.nome != null && usuario.senha != null && usuario.avatar != null) {

                ControleUsuarioRealm mControleUsuarioRealm = new ControleUsuarioRealm(activity.getBaseContext());
                mControleUsuarioRealm.alterar(usuario);
                mSettingsApplication.setUsuario(usuario);
                mSettingsApplication.accountName = usuario.email;
                return true;
            }

        } catch (FalhaDeParametrosException|FalhaInternaException e) {
            throw new FalhaErroNoRepositorioException(e);
        } catch (FalhaSemPermissaoException e) {
            e.printStackTrace();
        }
        return false;
    }

    }




