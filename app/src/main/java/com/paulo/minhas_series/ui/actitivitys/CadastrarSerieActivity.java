package com.paulo.minhas_series.ui.actitivitys;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.paulo.minhas_series.R;
import com.paulo.minhas_series.db.entities.Configuracao;
import com.paulo.minhas_series.db.entities.Serie;
import com.paulo.minhas_series.db.entities.Temporada;
import com.paulo.minhas_series.ui.actitivitys.views.ItemTemporada;
import com.paulo.minhas_series.ui.resources.BaseActivity;
import com.paulo.minhas_series.ui.resources.DialogAlert;
import com.paulo.minhas_series.ui.resources.SnakeAlert;

import java.util.ArrayList;
import java.util.List;

public class CadastrarSerieActivity extends BaseActivity implements View.OnClickListener {
    private EditText editTextNome;
    private Button buttonAdicionarTemporada;
    private List<Temporada> temporadaList = new ArrayList<>();
    private LinearLayout linearLayoutTemporadas;
    private Configuracao configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChild(R.layout.activity_cadastrar_serie);
        setupViews();
    }

    @Override
    public void setupViews() {
        editTextNome = (EditText) findViewById(R.id.et_nome);
        linearLayoutTemporadas = (LinearLayout) findViewById(R.id.ll_temporadas);
        buttonAdicionarTemporada = (Button) findViewById(R.id.bt_adicionar_temporada);
        buttonAdicionarTemporada.setOnClickListener(this);
        configuracao = realm.where(Configuracao.class).findFirst();
    }

    @Override
    public void loadViews() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.salvar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save:
                if (!valid())
                    break;
                salvar();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean valid() {
        if (editTextNome.getText().toString().isEmpty()) {
            editTextNome.setError("Informe o nome da série");
            editTextNome.requestFocus();
            return false;
        }

        if (temporadaList.isEmpty()) {
            SnakeAlert.alert(this, getString(R.string.mensagem_cadastrar_temporada));
            return false;
        }
        return true;
    }

    private void dialogAdicionarTemporada() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.adapter_adicionar_temporada, null);

        final EditText editTextDescricao = (EditText) view.findViewById(R.id.et_descricao);
        final EditText editTextQuantidadeCapitulos = (EditText) view.findViewById(R.id.et_quantidade_capitulos);

        int sequencia = temporadaList.size() + 1;
        editTextDescricao.setText(getText(R.string.temporada) + " " + sequencia);

        builder.setPositiveButton("Confirma", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editTextDescricao.getText().toString().isEmpty()) {
                    editTextNome.setError("Informe uma descrição");
                    editTextNome.requestFocus();
                    return;
                }

                if (editTextQuantidadeCapitulos.getText().toString().isEmpty()) {
                    editTextQuantidadeCapitulos.setError("Informe a quantidade de capítulos");
                    editTextQuantidadeCapitulos.requestFocus();
                    return;
                }

                String descricao = editTextDescricao.getText().toString();
                int quantidadeCapitulos = Integer.parseInt(editTextQuantidadeCapitulos.getText().toString());

                realm.beginTransaction();
                final Temporada temporada = realm.createObject(Temporada.class);
                long lastId = realm.where(Temporada.class).max("id").intValue() + 1;
                temporada.setId(lastId);
                temporada.setNome(descricao);
                temporada.setCapitulos(quantidadeCapitulos);
                temporada.setUsuario(configuracao.getUsuarioLogado());
                realm.commitTransaction();

                temporadaList.add(temporada);

                final ItemTemporada itemTemporada = new ItemTemporada(getApplicationContext(), temporada);
                itemTemporada.imageViewExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        DialogAlert.alert(CadastrarSerieActivity.this, getString(R.string.excluir), getString(R.string.mensagem_excluir), getString(R.string.sim), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                linearLayoutTemporadas.removeView(itemTemporada);
                                temporadaList.remove(itemTemporada.temporada);
                            }
                        }, getString(R.string.nao), null);
                    }
                });
                linearLayoutTemporadas.addView(itemTemporada);
            }
        });
        builder.setNegativeButton("Voltar", null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.show();
    }

    private void salvar() {
        String nomeSerie = editTextNome.getText().toString();

        realm.beginTransaction();
        Serie serie = realm.createObject(Serie.class);
        long lastId = realm.where(Serie.class).max("id").intValue() + 1;
        serie.setId(lastId);
        serie.setNome(nomeSerie);
        serie.getTemporadas().addAll(temporadaList);
        serie.setUsuario(configuracao.getUsuarioLogado());
        realm.copyToRealmOrUpdate(serie);
        realm.commitTransaction();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_adicionar_temporada:
                dialogAdicionarTemporada();
                break;
        }
    }
}
