package com.newsappmvvmarchitecture.www.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.core.BaseResult
import com.newsappmvvmarchitecture.domain.core.home.Results
import com.newsappmvvmarchitecture.domain.core.home.state.HomeFragmentState
import com.newsappmvvmarchitecture.domain.core.home.usecase.GetLocalNewsUseCase
import com.newsappmvvmarchitecture.domain.core.home.usecase.GetNewsUseCase
import com.newsappmvvmarchitecture.domain.core.home.usecase.StoreNewsData
import com.newsappmvvmarchitecture.www.ui.home.adapters.NewsAdapter
import com.newsappmvvmarchitecture.www.ui.home.adapters.OnPostClickListener
import com.newsappmvvmarchitecture.www.utils.ClickEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val storeNewsData: StoreNewsData,
    private val getLocalNewsUseCase: GetLocalNewsUseCase
) : ViewModel() , OnPostClickListener {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _navigateToDetials = MutableLiveData<ClickEvent<Results>>()
    val navigateToDetials : LiveData<ClickEvent<Results>>
        get() = _navigateToDetials

    private val state = MutableStateFlow<HomeFragmentState>(HomeFragmentState.Init)
    val mStateNews: StateFlow<HomeFragmentState> get() = state
    val newsAdapter = NewsAdapter(mutableListOf(), this)

    fun getMostViewedNews(section: String) {
        viewModelScope.launch {
            getNewsUseCase.execute(section)
                .onStart {
                    setLoading()
                }
                .catch {
                    hideLoading()
                    showToast(it.message.toString())
                }
                .collect {
                    hideLoading()
                    when (it) {
                        is BaseResult.Success -> state.value =
                            HomeFragmentState.SuccessGetNews(it.data)
                        is BaseResult.ErrorMsg -> state.value =
                            HomeFragmentState.ShowToast(it.msg)
                        is BaseResult.Error -> state.value =
                            HomeFragmentState.ShowToast(it.exception.localizedMessage)
                    }
                }
        }
    }

    fun storeNew(newsEntity: NewsEntity) = viewModelScope.launch {
        storeNewsData(
            NewsEntity(
                newsEntity.status,
                newsEntity.num_results,
                newsEntity.results,
            )
        )
    }

    @OptIn(InternalCoroutinesApi::class)
    val viewState = getLocalNewsUseCase()
        .map { result ->
            when (result) {
                is BaseResult.Success -> HomeFragmentState.SuccessGetFromDb(result.data)
                is BaseResult.ErrorMsg -> result.msg.let { HomeFragmentState.ShowToast(it) }
                is BaseResult.Error -> Log.d("exceptionMessage", result.exception.localizedMessage)
            }
        }.asLiveData(viewModelScope.coroutineContext)

    private fun setLoading() {
        state.value = HomeFragmentState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = HomeFragmentState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = HomeFragmentState.ShowToast(message)
    }

    override fun onPostClickListener(dataModel: Results) {
        _navigateToDetials.value = ClickEvent(dataModel)  // Trigger the event by setting a new Event as a new value
    }
}