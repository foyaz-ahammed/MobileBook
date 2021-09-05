package com.play2pay.bookapp.global

import android.app.Application
import com.play2pay.bookapp.module.networkModule
import com.play2pay.bookapp.module.repositoryModule
import com.play2pay.bookapp.module.viewModelModule
import org.koin.core.context.startKoin

class BookApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}