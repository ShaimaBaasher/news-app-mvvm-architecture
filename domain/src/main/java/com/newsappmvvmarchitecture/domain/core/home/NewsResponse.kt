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
    @SerializedName("published_date") var published_date : String?,
    @SerializedName("media") var media : List<media>?,
    ) : Parcelable

@Parcelize
data class media(
    @SerializedName("media-metadata") var metadata : List<metadata>? = null,
) : Parcelable

@Parcelize
data class metadata(
    @SerializedName("url") var url : String? = null,
) : Parcelable
