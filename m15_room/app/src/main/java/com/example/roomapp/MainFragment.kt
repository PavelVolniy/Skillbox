package com.example.roomapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomapp.databinding.MainFragmentBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> { MainViewModelFactory(this.requireContext().applicationContext) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {

                    is State.Error -> {
                        binding.progressBar.isVisible = false
                        binding.editText.error = state.errorMessage
                        binding.addButton.isEnabled = false
                    }

                    is State.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.addButton.isEnabled = false
                    }

                    is State.Success -> {
                        binding.progressBar.isVisible = false
                        binding.addButton.isEnabled = true
                    }

                    is State.Wait -> {
                        binding.progressBar.isVisible = false
                        binding.addButton.isEnabled = true
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.listWords.collect { listWords ->
                val result: StringBuilder = StringBuilder()
                listWords.map { result.append("${it.name} - count ${it.count}\n") }
                binding.resultList.text = result.toString()
            }
        }

        binding.editText.addTextChangedListener {
            viewModel.checkEnteredWord(binding.editText.text.toString())
        }

        binding.addButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addWord()
                binding.editText.setText("")
            }
        }

        binding.clearButton.setOnClickListener {
            viewModel.clearList()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}