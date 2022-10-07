package com.newsappmvvmarchitecture.www.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.newsappmvvmarchitecture.www.R
import com.newsappmvvmarchitecture.www.SharedViewModel
import com.newsappmvvmarchitecture.www.databinding.FragmentDetailsBinding
import com.newsappmvvmarchitecture.www.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private lateinit var sharedViewModel : SharedViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!
    private lateinit var binding: FragmentDetailsBinding

    // get the arguments from the Registration fragment
    private val args : DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val detailsViewModel =
            ViewModelProvider(this)[DetailsViewModel::class.java]

        sharedViewModel = ViewModelProvider(requireActivity()).get<SharedViewModel>(SharedViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        // Receive the arguments in a variable
        val userDetails = args.myArg

        if (sharedViewModel.resultsModel.value?.media?.size!! > 0)
        Picasso.get().load(sharedViewModel.resultsModel.value?.media?.get(0)?.metadata?.get(2)?.url)
            .error(R.mipmap.ic_launcher)
            .into(binding.postImg)

//        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.viewModel = sharedViewModel


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}