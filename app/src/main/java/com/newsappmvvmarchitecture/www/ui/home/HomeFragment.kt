package com.newsappmvvmarchitecture.www.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.newsappmvvmarchitecture.domain.core.NewsEntity
import com.newsappmvvmarchitecture.domain.core.home.state.HomeFragmentState
import com.newsappmvvmarchitecture.www.R
import com.newsappmvvmarchitecture.www.SharedViewModel
import com.newsappmvvmarchitecture.www.databinding.FragmentHomeBinding
import com.newsappmvvmarchitecture.www.di.SharedPrefModule
import com.newsappmvvmarchitecture.www.ui.home.adapters.NewsAdapter
import com.newsappmvvmarchitecture.www.utils.ext.gone
import com.newsappmvvmarchitecture.www.utils.ext.showToast
import com.newsappmvvmarchitecture.www.utils.ext.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var dayOfWeek: Int = 0
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private lateinit var sharedViewModel: SharedViewModel

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

        sharedViewModel =
            ViewModelProvider(requireActivity()).get<SharedViewModel>(SharedViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        binding.viewModel = homeViewModel
        setupRecyclerView()

        if(homeViewModel.getSavedDayOfWeek() != homeViewModel.getCurrentDayOfWeek())
            homeViewModel.deleteLocalNewsUseCase()

        getFromDb()

        val root: View = binding.root

        homeViewModel.navigateToDetials.observe(requireActivity(), Observer {
            it.getContentIfNotHandled()?.let {
                sharedViewModel.resultsModel.value = it
                val action = HomeFragmentDirections.actionNavigationHomeToNavigationDetails(it)

                // this will navigate the current fragment i.e
                // to the Detail fragment
                findNavController().navigate(action)
            }
        })

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
//                getFromDb()
            }
            is HomeFragmentState.Init -> Unit
        }
    }

    private fun getFromDb() {
        homeViewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                is HomeFragmentState.SuccessGetFromDb -> {
                    if (it.newsEntity == null) {
                        homeViewModel.getMostViewedNews("7")
                        observeNewsState(homeViewModel)
                    } else {
                        it.newsEntity?.let { it1 ->
                            handelNewsPosts(it1)
                        }
                    }
                }
                is HomeFragmentState.IsLoading -> it.isLoading?.let { it1 ->
                    handleLoading(it1)
                }
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