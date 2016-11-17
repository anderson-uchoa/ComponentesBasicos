package br.com.pechinchadehoje.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Anderson Uchoa on 13/11/16.
 */

public class Voucher implements Serializable{

    public String
                codigo,
                oferta,
                usuario,
                _id;

    public List<Date> dataValidados;


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<Date> getDataValidados() {
        return dataValidados;
    }

    public void setDataValidados(List<Date> dataValidados) {
        this.dataValidados = dataValidados;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
