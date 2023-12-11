package com.example.mvvmapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvmapp.databinding.MainFragmentBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchField.addTextChangedListener {
            viewModel.checkSearchRequest(it.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        binding.searchButton.isChecked = false
                        binding.progressBar.isVisible = true
                    }

                    State.Error -> {
                        binding.searchButton.isChecked = false
                        binding.progressBar.isVisible = false
                    }

                    is State.Success -> {
                        binding.searchButton.isChecked = true
                        binding.progressBar.isVisible = false
                    }

                }

            }
        }
    }

}