package com.paulo.minhas_series.ui.actitivitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.paulo.minhas_series.R;
import com.paulo.minhas_series.db.entities.Configuracao;
import com.paulo.minhas_series.db.entities.Usuario;
import com.paulo.minhas_series.ui.resources.RealmActivity;
import com.paulo.minhas_series.ui.resources.SnakeAlert;

public class LoginActivity extends RealmActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextSenha;
    private Button buttonEntrar, buttonRegistrar;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            email = extras.getString("email");

        manageLayout();
    }

    private void manageLayout() {
        editTextEmail = (EditText) findViewById(R.id.et_email);
        editTextSenha = (EditText) findViewById(R.id.et_password);
        buttonEntrar = (Button) findViewById(R.id.bt_entrar);
        buttonRegistrar = (Button) findViewById(R.id.bt_registrar);

        buttonEntrar.setOnClickListener(this);
        buttonRegistrar.setOnClickListener(this);

        if (email != null)
            editTextEmail.setText(email);
    }

    private boolean valid() {
        if (editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError("Informe seu Email");
            editTextEmail.requestFocus();
            return false;
        }

        if (editTextSenha.getText().toString().isEmpty()) {
            editTextSenha.setError("Informe seu Email");
            editTextSenha.requestFocus();
            return false;
        }

        return true;
    }

    private void logar() {
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        Usuario usuario = realm.where(Usuario.class).equalTo("email", email).equalTo("senha", senha).findFirst();
        if (usuario == null) {
            SnakeAlert.alert(this, getString(R.string.mensagem_login_incorreto));
            return;
        }

        realm.beginTransaction();
        Configuracao configuracao = realm.where(Configuracao.class).findFirst();
        if (configuracao == null) {
            configuracao = realm.createObject(Configuracao.class);
            configuracao.setId(1);
        }
        configuracao.setUsuarioLogado(usuario);
        realm.copyToRealmOrUpdate(configuracao);
        realm.commitTransaction();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_entrar:
                if (!valid())
                    return;
                logar();
                break;
            case R.id.bt_registrar:
                startActivity(new Intent(this, RegistroActivity.class));
                break;
        }
    }
}
