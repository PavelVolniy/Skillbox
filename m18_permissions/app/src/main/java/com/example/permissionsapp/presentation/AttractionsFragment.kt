package com.example.permissionsapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.permissionsapp.R
import com.example.permissionsapp.databinding.AttrationsFragmentBinding

class AttractionsFragment : Fragment() {
    private var _binding: AttrationsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AttrationsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter //todo

        binding.photoButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<PhotoFragment>(R.id.fragment_container)
                addToBackStack(AttractionsFragment::class.java.simpleName)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}