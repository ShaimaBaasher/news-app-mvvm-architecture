package com.newsappmvvmarchitecture.www.di

import android.content.Context
import com.newsappmvvmarchitecture.data.APIs
import com.newsappmvvmarchitecture.data.database.NewsDao
import com.newsappmvvmarchitecture.domain.core.NewsRepository
import com.newsappmvvmarchitecture.data.repository.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(@ApplicationContext context: Context,
                                 weatherDao: NewsDao, api: APIs
    ) : NewsRepository {
        return WeatherRepositoryImpl(context, weatherDao, api)
    }
    
}