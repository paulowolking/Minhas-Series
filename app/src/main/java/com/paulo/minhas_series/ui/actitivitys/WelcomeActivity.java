package com.paulo.minhas_series.ui.actitivitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.paulo.minhas_series.MinhasSeries;
import com.paulo.minhas_series.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        MinhasSeries application = (MinhasSeries) getApplicationContext();

        if (application.getUsuarioLogado() != null) {
            startActivity(new Intent(this, DashboardActivity.class));
            finish();
        } else {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }, 2000);
        }
    }
}
