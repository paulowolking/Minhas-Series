package com.paulo.minhas_series.db.entities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Paulo Henrique on 04/09/2016.
 */
public class Configuracao extends RealmObject {
    @PrimaryKey
    private long id;
    private Usuario usuarioLogado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
