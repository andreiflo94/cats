package com.heixss.cats.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.heixss.cats.CatsApp
import com.heixss.cats.db.AppDatabase
import com.heixss.cats.network.CatsApi
import com.heixss.cats.network.HeadersInterceptor
import com.heixss.cats.utils.SpDataStore
import dagger.Module
import dagger.Provides
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
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: CatsApp): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideCatsApi(retrofit: Retrofit): CatsApi {
        return retrofit.create(CatsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSpDataStore(context: Context): SpDataStore {
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
    fun provideOkHttpCache(context: Context): Cache {
        return Cache(File(context.cacheDir, "okhttp-cache"), (3 * 1000 * 1000).toLong())
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("appprefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context, AppDatabase::class.java, "cats-db")
            .fallbackToDestructiveMigration()
            .build()
    }
}