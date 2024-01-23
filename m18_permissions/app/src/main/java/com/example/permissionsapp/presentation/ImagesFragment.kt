package com.example.permissionsapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.permissionsapp.App
import com.example.permissionsapp.R
import com.example.permissionsapp.databinding.ImagesFragmentBinding
import com.example.permissionsapp.presentation.adapters.DiffUtilImageAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ImagesFragment : Fragment() {
    private var _binding: ImagesFragmentBinding? = null
    private val adapter = DiffUtilImageAdapter()
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: ImagesViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImagesFragmentBinding.inflate(layoutInflater)

        binding.recyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        viewModel.listImages.onEach { listImages ->
            adapter.submitList(listImages)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
        binding.photoButton.setOnClickListener {
            parentFragmentManager.commit {
                add<PhotoFragment>(R.id.fragment_container)
                addToBackStack(ImagesFragment::class.java.simpleName)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}