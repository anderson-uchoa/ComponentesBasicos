package br.com.pechinchadehoje;

import android.app.Application;

import br.com.pechinchadehoje.modelo.Usuario;

public class SettingsApplication extends Application {


    private static SettingsApplication instance = null;
    private String mac;
    private Usuario usuario = null;

    public String accountType;
    public String authTokenType;
    public String accountName;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public SettingsApplication getInstance(){
        return instance;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
