package com.paulo.minhas_series.ui.resources;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.paulo.minhas_series.R;
import com.paulo.minhas_series.db.entities.Configuracao;
import com.paulo.minhas_series.ui.actitivitys.CadastrarSerieActivity;
import com.paulo.minhas_series.ui.actitivitys.DashboardActivity;
import com.paulo.minhas_series.ui.actitivitys.LoginActivity;

public abstract class BaseActivity extends RealmActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_series:
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                break;

            case R.id.nav_adicionar:
                startActivity(new Intent(getApplicationContext(), CadastrarSerieActivity.class));
                break;

            case R.id.nav_sair:
                sair();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setChild(int activityReference) {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.content_layout);
        LayoutInflater layoutInflater = getLayoutInflater();
        View childLayout = layoutInflater.inflate(activityReference, frameLayout);
    }

    public abstract void setupViews();

    public abstract void loadViews();

    private void sair() {
        DialogAlert.alert(this, getString(R.string.sair), getString(R.string.mensagem_sair), getString(R.string.sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                realm.beginTransaction();
                realm.delete(Configuracao.class);
                realm.commitTransaction();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, getString(R.string.voltar), null);

    }
}
