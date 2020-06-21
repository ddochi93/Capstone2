package com.example.harusikdan

import android.app.Application
import com.example.harusikdan.koin.modules.networkModule
import com.example.harusikdan.koin.modules.usecaseModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HarusikdanApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        startKoin {
            androidLogger()
            androidContext(this@HarusikdanApplication)
            modules(listOf(networkModule, usecaseModule))
        }
    }

}