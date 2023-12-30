package com.example.recyclerviewapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.MainFragmentBinding
import com.example.recyclerviewapp.di.DaggerAppComponent
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateData()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listPhotos.onEach {photo->
            binding.textView.text = photo.toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}