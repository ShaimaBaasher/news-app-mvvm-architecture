package com.newsappmvvmarchitecture.www.di

import com.newsappmvvmarchitecture.data.APIs
import com.newsappmvvmarchitecture.data.BuildConfig
import com.newsappmvvmarchitecture.www.di.NetworkModule.component.BASE_URL

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@Suppress("unused")
@InstallIn(SingletonComponent::class)
class NetworkModule {

    object component {
        const val BASE_URL = BuildConfig.API_BASE_URL
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient) : Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create())
            client(okHttp)
            baseUrl(BASE_URL)
        }.build()
    }

    @Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if(BuildConfig.DEBUG)
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            addInterceptor(logging)
        }.build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit) : APIs {
        return retrofit.create(APIs::class.java)
    }


}
