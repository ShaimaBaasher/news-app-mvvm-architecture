package com.newsappmvvmarchitecture.domain.core.home.state
import com.newsappmvvmarchitecture.domain.core.NewsEntity

sealed class HomeFragmentState {
    object Init : HomeFragmentState()
    data class IsLoading(val isLoading : Boolean?) : HomeFragmentState()
    data class ShowToast(val message : String?) : HomeFragmentState()
    data class SuccessGetNews(val newsEntity: NewsEntity?) : HomeFragmentState()
    data class SuccessGetFromDb(val newsEntity: NewsEntity?) : HomeFragmentState()
}