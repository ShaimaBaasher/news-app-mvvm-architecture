package com.newsappmvvmarchitecture.data.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.newsappmvvmarchitecture.domain.core.home.Results
import java.lang.reflect.Type


class Converters {

    @TypeConverter
    fun fromNewsList(news: List<Results?>?): String? {
        if (news == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Results?>?>() {}.type
        return gson.toJson(news, type)
    }

    @TypeConverter
    fun toNewsList(news: String?): List<Results>? {
        if (news == null) {
            return null
        }
        val gson = Gson()
        val type =
            object : TypeToken<List<Results?>?>() {}.type
        return gson.fromJson<List<Results>>(news, type)
    }



}