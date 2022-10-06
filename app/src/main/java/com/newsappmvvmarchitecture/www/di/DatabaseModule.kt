package com.newsappmvvmarchitecture.www.di

import android.content.Context
import androidx.room.Room
import com.newsappmvvmarchitecture.data.database.AppDatabase
import com.newsappmvvmarchitecture.data.database.NewsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "news-db").build()
    }

    @Singleton
    @Provides
    fun provideNewsDao(database: AppDatabase): NewsDao {
        return database.newsDao()
    }
}
