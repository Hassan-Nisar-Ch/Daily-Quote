package com.example.dailyquote.presentation.categories

import androidx.fragment.app.viewModels
import com.example.dailyquote.databinding.FragmentCategoriesBinding
import com.example.dailyquote.presentation.base.BaseFragment
import com.example.dailyquote.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : BaseFragment<FragmentCategoriesBinding, CategoriesViewModel>(
    FragmentCategoriesBinding::inflate
) {

    override val viewModel: CategoriesViewModel by viewModels()

    private val adapter = CategoryAdapter(
        onCategoryClick = { viewModel.onCategoryClick(it) }
    )

    override fun setUpViews() {
        binding.rvQuotes.adapter = adapter
    }

    override fun observeData() {
        launchAndRepeatWithViewLifecycle {
            viewModel.categoriesFlow.collect { categories ->
                adapter.submitList(categories)
            }
        }
    }
}