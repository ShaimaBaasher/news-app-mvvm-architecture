package com.newsappmvvmarchitecture.domain.core

sealed class BaseResult <out T> {
    data class Success <T>(val data : T) : BaseResult<T>()
    data class ErrorMsg <T>(val msg : String) : BaseResult<T>()
    data class Error (val exception: Throwable) : BaseResult<Nothing>()
}