package com.newsappmvvmarchitecture.www.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AboutViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is a simple News app"
    }
    val text: LiveData<String> = _text
}