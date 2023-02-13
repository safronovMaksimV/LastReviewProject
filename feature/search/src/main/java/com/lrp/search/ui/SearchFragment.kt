package com.lrp.search.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lrp.base.utils.viewBinding
import com.lrp.search.R
import com.lrp.search.databinding.FragmentSearchBinding
import com.lrp.search.ui.composable.SearchView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeHolder.setContent {
            SearchView(viewModel)
        }
    }
}