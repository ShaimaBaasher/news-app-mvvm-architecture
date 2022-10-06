package com.newsappmvvmarchitecture.domain.core.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results(
    @SerializedName("id") var id : Long?,
    @SerializedName("uri") var uri : String?,
    @SerializedName("url") var url : String?,
    @SerializedName("source") var source : String?,
    @SerializedName("title") var title : String?,
    @SerializedName("abstract") var abstract : String?,
//    @SerializedName("media") var weather : List<media>?,
    ) : Parcelable

@Parcelize
data class media(
    @SerializedName("temp") var temp : String? = null,
    @SerializedName("caption") var humidity : String? = null, ) : Parcelable
