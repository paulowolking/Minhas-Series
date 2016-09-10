package com.paulo.minhas_series.ui.actitivitys.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paulo.minhas_series.R;
import com.paulo.minhas_series.db.entities.Temporada;

/**
 * Created by Paulo Henrique on 04/09/2016.
 */
public class ItemTemporada extends LinearLayout {
    public TextView textViewNome, textViewQuantidade;
    public ImageView imageViewExcluir;
    public Temporada temporada;

    public ItemTemporada(Context context, Temporada temporada) {
        super(context);

        this.temporada = temporada;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.adapter_item_temporada, this);

        textViewNome = (TextView) findViewById(R.id.tv_nome);
        imageViewExcluir = (ImageView) findViewById(R.id.iv_excluir);
        textViewQuantidade = (TextView) findViewById(R.id.tv_quantidadeCapitulos);

        textViewNome.setText(temporada.getNome());
        textViewQuantidade.setText(temporada.getCapitulos() + " capítulos");
    }
}
