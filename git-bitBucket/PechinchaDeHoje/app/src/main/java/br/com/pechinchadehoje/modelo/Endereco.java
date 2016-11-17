package br.com.pechinchadehoje.modelo;

import java.io.Serializable;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */
public class Endereco {

    public String
            cidade,
            cep,
            estado,
            logradouro;

    public Double
            longitude,
            latitude;

    public static final String
            CIDADE = "cidade",
            LONGITUDE = "longitude",
            LATITUDE = "latitude",
            ESTADO = "estado",
            LOGRADOURO = "logradouro",
            CEP = "cep";


    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
