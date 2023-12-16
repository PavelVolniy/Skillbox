package com.example.retrofitapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.retrofitapp.databinding.MainFragmentBinding

private const val KEY_IMAGE_PATH = "keyImage"

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private var urlImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        urlImage = savedInstanceState?.getString(KEY_IMAGE_PATH)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)

        if (urlImage != null) {
            Toast.makeText(context, urlImage, Toast.LENGTH_SHORT).show()
            Glide.with(binding.imageUser.context)
                .load(urlImage)
                .into(binding.imageUser)
        }
        if (viewModel.state.value == State.Created) {
            viewModel.getUserData(binding.imageUser)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.updateButton.setOnClickListener {
            viewModel.getUserData(binding.imageUser)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_IMAGE_PATH, viewModel.viewCard.value?.pictureUri)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}