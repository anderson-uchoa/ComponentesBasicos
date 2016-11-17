package br.com.pechinchadehoje.modelo;

import java.io.Serializable;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class Usuario{

public String
        _id,
        nome,
        email,
        senha,
        avatar;


    public static final String
            ID = "_id",
            EMAIL = "email",
            SENHA = "senha",
            NOME = "nome",
            AVATAR ="avatar";


    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
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


    public class Token{

        String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

}


