package com.paulo.minhas_series.ui.actitivitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paulo.minhas_series.R;
import com.paulo.minhas_series.db.entities.Usuario;
import com.paulo.minhas_series.ui.resources.RealmActivity;
import com.paulo.minhas_series.ui.resources.SnakeAlert;

public class RegistroActivity extends RealmActivity implements View.OnClickListener {

    private EditText editTextNome, editTextEmail, editTextSenha, editTextConfirmaSenha;
    private Button buttonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        manageLayout();
    }

    private void manageLayout() {
        editTextNome = (EditText) findViewById(R.id.et_nome);
        editTextEmail = (EditText) findViewById(R.id.et_email);
        editTextSenha = (EditText) findViewById(R.id.et_senha);
        editTextConfirmaSenha = (EditText) findViewById(R.id.et_confirma_senha);
        buttonCadastrar = (Button) findViewById(R.id.bt_cadastrar);

        buttonCadastrar.setOnClickListener(this);
    }

    private void save() {
        String nome = editTextNome.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();

        realm.beginTransaction();
        Usuario usuario = realm.createObject(Usuario.class);
        long lastId = realm.where(Usuario.class).max("id").intValue() + 1;
        usuario.setId(lastId);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        realm.copyToRealmOrUpdate(usuario);
        realm.commitTransaction();
    }

    private boolean valid() {
        if (editTextNome.getText().toString().isEmpty()) {
            editTextNome.setError("Informe seu Nome");
            editTextNome.requestFocus();
            return false;
        }

        if (editTextEmail.getText().toString().isEmpty()) {
            editTextEmail.setError("Informe seu Email");
            editTextEmail.requestFocus();
            return false;
        }

        Usuario usuarioComEmail = realm.where(Usuario.class).equalTo("email", editTextEmail.getText().toString()).findFirst();
        if (usuarioComEmail != null) {
            SnakeAlert.alert(this, getString(R.string.usuario_ja_cadastrado));
            return false;
        }

        boolean emailValido = Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches();
        if (!emailValido) {
            editTextEmail.setError("Informe um email válido");
            return false;
        }

        if (editTextSenha.getText().toString().isEmpty()) {
            editTextSenha.setError("Informe sua Senha");
            editTextSenha.requestFocus();
            return false;
        }

        if (editTextConfirmaSenha.getText().toString().isEmpty()) {
            editTextConfirmaSenha.setError("Informe sua senha novamente");
            editTextConfirmaSenha.requestFocus();
            return false;
        }

        if (!editTextSenha.getText().toString().equals(editTextConfirmaSenha.getText().toString())) {
            Toast.makeText(this, "Senhas são diferentes", Toast.LENGTH_SHORT).show();
            editTextSenha.setText("");
            editTextConfirmaSenha.setText("");
            editTextSenha.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_cadastrar:
                if (!valid())
                    return;
                save();
                SnakeAlert.alert(this, getString(R.string.cadastro_sucesso));
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("email", editTextEmail.getText().toString());
                startActivity(intent);
                finish();
                break;
        }
    }
}
