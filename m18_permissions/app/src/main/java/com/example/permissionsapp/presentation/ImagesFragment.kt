package com.example.permissionsapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.permissionsapp.R
import com.example.permissionsapp.databinding.ImagesFragmentBinding

class ImagesFragment : Fragment() {
    private var _binding: ImagesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ImagesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter //todo

        binding.photoButton.setOnClickListener {
            Toast.makeText(this.context, "Click", Toast.LENGTH_SHORT).show() //todo remove
//            parentFragmentManager.commit {
//                replace<PhotoFragment>(R.id.fragment_container)
//                addToBackStack(ImagesFragment::class.java.simpleName)
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}