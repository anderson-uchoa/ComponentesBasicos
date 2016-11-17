package br.com.pechinchadehoje.database;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;


public class UsuarioRealm extends RealmObject{

    public static final String ID = "id";
    public static final String ID_DEFAULT = "idDefault";

    @PrimaryKey
    private String id;
    private String nome;
    private String email;
    private String senha;
    private String avatar;

    @Index
    private int idDefault;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getIdDefault() {
        return idDefault;
    }

    public void setIdDefault(int idDefault) {
        this.idDefault = idDefault;
    }
}
