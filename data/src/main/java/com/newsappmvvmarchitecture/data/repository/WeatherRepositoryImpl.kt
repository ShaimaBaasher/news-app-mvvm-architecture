package com.newsappmvvmarchitecture.data.repository

import android.content.Context
import com.newsappmvvmarchitecture.data.APIs
import com.newsappmvvmarchitecture.data.BuildConfig
import com.newsappmvvmarchitecture.data.database.NewsDao
import com.newsappmvvmarchitecture.data.utils.Utils
import com.newsappmvvmarchitecture.domain.core.*
import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import com.newsappmvvmarchitecture.domain.core.home.Results

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.*

const val API_KEY = BuildConfig.API_KEY

class WeatherRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val newsDao: NewsDao,
    private val api: APIs
) : NewsRepository {

    override suspend fun storeNews(newsEntity: NewsEntity) {
        val placeEntity = fromNews(newsEntity)
        newsDao.insert(placeEntity)
    }

    override suspend fun deleteAll() {
        newsDao.deleteAll()
    }

    override suspend fun getNews(section: String): Flow<BaseResult<NewsEntity>> {
        return flow {
            try {
                val response = api.getMostViewedNews(section, API_KEY)

                val body = response.body()
                if (body?.status.equals("OK")) {
                    val newsList = mutableListOf<Results>()
                    body?.results?.forEach {
                        newsList.add(Results(it.id, it.uri, it.url, it.source, it.title, it.abstract, it.published_date, it.media))
                    }
                    emit(BaseResult.Success(NewsEntity(status = body?.status, num_results = body?.num_results, results = newsList)))
                } else {
                    if (body!!.fault != null)
                        emit(BaseResult.ErrorMsg(body.fault!!.faultstring!!))
                }
            } catch (e: Exception) {
                emit(BaseResult.ErrorMsg(Utils.resolveError(e)))
            }
        }
    }

    override  fun observeLocalNews(): Flow<BaseResult<NewsEntity>>{
        return newsDao.loadAll()
            .map { weatherEntities -> BaseResult.Success(weatherEntities) }
    }

}
