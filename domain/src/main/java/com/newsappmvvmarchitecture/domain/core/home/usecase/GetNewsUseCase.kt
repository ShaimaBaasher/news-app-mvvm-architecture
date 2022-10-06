package com.newsappmvvmarchitecture.domain.core.home.usecase

import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import com.newsappmvvmarchitecture.domain.core.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    suspend fun execute(section : String,): Flow<BaseResult<NewsEntity>>  {
        return newsRepository.getNews(section,)
    }
}