package com.paulo.minhas_series.ui.actitivitys.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paulo.minhas_series.R;
import com.paulo.minhas_series.db.entities.Serie;
import com.paulo.minhas_series.db.entities.Temporada;
import com.paulo.minhas_series.ui.actitivitys.fragments.ListaSeriesAssistindo;

import java.util.List;

import io.realm.Realm;

public class ListaSeriePrevistaAdapter extends RecyclerView.Adapter<ListaSeriePrevistaAdapter.ListaSerieViewHolder> {
    private List<Serie> series;
    private Context context;

    public ListaSeriePrevistaAdapter(List<Serie> series, Context context) {
        this.series = series;
        this.context = context;
    }

    @Override
    public ListaSerieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_series, parent, false);
        return new ListaSerieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListaSerieViewHolder holder, int position) {
        Serie serie = series.get(position);

        holder.serie = serie;
        holder.textViewNome.setText(serie.getNome());
        holder.textViewQuantidadeTemporada.setText(serie.getTemporadas().size() + " temporada(s)");

        int quantidadeCapitulos = 0;
        for (Temporada temporada : serie.getTemporadas())
            quantidadeCapitulos += temporada.getCapitulos();

        holder.textViewQuantidadeCapitulos.setText(quantidadeCapitulos + " capítulo(s)");
    }

    @Override
    public int getItemCount() {
        return series.size();
    }

    public class ListaSerieViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNome;
        public TextView textViewQuantidadeTemporada;
        public TextView textViewQuantidadeCapitulos;
        public ImageView imageViewMenu;
        public Serie serie;

        public ListaSerieViewHolder(View itemView) {
            super(itemView);
            textViewNome = (TextView) itemView.findViewById(R.id.tv_nome);
            textViewQuantidadeTemporada = (TextView) itemView.findViewById(R.id.tv_quantidade_temporada);
            textViewQuantidadeCapitulos = (TextView) itemView.findViewById(R.id.tv_quantidade_capitulos);
            imageViewMenu = (ImageView) itemView.findViewById(R.id.iv_menu);

            imageViewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopup(imageViewMenu, serie);
                }
            });
        }

        private void showPopup(View view, final Serie serie) {
            View menuItemView = view.findViewById(R.id.iv_menu);
            PopupMenu popup = new PopupMenu(context, menuItemView);
            MenuInflater inflate = popup.getMenuInflater();
            inflate.inflate(R.menu.opcoes_serie_prevista, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.assistir:
                            assistir(serie);
                            break;
                        case R.id.excluir:
                            excluir(serie);
                            break;
                        default:
                            return false;
                    }
                    return false;
                }
            });
            popup.show();
        }

        private void assistir(Serie serie) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            serie.setAssistindo(true);
            realm.copyToRealmOrUpdate(serie);
            realm.commitTransaction();
            realm.close();

            series.remove(serie);
            notifyDataSetChanged();

            ListaSeriesAssistindo listaAmigoFragment = (ListaSeriesAssistindo) ListaSeriesAssistindo.getInstance();
            listaAmigoFragment.loadViews();

        }

        private void excluir(Serie serie) {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            serie.deleteFromRealm();
            realm.copyToRealmOrUpdate(serie);
            realm.commitTransaction();
            realm.close();

            series.remove(serie);
            notifyDataSetChanged();
        }
    }
}
