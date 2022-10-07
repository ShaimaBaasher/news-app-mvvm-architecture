package com.newsappmvvmarchitecture.domain.core.home.usecase

import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.NewsRepository
import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteLocalNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
     suspend operator fun invoke() {
        return newsRepository.deleteAll()
    }
}
