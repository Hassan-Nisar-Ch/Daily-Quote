package com.example.dailyquote.presentation.home

import androidx.fragment.app.viewModels
import com.example.dailyquote.databinding.FragmentHomeBinding
import com.example.dailyquote.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()
}