package com.example.gooddoggos.ui.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gooddoggos.databinding.ItemDogBinding
import com.example.gooddoggos.models.GoodDoggoItem

class DogAdapter: PagingDataAdapter<GoodDoggoItem,DogAdapter.DogViewHolder>(DOG_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }


    inner class DogViewHolder(private val binding: ItemDogBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GoodDoggoItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.url)
                    .into(ivDog)

                var breedName: String = ""

//                for (breed in item.breeds) {
//                    breedName = breed.name
//                }

                tvDogName.text = "breedName"
            }
        }
    }

    companion object {
        private val DOG_COMPARATOR = object : DiffUtil.ItemCallback<GoodDoggoItem>() {
            override fun areItemsTheSame(oldItem: GoodDoggoItem, newItem: GoodDoggoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GoodDoggoItem,
                newItem: GoodDoggoItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}