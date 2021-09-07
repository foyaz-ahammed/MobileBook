package com.play2pay.bookapp.global

import android.app.Application
import com.play2pay.bookapp.module.networkModule
import com.play2pay.bookapp.module.preferenceModule
import com.play2pay.bookapp.module.repositoryModule
import com.play2pay.bookapp.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BookApplication)
            modules(networkModule, repositoryModule, viewModelModule, preferenceModule)
        }
    }
}