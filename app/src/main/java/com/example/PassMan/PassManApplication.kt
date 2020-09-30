package com.example.PassMan

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class PassManApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("passman.realm")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}