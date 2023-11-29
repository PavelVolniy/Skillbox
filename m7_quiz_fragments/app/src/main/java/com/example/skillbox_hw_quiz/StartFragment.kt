package com.example.skillbox_hw_quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.skillbox_hw_quiz.databinding.StartFragmentBinding
import com.example.skillbox_hw_quiz.ui.main.MainFragment

class StartFragment: Fragment() {
    private var _binding: StartFragmentBinding?  = null
    private val  binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= StartFragmentBinding.inflate(inflater)

        binding.nextButton.setOnClickListener {
            parentFragmentManager.commit {
                replace<MainFragment>(R.id.fragment_container)
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}