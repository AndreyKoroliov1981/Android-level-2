package com.example.recyclerview.diffutil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import com.example.recyclerview.databinding.FragmentDiffUtilBinding

class DiffUtilFragment : Fragment() {
    private lateinit var binding: FragmentDiffUtilBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDiffUtilBinding.inflate(inflater)
        return binding.root
    }

}

