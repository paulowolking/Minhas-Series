package com.paulo.minhas_series.ui.actitivitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.paulo.minhas_series.R;
import com.paulo.minhas_series.ui.actitivitys.fragments.ListaSeriesAssistindo;
import com.paulo.minhas_series.ui.actitivitys.fragments.ListaSeriesPrevista;
import com.paulo.minhas_series.ui.resources.BaseActivity;
import com.paulo.minhas_series.ui.resources.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends BaseActivity {
    public static final int FRAGMENT_SERIES_ASSISTINDO = 0;
    public static final int FRAGMENT_SERIES_PREVISTA = 1;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private List<Fragment> fragmentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChild(R.layout.activity_dashboard);
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListaSeriesAssistindo listaSeriesAssistindo = (ListaSeriesAssistindo) ListaSeriesAssistindo.getInstance();
        ListaSeriesPrevista listaSeriesPrevista = (ListaSeriesPrevista) ListaSeriesPrevista.getInstance();

        listaSeriesAssistindo.loadViews();
        listaSeriesPrevista.loadViews();
    }

    @Override
    public void setupViews() {
        fragmentos = new ArrayList<>();
        fragmentos.add(FRAGMENT_SERIES_ASSISTINDO, ListaSeriesAssistindo.newInstance(new Bundle()));
        fragmentos.add(FRAGMENT_SERIES_PREVISTA, ListaSeriesPrevista.newInstance(new Bundle()));

        mSectionsPagerAdapter = new SectionsPagerAdapter(this, fragmentos);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void loadViews() {

    }
}
