package br.com.pechinchadehoje.modelo.repositorio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import br.com.pechinchadehoje.modelo.Usuario;

/**
 * Created by Anderson Uchoa on 14/11/16.
 */


public class Test {

    public static void main(String[] args) {



        JsonObject innerObject = new JsonObject();
        innerObject.addProperty(Usuario.EMAIL, "email");
        innerObject.addProperty(Usuario.SENHA, "senha");


        System.out.println(innerObject);





       // Gson parser = new Gson();

       // String body = parser.toJson(usuario, Usuario.class);

//        System.out.println(body);
    }
}
