// app/src/main/java/com/example/groceriestracker/ui/home/ItemAdapter.kt
package com.example.groceriestracker.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.databinding.ItemListBinding

class ItemAdapter : ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        // Set click listener for the item view
        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, item)
        }
    }

    class ItemViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.itemName.text = item.name
            binding.itemAmount.text = item.amount.toString()
            binding.itemUnit.text = item.unit
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    // Set the click listener for the adapter
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }


    // Interface for the click listener
    interface OnClickListener {
        fun onClick(position: Int, model: Item)
    }
}