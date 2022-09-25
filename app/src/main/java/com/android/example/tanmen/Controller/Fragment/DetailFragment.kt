package com.android.example.tanmen.Controller.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.android.example.tanmen.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val args = arguments?.getSerializable("shopDetail")
        Log.d("detail", "$args")

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@DetailFragment)
                    .commit()
            }
        })

        return binding.root
    }

}