package br.com.pechinchadehoje.modelo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class Oferta implements  Serializable{

    public String
        categoria,
        descricao,
        tag,
        segmento,
        loja,
        _id;

    public boolean
            adicional,
            ativa;

    public Double
                valorComDesconto,
                valorSemDesconto;

    public int
                limitePorPessoa,
                limiteTotal;

    public Date dataInicio,
            dataFim;


    public static final String
            CATEGORIA = "cidade",
            DESCRICAO = "longitude",
            TAG = "latitude",
            SEGMENTO = "estado",
            ADICIONAL = "logradouro",
            VALOR_COM_DESCONTO = "valorComDesconto",
            VALOR_SEM_DESCONTO = "valorSemDesconto",
            LIMITE_POR_PESSOA = "limitePorPessoa",
            LIMITE_TOTAL = "limiteTotal",
            DATA_FIM = "dataFim",
            DATA_INICIO = "dataInicio",
             _ID = "_id";

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public boolean isAdicional() {
        return adicional;
    }

    public void setAdicional(boolean adicional) {
        this.adicional = adicional;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Double getValorComDesconto() {
        return valorComDesconto;
    }

    public void setValorComDesconto(Double valorComDesconto) {
        this.valorComDesconto = valorComDesconto;
    }

    public Double getValorSemDesconto() {
        return valorSemDesconto;
    }

    public void setValorSemDesconto(Double valorSemDesconto) {
        this.valorSemDesconto = valorSemDesconto;
    }

    public int getLimitePorPessoa() {
        return limitePorPessoa;
    }

    public void setLimitePorPessoa(int limitePorPessoa) {
        this.limitePorPessoa = limitePorPessoa;
    }

    public int getLimiteTotal() {
        return limiteTotal;
    }

    public void setLimiteTotal(int limiteTotal) {
        this.limiteTotal = limiteTotal;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

