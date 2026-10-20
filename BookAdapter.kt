package com.cis2818.project4


import android.content.Context
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope


import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cis2818.project4.databinding.ItemBookBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookAdapter(
    private val books: List<Book>,
    private val onHeartClick: (Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BookViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]

        holder.binding.titleTextView.text = book.title
        holder.binding.authorTextView.text = book.author

        // Load cover image with Glide
        Glide.with(holder.itemView.context)
            .load(book.coverUrl)
            .placeholder(R.drawable.ic_loading) // while loading
            .error(R.drawable.ic_no_image)      // if failed
            .into(holder.binding.coverImageView)

        // Set heart icon depending on isSaved
        if (book.isSaved) {
            holder.binding.heartButton.setImageResource(R.drawable.ic_heart_filled)
        } else {
            holder.binding.heartButton.setImageResource(R.drawable.ic_heart_outline)
        }

        // Heart button click
        holder.binding.heartButton.setOnClickListener {
            book.isSaved = !book.isSaved
            holder.binding.heartButton.setImageResource(
                if (book.isSaved) R.drawable.ic_heart_filled
                else R.drawable.ic_heart_outline
            )
            onHeartClick(book) // send the book back to ViewModel
        }
    }

    override fun getItemCount() = books.size
}
