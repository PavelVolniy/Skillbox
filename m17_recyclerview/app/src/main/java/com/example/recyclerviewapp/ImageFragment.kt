package com.example.recyclerviewapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.recyclerviewapp.databinding.ImageFragmentBinding

private const val IMAGE_PATH = "image_path"

class ImageFragment : Fragment() {
    private var imagePath: String? = null
    private var _binding: ImageFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imagePath = it.getString(IMAGE_PATH)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImageFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(imagePath)
            .into(binding.image)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}