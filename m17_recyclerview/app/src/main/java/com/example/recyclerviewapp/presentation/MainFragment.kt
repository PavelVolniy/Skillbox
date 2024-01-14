package com.example.recyclerviewapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewapp.ImageFragment
import com.example.recyclerviewapp.R
import com.example.recyclerviewapp.databinding.MainFragmentBinding
import com.example.recyclerviewapp.di.DaggerAppComponent
import com.example.recyclerviewapp.entity.Photo
import com.example.recyclerviewapp.presentation.adapters.DiffUtilsAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val IMAGE_PATH = "image_path"

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }
    private val diffAdapter = DiffUtilsAdapter { photo -> onClickItem(photo) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(layoutInflater)
        binding.recyclerView.adapter = diffAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false
        )

        viewModel.listPhotosPaging.onEach { photos ->
            diffAdapter.submitData(photos)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun onClickItem(item: Photo) {
        val path = Bundle().apply { putString(IMAGE_PATH, item.img_src) }

        parentFragmentManager.commit {
            replace<ImageFragment>(containerViewId = R.id.fragment_container, args = path)
            addToBackStack(MainFragment::class.java.simpleName)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}