package com.newsappmvvmarchitecture.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.NewsEntityDAO
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsEntity: NewsEntityDAO)

    @Query("SELECT * FROM news_table")
    fun loadAll(): Flow<NewsEntity>

    @Query("DELETE FROM news_table")
    fun deleteAll()
}
