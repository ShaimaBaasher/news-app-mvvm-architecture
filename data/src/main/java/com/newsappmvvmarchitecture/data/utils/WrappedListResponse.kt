package com.shaima.data.utils

import com.google.gson.annotations.SerializedName
import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.fault
import com.newsappmvvmarchitecture.domain.core.home.Results

data class WrappedNewsResponse(
    @SerializedName("status") var status : String,
    @SerializedName("num_results") var num_results : Int,
    @SerializedName("fault") var fault : fault? = null,
    @SerializedName("results") var results : List<Results>? = null,
)