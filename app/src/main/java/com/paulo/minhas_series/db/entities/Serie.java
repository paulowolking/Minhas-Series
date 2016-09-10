package com.paulo.minhas_series.db.entities;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Paulo Henrique on 04/09/2016.
 */
public class Serie extends RealmObject {
    @PrimaryKey
    private long id;
    private String nome;
    private RealmList<Temporada> temporadas = new RealmList<>();
    private boolean assistindo;
    private Usuario usuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public RealmList<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(RealmList<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public boolean isAssistindo() {
        return assistindo;
    }

    public void setAssistindo(boolean assistindo) {
        this.assistindo = assistindo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
