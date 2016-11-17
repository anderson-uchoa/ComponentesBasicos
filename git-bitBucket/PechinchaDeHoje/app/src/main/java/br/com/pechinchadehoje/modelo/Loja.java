package br.com.pechinchadehoje.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class Loja {


    public String
                nome,
                email,
                senha,
                descricao,
                segmento,
                logo,
                _id;

    public Endereco
            endereco;

    public boolean ativa;
    public List<String> telefones;

    public static final String
            _ID = "_id",
            NOME_DA_LOJA = "nome",
            SENHA = "senha",
            EMAIL = "email",
            DESCRICAO = "descricao",
            SEGMENTO = "segmento",
            LISTA_DE_TELEFONES = "telefones",
            ATIVA = "ativa",
            LOGO = "logo";


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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public List<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<String> telefones) {
        this.telefones = telefones;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
