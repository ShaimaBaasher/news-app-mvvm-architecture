package com.newsappmvvmarchitecture.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.newsappmvvmarchitecture.data.utils.Converters
import com.newsappmvvmarchitecture.domain.core.NewsEntityDAO

@Database(entities = [NewsEntityDAO::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}
