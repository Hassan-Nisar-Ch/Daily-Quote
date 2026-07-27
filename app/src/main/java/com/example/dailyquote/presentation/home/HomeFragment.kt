package com.example.dailyquote.presentation.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.dailyquote.R
import com.example.dailyquote.databinding.FragmentHomeBinding
import com.example.dailyquote.presentation.base.BaseFragment
import com.example.dailyquote.util.launchAndRepeatWithViewLifecycle
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
                    is HomeViewModel.HomeEvent.ShareQuote -> {
                        shareQuote(event.quote, event.author)
                    }

                    is HomeViewModel.HomeEvent.CopyQuote -> {
                        copyToClipboard(event.quote, event.author)
                    }
                }
            }
        }
    }

    private fun copyToClipboard(quote: String, author: String) {
        val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("quote", "\"$quote\" - $author")
        clipboard.setPrimaryClip(clip)
        Toast.makeText(requireContext(), getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT).show()
    }

    private fun shareQuote(quote: String, author: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "\"$quote\" - $author")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share)))
    }
}