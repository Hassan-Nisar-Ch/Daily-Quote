package com.example.dailyquote.presentation.categories

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        onCategoryClick = { name ->
            viewModel.onCategoryClick(name)
        }
    )

    override fun setUpViews() {
        binding.rvCategories.adapter = adapter
    }

    override fun observeData() {
        launchAndRepeatWithViewLifecycle {
            viewModel.categoriesFlow.collect { categories ->
                adapter.submitList(categories)
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.event.collect { event ->
                when (event) {
                    is CategoriesViewModel.CategoriesEvent.NavigateToQuotes -> {
                        val action = CategoriesFragmentDirections.actionCategoriesFragmentToQuotesFragment(
                        category = event.name
                        )
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }
}