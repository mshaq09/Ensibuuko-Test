package com.ensibuuko.test;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        String realmName = "ensdb";
        RealmConfiguration config = new RealmConfiguration.Builder().name(realmName).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }
}
