package com.newsappmvvmarchitecture.domain.core.home.usecase

import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.NewsRepository
import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    operator fun invoke(): Flow<BaseResult<NewsEntity>> {
        return newsRepository.observeLocalNews()
    }
}
