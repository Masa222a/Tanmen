package com.android.example.tanmen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.example.tanmen.databinding.FragmentSearchBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchBottomSheetDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

}