package com.djordjekrutil.mozzartgreek.core.di

import android.content.Context
import com.djordjekrutil.mozzartgreek.BuildConfig
import com.djordjekrutil.mozzartgreek.feature.db.AppDatabase
import com.djordjekrutil.mozzartgreek.feature.repository.DrawsSelectedNumbersRepository
import com.djordjekrutil.mozzartgreek.feature.repository.GamesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule() {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .client(createClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun provideDrawsSelectedNumbersRepository(drawsSelectedNumbersDatabaseSource: DrawsSelectedNumbersRepository.Database): DrawsSelectedNumbersRepository.IDatabase =
        drawsSelectedNumbersDatabaseSource

    @Provides
    @Singleton
    fun provideGamesRepository(gamesDatabaseSource: GamesRepository.Network): GamesRepository.INetwork =
        gamesDatabaseSource
}