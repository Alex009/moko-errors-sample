package ru.alex009.moko.errors.myapplication.android

import android.app.Application
import ru.alex009.moko.errors.myapplication.Configurator

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Configurator.init()
    }
}
