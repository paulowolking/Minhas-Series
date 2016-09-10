package com.paulo.minhas_series.ui.actitivitys.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.paulo.minhas_series.MinhasSeries;
import com.paulo.minhas_series.R;
import com.paulo.minhas_series.db.entities.Serie;
import com.paulo.minhas_series.ui.actitivitys.adapters.ListaSerieAssistindoAdapter;
import com.paulo.minhas_series.ui.resources.BaseFragment;
import com.paulo.minhas_series.ui.resources.EmptyRecyclerView;
import com.paulo.minhas_series.ui.resources.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paulo Henrique on 04/09/2016.
 */
public class ListaSeriesAssistindo extends BaseFragment {
    private static final String TAG = ListaSeriesAssistindo.class.getSimpleName();

    private static Fragment fragmentInstance;
    public EmptyRecyclerView recyclerView;
    private ListaSerieAssistindoAdapter adapter;
    private List<Serie> series = new ArrayList<>();
    private MinhasSeries minhasSeries;

    public static Fragment newInstance(Bundle args) {
        fragmentInstance = new ListaSeriesAssistindo();
        args.putInt(SectionsPagerAdapter.FRAGMENT_TITLE_RESOURCE_ID, R.string.series);
        fragmentInstance.setArguments(args);
        return fragmentInstance;
    }

    public static Fragment getInstance() {
        if (fragmentInstance == null)
            fragmentInstance = newInstance(new Bundle());
        return fragmentInstance;
    }

    @Override
    public void manageIntent() {
        carregaListagemSeries();
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_listagem;
    }

    @Override
    public void setupViews(View rootView) {
        minhasSeries = (MinhasSeries) getActivity().getApplicationContext();
        recyclerView = (EmptyRecyclerView) rootView.findViewById(R.id.rv_series_previstas);
        series.addAll(realm.where(Serie.class)
                .equalTo("assistindo", true)
                .equalTo("usuario.id", minhasSeries.getUsuarioLogado().getId())
                .findAll());

        if (recyclerView != null)
            recyclerView.setHasFixedSize(true);

        TextView emptyView = (TextView) rootView.findViewById(R.id.tv_empty);
        emptyView.setCompoundDrawablePadding(30);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ListaSerieAssistindoAdapter(series, getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(emptyView);
    }

    @Override
    public void loadViews() {
        carregaListagemSeries();
    }

    private void carregaListagemSeries() {
        if (series == null || realm == null)
            return;

        series.clear();
        series.addAll(realm.where(Serie.class)
                .equalTo("assistindo", true)
                .equalTo("usuario.id", minhasSeries.getUsuarioLogado().getId())
                .findAll());
        adapter.notifyDataSetChanged();
    }

}
