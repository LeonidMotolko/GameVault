package com.example.gamevault.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gamevault.databinding.ItemGameBinding
import com.example.gamevault.model.LibraryItem

class GameAdapter(
    private val onItemClick: (LibraryItem) -> Unit
) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    private var items: List<LibraryItem> = emptyList()

    fun submitList(newItems: List<LibraryItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LibraryItem) {
            binding.titleText.text = item.title
            binding.genreText.text = item.genre
            binding.typeTag.text = item.typeName
            binding.ratingText.text = "${item.rating}/10"
            binding.root.setOnClickListener { onItemClick(item) }
        }
    }
}
