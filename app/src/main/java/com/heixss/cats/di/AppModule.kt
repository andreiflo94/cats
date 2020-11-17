package com.heixss.cats.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.heixss.cats.db.AppDatabase
import com.heixss.cats.network.CatsApi
import com.heixss.cats.network.HeadersInterceptor
import com.heixss.cats.utils.SpDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCatsApi(retrofit: Retrofit): CatsApi {
        return retrofit.create(CatsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSpDataStore(@ApplicationContext context: Context): SpDataStore {
        return SpDataStore(
            context.getSharedPreferences(
                context.packageName + "shared_preferences.persist",
                Context.MODE_PRIVATE
            )
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            // Moshi maps JSON to classes
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache, headersInterceptor: HeadersInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(headersInterceptor)
        client.addInterceptor(
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        client.cache(cache)
        client.connectionPool(ConnectionPool(5, 3, TimeUnit.SECONDS))
        return client.build()
    }

    @Singleton
    @Provides
    fun provideOkHttpCache(@ApplicationContext context: Context): Cache {
        return Cache(File(context.cacheDir, "okhttp-cache"), (3 * 1000 * 1000).toLong())
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("appprefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "cats-db")
            .fallbackToDestructiveMigration()
            .build()
    }
}