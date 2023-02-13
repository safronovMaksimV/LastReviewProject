package com.lrp.home.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lrp.base.utils.viewBinding
import com.lrp.home.R
import com.lrp.home.databinding.FragmentHomeBinding
import com.lrp.home.ui.composable.HomeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureAppBar()

        binding.composeHolder.setContent {
            HomeView(viewModel)
        }
    }

    private fun configureAppBar() {
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }
}