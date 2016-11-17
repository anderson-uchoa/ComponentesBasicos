package br.com.pechinchadehoje.controlador;

import android.app.Activity;

import com.google.gson.JsonObject;

import org.json.JSONException;

import br.com.pechinchadehoje.SettingsApplication;
import br.com.pechinchadehoje.controlador.constantes.ParametrosRequisicao;
import br.com.pechinchadehoje.controlador.excecoes.FalhaErroNoRepositorioException;

import br.com.pechinchadehoje.modelo.Usuario;
import br.com.pechinchadehoje.modelo.repositorio.HttpClient;
import br.com.pechinchadehoje.modelo.repositorio.RepositorioAutenticacao;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaInternaException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaNaoHaResultadosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class ControladorAutenticacao {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private RepositorioAutenticacao mRepositorioAutenticacao;
    private Activity activity;

    public ControladorAutenticacao(Activity activity) {
        mRepositorioAutenticacao = new RepositorioAutenticacao(new HttpClient());
        this.activity = activity;
    }

    public boolean logar(String email, String senha) throws FalhaDeParametrosException, FalhaErroNoRepositorioException, FalhaNaoHaResultadosException, FalhaSemPermissaoException, JSONException {

        if (email == null || senha == null) {
            throw new FalhaDeParametrosException();
        }

        SettingsApplication mSettingsApplication = ((SettingsApplication) activity.getApplication());


        JsonObject innerObject = new JsonObject();
        innerObject.addProperty(Usuario.EMAIL, email.trim());
        innerObject.addProperty(Usuario.SENHA, senha);

        try {
            System.out.println("ENTROU CARAI");
            RequestBody params = RequestBody.create(JSON, innerObject.toString());

            Usuario usuario = mRepositorioAutenticacao.logar(params);

            if (usuario == null) {
                return false;

            }
                if (usuario.email != null && usuario.nome != null && usuario.senha != null && usuario.avatar != null) {

                    ControleUsuarioRealm mControleUsuarioRealm = new ControleUsuarioRealm(activity);
                    mControleUsuarioRealm.adicionar(usuario);
                    mSettingsApplication.setUsuario(usuario);
                    mSettingsApplication.accountName = usuario.email;
                    mSettingsApplication.authTokenType = usuario.senha;

                    return true;
                }


            } catch (FalhaDeParametrosException | FalhaInternaException e) {
            throw new FalhaErroNoRepositorioException(e);
        }
        return false;
    }




    public boolean alterarSenha(String senha, String novaSenha, String token) throws FalhaErroNoRepositorioException, FalhaNaoHaResultadosException, FalhaSemPermissaoException, FalhaDeParametrosException {

        SettingsApplication mSettingsApplication = ((SettingsApplication) activity.getApplication());

        if (senha == null || novaSenha == null) {
            throw new FalhaDeParametrosException();
        }


        JsonObject innerObject = new JsonObject();
        innerObject.addProperty(Usuario.SENHA, senha);
        innerObject.addProperty("novaSenha", novaSenha);

        JsonObject jsonObject = new JsonObject();
        jsonObject.add("usuario", innerObject);

        try {

            RequestBody params = RequestBody.create(JSON, innerObject.toString());

            Usuario usuario = mRepositorioAutenticacao.alterarSenhaUsuario(params, token);

            if (usuario == null) {
                return false;
            }

            if (usuario.email != null && usuario.nome != null && usuario.senha != null && usuario.avatar != null) {

                ControleUsuarioRealm mControleUsuarioRealm = new ControleUsuarioRealm(activity.getBaseContext());
                mControleUsuarioRealm.adicionar(usuario);

                mSettingsApplication.setUsuario(usuario);
                mSettingsApplication.accountName = usuario.email;
                //    mSettingsApplication.accountType = Autenticador.ACCOUNT_TYPE;
                return true;
            }

        } catch (FalhaDeParametrosException | FalhaInternaException e) {
            throw new FalhaErroNoRepositorioException(e);
        }
        return false;
    }

}



