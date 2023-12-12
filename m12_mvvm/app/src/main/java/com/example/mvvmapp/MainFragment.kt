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

        binding.searchButton.setOnClickListener {
            viewModel.getResultRequest()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        binding.searchButton.isEnabled = false
                        binding.progressBar.isVisible = true
                        binding.inputLayout.error = null
                    }

                    State.Error -> {
                        binding.inputLayout.error = "a few characters, min 3"
                        binding.searchButton.isEnabled = false
                        binding.progressBar.isVisible = false
                    }

                    is State.Success -> {
                        binding.inputLayout.error = null
                        binding.searchButton.isEnabled = true
                        binding.progressBar.isVisible = false
                    }

                }

            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.result.collect { result ->
                binding.resultField.text = result
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}