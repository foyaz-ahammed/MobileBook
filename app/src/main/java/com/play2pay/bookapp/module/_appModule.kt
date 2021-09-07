package com.play2pay.bookapp.module

import android.content.Context
import android.content.SharedPreferences
import com.play2pay.bookapp.helper.BASE_URL
import com.play2pay.bookapp.helper.BOOK_SHARED_PREFERENCE
import com.play2pay.bookapp.repository.BookRepository
import com.play2pay.bookapp.repository.api.BookAPI
import com.play2pay.bookapp.viewmodels.MainViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/* Define module variables */
val networkModule = module {
    single { provideRetrofit(get(), get()) }
    single { provideOkHttpClient() }
    single { provideMoshi() }
}

val repositoryModule = module {
    single { BookRepository(get()) }
    single { provideAPI(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

val preferenceModule = module {
    single { provideSharedPref(androidContext()) }
}

/**
 * @return [Retrofit] instance
 */
private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi
): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(okHttpClient)
    .build()

/**
 * @return [OkHttpClient] instance
 */
private fun provideOkHttpClient() = OkHttpClient.Builder()
    .readTimeout(10L, TimeUnit.SECONDS)
    .readTimeout(15L, TimeUnit.SECONDS)
    .addInterceptor(
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    )
    .build()

/**
 * @return [Moshi] instance
 */
private fun provideMoshi(): Moshi = Moshi.Builder().build()

/**
 * @return [BookAPI] instance
 */
private fun provideAPI(retrofit: Retrofit): BookAPI = retrofit.create(BookAPI::class.java)

/**
 * @return [SharedPreferences] instance
 */
private fun provideSharedPref(context: Context): SharedPreferences
    = context.applicationContext.getSharedPreferences(BOOK_SHARED_PREFERENCE, Context.MODE_PRIVATE)