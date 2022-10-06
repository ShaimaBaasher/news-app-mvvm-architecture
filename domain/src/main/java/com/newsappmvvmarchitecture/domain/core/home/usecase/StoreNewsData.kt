package com.newsappmvvmarchitecture.domain.core.home.usecase

import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.NewsRepository
import javax.inject.Inject

class StoreNewsData @Inject constructor(private val newsRepository: NewsRepository) {
    suspend operator fun invoke(entity: NewsEntity) {
        return newsRepository.storeNews(entity)
    }
}