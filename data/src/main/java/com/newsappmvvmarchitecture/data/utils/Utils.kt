package com.newsappmvvmarchitecture.data.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


object Utils {

    fun resolveError(e: Exception): String {
        var error = e
        when (e) {
            is SocketTimeoutException -> {
                error = NetworkErrorException(errorMessage = "connection error!")
            }
            is ConnectException -> {
                error = NetworkErrorException(errorMessage = "no internet access!")
            }
            is UnknownHostException -> {
                error = NetworkErrorException(errorMessage = "no internet access!")
            }
        }

        if (e is HttpException) {
            when (e.code()) {
                502 -> {
                    error = NetworkErrorException(e.code(), "internal error!")
                }
                401 -> {
                    throw AuthenticationException("authentication error!")
                }
                400 -> {
                    error = NetworkErrorException.parseException(e)
                }
            }
        }
        return error.localizedMessage
    }



    fun hideKeyboardFrom(context: Context, view: View) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}
