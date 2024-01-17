package com.example.permissionsapp.presentation.adapters

import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.permissionsapp.databinding.ImageItemBinding
import com.example.permissionsapp.domain.entity.ImageItemDTO

class DiffUtilImageAdapter : ListAdapter<ImageItemDTO, ImageHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context))
        return ImageHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            date.text = item.date
            val path = "${MediaStore.Images.Media.EXTERNAL_CONTENT_URI}${ item.imagePath }"
            Glide.with(image.context)
                .load(path)
                .into(image)
        }
    }

}


class DiffUtilCallBack : DiffUtil.ItemCallback<ImageItemDTO>() {
    override fun areItemsTheSame(oldItem: ImageItemDTO, newItem: ImageItemDTO): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImageItemDTO, newItem: ImageItemDTO): Boolean {
        return oldItem == newItem
    }
}

class ImageHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)
