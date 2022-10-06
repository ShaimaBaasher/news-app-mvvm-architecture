package com.shaima.data.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.newsappmvvmarchitecture.data.APIs
import com.newsappmvvmarchitecture.data.database.NewsDao
import com.newsappmvvmarchitecture.data.utils.Utils
import com.newsappmvvmarchitecture.domain.core.*
import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import com.newsappmvvmarchitecture.domain.core.home.Results

import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.*

//const val API_KEY = BuildConfig.API_KEY
const val API_KEY = "5RPYyYmke5sOMMEdJVHvL6SV7ufjGh9V"

class WeatherRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val newsDao: NewsDao,
    private val api: APIs
) : NewsRepository {

    override suspend fun storeNews(newsEntity: NewsEntity) {
        val placeEntity = fromNews(newsEntity)
        Log.d("placeEntity", Gson().toJson(placeEntity))
        newsDao.insert(placeEntity)
    }

    override suspend fun getNews(section: String): Flow<BaseResult<NewsEntity>> {
        return flow {
            try {
                val response = api.getMostViewedNews(section, API_KEY)
                Log.d("responseFromServer", Gson().toJson(response.body()))

                val body = response.body()
                if (body?.status.equals("OK")) {
                    val newsList = mutableListOf<Results>()
                    body?.results?.forEach {
                        newsList.add(Results(it.id, it.uri, it.url, it.source, it.title, it.abstract))
                    }
                    emit(BaseResult.Success(NewsEntity(status = body?.status, num_results = body?.num_results, results = newsList)))
                } else {
                    if (body!!.fault != null)
                        emit(BaseResult.ErrorMsg(body.fault!!.faultstring!!))
                }
            } catch (e: Exception) {
                Log.d("responseFromServerFS", Gson().toJson(e.message))
                emit(BaseResult.ErrorMsg(Utils.resolveError(e)))
            }
        }
    }

    override  fun observeLocalNews(): Flow<BaseResult<NewsEntity>>{
        return newsDao.loadAll()
            .map { weatherEntities -> BaseResult.Success(weatherEntities) }
    }

}