package com.example.architectureapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.architectureapp.databinding.MainFragmentBinding
import com.example.architectureapp.di.DaggerAppComponent
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refreshButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.updateTask()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is State.Loading -> binding.progressBar.isVisible = true
                    is State.Success-> {
                        binding.progressBar.isVisible = false
                        binding.taskText.text = state.result
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}