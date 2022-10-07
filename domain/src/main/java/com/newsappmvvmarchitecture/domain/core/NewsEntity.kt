package com.newsappmvvmarchitecture.domain.core

import android.os.Parcelable
import androidx.room.*
import com.newsappmvvmarchitecture.domain.core.home.Results
import kotlinx.parcelize.Parcelize

@Entity(tableName = "news_table")
data class NewsEntityDAO(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name="status")
    var status: String,
    @ColumnInfo(name="num_results")
    var num_results: Int,
    @ColumnInfo(name="results")
    var results: List<Results>,
    )

@Parcelize
data class NewsEntity(
    var status: String?,
    var num_results: Int?,
    var results: List<Results>,
) : Parcelable

@Parcelize
data class fault(
    var faultstring: String?,
) : Parcelable



fun fromNews(news: NewsEntity) = NewsEntityDAO(0, news.status!!, news.num_results!!, news.results)
fun NewsEntityDAO.toNews() = NewsEntity(this.status, this.num_results, this.results)
