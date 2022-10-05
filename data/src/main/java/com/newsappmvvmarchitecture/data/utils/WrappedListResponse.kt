package com.shaima.data.utils

import com.google.gson.annotations.SerializedName
import com.shaima.domain.home.response.CityResponse
import com.shaima.domain.home.response.WeatherResponse

data class WrappedListResponse(
    @SerializedName("cod") var cod : String,
    @SerializedName("message") var message : String,
    @SerializedName("list") var list : List<WeatherResponse>? = null,
    @SerializedName("city") var city : CityResponse? = null,
)