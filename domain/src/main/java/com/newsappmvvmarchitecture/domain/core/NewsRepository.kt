package com.newsappmvvmarchitecture.domain.core


import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews(section: String) : Flow<BaseResult<NewsEntity>>
    suspend fun storeNews(weatherEntity: NewsEntity)
    suspend fun deleteAll()
    fun observeLocalNews(): Flow<BaseResult<NewsEntity>>
}