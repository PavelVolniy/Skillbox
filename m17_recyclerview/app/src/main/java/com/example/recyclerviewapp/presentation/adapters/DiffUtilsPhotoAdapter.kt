package com.example.recyclerviewapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.databinding.PhotoItemBinding
import com.example.recyclerviewapp.entity.Photo
import javax.inject.Inject

class DiffUtilsAdapter @Inject constructor(
    private val onClick: (Photo) -> Unit
) : PagingDataAdapter<Photo, PhotoViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            camera.text = item?.camera?.name
            date.text = item?.rover?.landing_date
            rover.text = item?.rover?.name
            sol.text = item?.sol
            item?.let {
                Glide.with(imageView.context)
                    .load(item.img_src)
                    .centerCrop()
                    .into(imageView)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let { it1 -> onClick(it1) }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}

class PhotoViewHolder(val binding: PhotoItemBinding) :
    RecyclerView.ViewHolder(binding.root)