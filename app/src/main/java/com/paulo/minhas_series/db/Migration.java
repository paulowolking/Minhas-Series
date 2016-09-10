package com.paulo.minhas_series.db;


import io.realm.DynamicRealm;
import io.realm.RealmMigration;

public class Migration implements RealmMigration {

    public static final long DATABASE_VERSION = 1;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    }
}