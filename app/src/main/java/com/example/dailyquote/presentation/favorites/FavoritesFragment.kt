package com.example.dailyquote.presentation.favorites

import androidx.fragment.app.viewModels
import com.example.dailyquote.databinding.FragmentFavoritesBinding
import com.example.dailyquote.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(
    FragmentFavoritesBinding::inflate
) {

    override val viewModel: FavoritesViewModel by viewModels()

    override fun setUpViews() {
    }

    override fun observeData() {
    }
}