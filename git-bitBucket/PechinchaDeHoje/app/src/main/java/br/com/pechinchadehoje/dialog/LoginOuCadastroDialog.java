package br.com.pechinchadehoje.dialog;


import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;

import br.com.pechinchadehoje.R;
import br.com.pechinchadehoje.activity.MainActivity;
import br.com.pechinchadehoje.controlador.ControladorAutenticacao;
import br.com.pechinchadehoje.controlador.excecoes.FalhaErroNoRepositorioException;
import br.com.pechinchadehoje.modelo.Oferta;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaDeParametrosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaNaoHaResultadosException;
import br.com.pechinchadehoje.modelo.repositorio.excecoes.FalhaSemPermissaoException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginOuCadastroDialog extends DialogFragment {

    private static final String TAG_FRAGMENT = "LOGIN_CADASTRO_DIALOG";

    private Toast mToast;

    private EditText email;
    private EditText senha;

    private RealizarLoginTask realizarLoginTask;
    private CriarContaTask criarContaTask;

    private static AppCompatActivity context;


    public static void show(AppCompatActivity context) {
        LoginOuCadastroDialog.context = context;
        LoginOuCadastroDialog loginOuCadastroDialog = new LoginOuCadastroDialog();
        loginOuCadastroDialog.show(context.getSupportFragmentManager(), "[ABOUT_DIALOG]");
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

       MaterialDialog dialog = new MaterialDialog.Builder(LoginOuCadastroDialog.context)
                .title(R.string.loginPechincha)
                .customView(R.layout.login_cadastro_dialog, true)
                .positiveText(R.string.entrar)
                .negativeText(R.string.criarConta)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                           email = (EditText) dialog.getCustomView().findViewById(R.id.etemail);
                           senha = (EditText) dialog.getCustomView().findViewById(R.id.eTSenha);

                         //   realizarLoginTask = new RealizarLoginTask(email.getText().toString(), senha.getText().toString());
                           // realizarLoginTask.execute();
                            System.out.print("ENTROUUUUU");
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final MediaType JSON
                                            = MediaType.parse("application/json; charset=utf-8");

                                    OkHttpClient client = new OkHttpClient();
                                    JsonObject innerObject = new JsonObject();
                                    innerObject.addProperty("email", "alexsandro@mail.com");
                                    innerObject.addProperty("senha", "1234");
                                    RequestBody body = RequestBody.create(JSON, innerObject.toString());
                                    Request request = new Request.Builder()
                                            .url("http://ec2-52-38-136-232.us-west-2.compute.amazonaws.com:3000/usuario/public/auth")
                                            .post(body)
                                            .build();
                                    try {
                                        Response response = client.newCall(request).execute();
                                        System.out.print(response.body().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                    }
                })
               .onNegative(new MaterialDialog.SingleButtonCallback() {
                   @Override
                   public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                   }
               }).build();


    return dialog;
    }


    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
        mToast.show();
    }


    private class RealizarLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        RealizarLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            try {
                boolean resposta = new ControladorAutenticacao(LoginOuCadastroDialog.context).logar(mEmail, mPassword);
                return resposta;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            realizarLoginTask = null;
            if (success) {
                Log.i(RealizarLoginTask.class.toString(), "Deu certo!");
            } else {
                Log.i(RealizarLoginTask.class.toString(), "Deu merda!");
            }
        }

        @Override
        protected void onCancelled() {
            realizarLoginTask = null;

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(realizarLoginTask != null){
            realizarLoginTask.cancel(true);
        }
    }

    private class CriarContaTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return null;
        }
    }
}
