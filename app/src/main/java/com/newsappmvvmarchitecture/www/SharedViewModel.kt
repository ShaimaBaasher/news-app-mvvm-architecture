package com.newsappmvvmarchitecture.www

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsappmvvmarchitecture.domain.core.home.Results

class SharedViewModel : ViewModel() {

    var resultsModel : MutableLiveData<Results> = MutableLiveData()

}