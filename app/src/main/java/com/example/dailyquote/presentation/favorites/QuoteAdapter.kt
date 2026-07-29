package com.example.dailyquote.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyquote.R
import com.example.dailyquote.databinding.ItemQuoteBinding
import com.example.dailyquote.domain.model.Quote
import com.google.android.material.card.MaterialCardView

class QuoteAdapter(
    private val onShareClick: (Quote) -> Unit,
    private val onCopyClick: (Quote) -> Unit,
    private val onSaveClick: (MaterialCardView) -> Unit,
    private val onFavoriteClick: (Quote) -> Unit
) : ListAdapter<Quote, QuoteAdapter.ViewHolder>(QuoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemQuoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = getItem(position)

        holder.binding.apply {
            tvQuoteText.text = quote.quote
            tvQuoteAuthor.text = quote.author
            btnFavorite.icon = ContextCompat.getDrawable(root.context, R.drawable.ic_fav)

            btnShare.setOnClickListener {
                onShareClick(quote)
            }

            btnCopy.setOnClickListener {
                onCopyClick(quote)
            }

            btnSave.setOnClickListener {
                onSaveClick(cardQuote)
            }

            btnFavorite.setOnClickListener {
                onFavoriteClick(quote)
            }
        }
    }


    class ViewHolder(val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root)

    class QuoteDiffCallback : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.quote == newItem.quote
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }

}