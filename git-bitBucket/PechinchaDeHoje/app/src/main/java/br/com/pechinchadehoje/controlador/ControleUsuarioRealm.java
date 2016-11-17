package br.com.pechinchadehoje.controlador;

import android.content.Context;

import br.com.pechinchadehoje.database.UsuarioRealm;
import br.com.pechinchadehoje.database.constantes.Constantes;
import br.com.pechinchadehoje.modelo.Usuario;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmException;


public class ControleUsuarioRealm {

    protected Realm realm;

    public ControleUsuarioRealm(Context context) {
        RealmConfiguration realmConfiguration =
                new RealmConfiguration.Builder(context)
                        .deleteRealmIfMigrationNeeded()
                        .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        this.realm = Realm.getDefaultInstance();
    }

    public boolean adicionar(Usuario usuario) {

        UsuarioRealm usuarioRealm = pessoaToPessoaRealm(usuario);

        try {
            this.realm.beginTransaction();
            realm.copyToRealmOrUpdate(usuarioRealm);
            this.realm.commitTransaction();
        } catch (RealmException ex) {
            this.realm.cancelTransaction();
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean alterar(Usuario usuario){

        try{
            this.realm.beginTransaction();

            this.realm.copyToRealmOrUpdate(pessoaToPessoaRealm(usuario));

            this.realm.commitTransaction();
        }catch (RealmException ex){
            this.realm.cancelTransaction();
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    public Usuario buscar(String id) {
        UsuarioRealm usuarioRealm = this.realm.where(UsuarioRealm.class).equalTo(UsuarioRealm.ID, id).findFirst();
        if (usuarioRealm != null) {
            return usuarioRealmToUsuario(usuarioRealm);
        }
        return null;
    }

    public Usuario buscarPorIdDefault() {
        UsuarioRealm pessoaRealm = this.realm.where(UsuarioRealm.class).equalTo(UsuarioRealm.ID_DEFAULT, Constantes.ID_DEFAULT).findFirst();
        if (pessoaRealm != null) {
            return usuarioRealmToUsuario(pessoaRealm);
        }
        return null;
    }

    public boolean remover(String id) {
        try {

            realm.beginTransaction();

            UsuarioRealm entity = this.realm.where(UsuarioRealm.class).equalTo(UsuarioRealm.ID, id).findFirst();
            if(entity == null) return false;

            entity.removeFromRealm();
            realm.commitTransaction();

        } catch (RealmException realmException) {
            realmException.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean remover() {
        try {

            realm.beginTransaction();

            UsuarioRealm entity = this.realm.where(UsuarioRealm.class).equalTo(UsuarioRealm.ID_DEFAULT, Constantes.ID_DEFAULT).findFirst();
            if(entity == null) return false;

            entity.removeFromRealm();
            realm.commitTransaction();

        } catch (RealmException realmException) {
            realmException.printStackTrace();
            return false;
        }
        return true;
    }

    public void close() {
        this.realm.close();
    }

    private UsuarioRealm pessoaToPessoaRealm(Usuario usuario) {
        UsuarioRealm usuarioRealm = new UsuarioRealm();
        usuarioRealm.setId(usuario._id);
        usuarioRealm.setNome(usuario.nome);
        usuarioRealm.setEmail(usuario.email);
        usuarioRealm.setEmail(usuario.senha);
        usuarioRealm.setAvatar(usuario.avatar);
        usuarioRealm.setIdDefault(Constantes.ID_DEFAULT);

        return usuarioRealm;
    }

    private Usuario usuarioRealmToUsuario(UsuarioRealm usuarioRealm) {
        Usuario usuario = new Usuario();
        usuario._id = usuarioRealm.getId();
        usuario.nome = usuarioRealm.getNome();
        usuario.email = usuarioRealm.getEmail();
        usuario.senha = usuarioRealm.getSenha();
        usuario.avatar = usuarioRealm.getAvatar();
        return usuario;
    }


}
