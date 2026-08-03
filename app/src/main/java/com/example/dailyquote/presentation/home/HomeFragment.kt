package com.example.dailyquote.presentation.home

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.dailyquote.R
import com.example.dailyquote.databinding.FragmentHomeBinding
import com.example.dailyquote.presentation.base.BaseFragment
import com.example.dailyquote.presentation.common.QuoteEvent
import com.example.dailyquote.util.copyToClipboard
import com.example.dailyquote.util.getBitmapFromView
import com.example.dailyquote.util.launchAndRepeatWithViewLifecycle
import com.example.dailyquote.util.saveBitmap
import com.example.dailyquote.util.shareQuote
import com.example.dailyquote.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {

    override val viewModel: HomeViewModel by viewModels()

    override fun setUpViews() {
        binding.apply {
            btnShare.setOnClickListener {
                viewModel.onShareClicked()
            }

            btnCopy.setOnClickListener {
                viewModel.onCopyClicked()
            }

            btnFavorite.setOnClickListener {
                viewModel.onFavoriteClicked()
            }

            btnSave.setOnClickListener {
                viewModel.onSaveClicked()
            }
        }
    }

    override fun observeData() {
        launchAndRepeatWithViewLifecycle {
            viewModel.quote.collect { quote ->
                if (quote != null) {
                    binding.apply {
                        tvQuoteText.text = quote.quote
                        tvQuoteAuthor.text = quote.author
                        cvQuoteContainer.isVisible = true
                        llActions.isVisible = true

                        val icon = if (quote.isFavorite) {
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_fav)
                        } else {
                            ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
                        }
                        btnFavorite.icon = icon
                    }
                } else {
                    binding.apply {
                        cvQuoteContainer.isVisible = false
                        llActions.isVisible = false
                    }
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.isLoading.collect { isLoading ->
                binding.progressBar.isVisible = isLoading
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.error.collect { error ->
                if (error != null) {
                    binding.tvError.text = error
                    binding.tvError.isVisible = true
                } else {
                    binding.tvError.isVisible = false
                }
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.event.collect { event ->
                when (event) {
                    is QuoteEvent.ShareQuote -> {
                        shareQuote(event.quote, event.author)
                    }

                    is QuoteEvent.CopyQuote -> {
                        copyToClipboard(event.quote, event.author)
                    }

                    is QuoteEvent.ShowMessage -> {
                        showToast(event.message)
                    }

                    is QuoteEvent.SaveQuote -> {
                        val bitmap = getBitmapFromView(binding.cvQuoteContainer)
                        saveBitmap(requireContext(), bitmap, "quote_${System.currentTimeMillis()}")
                    }
                }
            }
        }
    }
}