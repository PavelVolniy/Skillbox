package com.example.permissionsapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.permissionsapp.R
import com.example.permissionsapp.databinding.ImagesFragmentBinding
import com.example.permissionsapp.di.DaggerAppComponent
import com.example.permissionsapp.presentation.adapters.DiffUtilImageAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ImagesFragment : Fragment() {
    private var _binding: ImagesFragmentBinding? = null
    private val adapter = DiffUtilImageAdapter()
    private val binding get() = _binding!!
    private val viewModel: ImagesViewModel by viewModels {
        DaggerAppComponent.create().viewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImagesFragmentBinding.inflate(layoutInflater)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.updateData()
        }
        viewModel.loading.onEach { binding.progressBar.isVisible = it }.launchIn(lifecycleScope)
        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listImages.onEach { listImages ->
            adapter.submitList(listImages)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.photoButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<PhotoFragment>(R.id.fragment_container)
                addToBackStack(ImagesFragment::class.java.simpleName)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}