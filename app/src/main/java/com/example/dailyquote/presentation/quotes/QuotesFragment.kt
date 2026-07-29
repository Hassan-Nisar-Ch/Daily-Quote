package com.example.dailyquote.presentation.quotes

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.dailyquote.databinding.FragmentQuotesBinding
import com.example.dailyquote.presentation.base.BaseFragment
import com.example.dailyquote.presentation.favorites.QuoteAdapter
import com.example.dailyquote.presentation.home.QuoteEvent
import com.example.dailyquote.util.copyToClipboard
import com.example.dailyquote.util.getBitmapFromView
import com.example.dailyquote.util.launchAndRepeatWithViewLifecycle
import com.example.dailyquote.util.saveBitmap
import com.example.dailyquote.util.shareQuote
import com.example.dailyquote.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding, QuotesViewModel>(
    FragmentQuotesBinding::inflate
) {

    override val viewModel: QuotesViewModel by viewModels()
    private val args: QuotesFragmentArgs by navArgs()

    private val adapter = QuoteAdapter(
        onShareClick = { viewModel.onShareClicked(it) },
        onCopyClick = { viewModel.onCopyClicked(it) },
        onSaveClick = { viewModel.onSaveClicked(it) },
        onFavoriteClick = { viewModel.onFavoriteClicked(it) }
    )

    override fun setUpViews() {
        binding.apply {
            title.text = args.displayName
            rvQuotes.adapter = adapter
        }
        viewModel.getQuotesByCategory(args.category)
    }

    override fun observeData() {
        launchAndRepeatWithViewLifecycle {
            viewModel.quotes.collect { quotes ->
                adapter.submitList(quotes)
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.isLoading.collect { isLoading ->
                // Add a progress bar if needed in layout
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.error.collect { error ->
                if (error != null) {
                    showToast(error)
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
                        val bitmap = getBitmapFromView(event.view)
                        saveBitmap(requireContext(), bitmap, "quote_${System.currentTimeMillis()}")
                    }
                }
            }
        }
    }
}
