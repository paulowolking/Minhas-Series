package com.paulo.minhas_series;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.paulo.minhas_series.db.Migration;
import com.paulo.minhas_series.db.entities.Configuracao;
import com.paulo.minhas_series.db.entities.Usuario;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Paulo Henrique on 04/09/2016.
 */
public class MinhasSeries extends Application {
    private static boolean DEBUG_ENABLE = true;
    private static final String TAG = Application.class.getSimpleName();

    public MinhasSeries() {

    }

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .schemaVersion(Migration.DATABASE_VERSION)
                .migration(new Migration())
                .name(Realm.DEFAULT_REALM_NAME)
                .build();

        Realm.setDefaultConfiguration(realmConfiguration);
        createConfiguracao();

        if (DEBUG_ENABLE)
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                            .build());
    }

    private void createConfiguracao() {
        Realm realm = Realm.getDefaultInstance();
        Configuracao configuracao = realm.where(Configuracao.class).findFirst();

        if (configuracao == null) {
            realm.beginTransaction();
            configuracao = realm.createObject(Configuracao.class);
            configuracao.setId(1);
            realm.copyToRealmOrUpdate(configuracao);
            realm.commitTransaction();
        }
        realm.close();
    }

    public Usuario getUsuarioLogado() {
        Realm realm = Realm.getDefaultInstance();
        Configuracao configuracao = realm.where(Configuracao.class).findFirst();
        return configuracao.getUsuarioLogado();
    }
}
