package com.newsappmvvmarchitecture.www.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.home.Results
import com.newsappmvvmarchitecture.domain.core.home.state.HomeFragmentState
import com.newsappmvvmarchitecture.www.R
import com.newsappmvvmarchitecture.www.databinding.FragmentHomeBinding
import com.newsappmvvmarchitecture.www.ui.home.adapters.NewsAdapter
import com.newsappmvvmarchitecture.www.utils.ext.gone
import com.newsappmvvmarchitecture.www.utils.ext.showToast
import com.newsappmvvmarchitecture.www.utils.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.viewModel = homeViewModel
        setupRecyclerView()
        homeViewModel.getMostViewedNews("7")
        observeNewsState(homeViewModel)
        val root: View = binding.root
        return root
    }

    private fun observeNewsState(viewModel: HomeViewModel) {
        viewModel.mStateNews
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: HomeFragmentState) {
        when (state) {
            is HomeFragmentState.SuccessGetNews -> state.newsEntity?.let {
                handelNewsPosts(it)
                homeViewModel.storeNew(it)
            }
            is HomeFragmentState.IsLoading -> state.isLoading?.let { handleLoading(it) }
            is HomeFragmentState.ShowToast -> {
                state.message?.let { requireActivity().showToast(it) }
                getFromDb()
            }
            is HomeFragmentState.Init -> Unit
        }
    }

    private fun getFromDb() {
        homeViewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeFragmentState.SuccessGetFromDb -> it.newsEntity?.let { it1 ->
                    if(it.newsEntity!!.results.isNotEmpty()) {
                        handelNewsPosts(it1)
                    }
                }
                is HomeFragmentState.IsLoading -> it.isLoading?.let { it1 -> handleLoading(it1) }
                is HomeFragmentState.ShowToast -> it.message?.let { it1 ->
                    requireActivity().showToast(it1)
                }
                is HomeFragmentState.Init -> Unit
            }
        }
    }

    private fun handelNewsPosts(results: NewsEntity) {
        binding.newsRecyclerView.adapter?.let {
            if (it is NewsAdapter) {
                it.updateList(results.results)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingProgressBar.visible()
        } else {
            binding.loadingProgressBar.gone()
        }
    }

}