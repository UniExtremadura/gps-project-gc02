package com.example.gc02
import android.app.Application
import com.example.gc02.utils.AppContainer

class RetroReuseApplication : Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
