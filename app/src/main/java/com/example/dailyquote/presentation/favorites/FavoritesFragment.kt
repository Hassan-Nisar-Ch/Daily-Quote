package com.example.dailyquote.presentation.favorites

import androidx.fragment.app.viewModels
import com.example.dailyquote.databinding.FragmentFavoritesBinding
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
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>(
    FragmentFavoritesBinding::inflate
) {

    override val viewModel: FavoritesViewModel by viewModels()

    private val adapter = QuoteAdapter(
        onShareClick = { viewModel.onShareClicked(it) },
        onCopyClick = { viewModel.onCopyClicked(it) },
        onSaveClick = { cardView ->
            val bitmap = getBitmapFromView(cardView)
            saveBitmap(requireContext(), bitmap, "quote_${System.currentTimeMillis()}")
        },
        onFavoriteClick = { viewModel.onFavoriteClicked(it) }
    )

    override fun setUpViews() {
        binding.rvQuotes.adapter = adapter
    }

    override fun observeData() {
        launchAndRepeatWithViewLifecycle {
            viewModel.favorites.collect { quotes ->
                adapter.submitList(quotes)
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

                    is QuoteEvent.SaveQuote -> {

                    }

                    is QuoteEvent.ShowMessage -> {
                        showToast(event.message)
                    }
                }
            }
        }
    }
}
