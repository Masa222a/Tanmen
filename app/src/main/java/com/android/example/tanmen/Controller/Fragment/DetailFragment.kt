package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.setFragmentResultListener
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val args = arguments?.getSerializable("shopDetail") as Shop
        args.image.into(binding.shopPhoto)
        binding.shopName.text = args.name
        binding.shopAddress.text = args.address
        binding.shopBusinessHours.text = args.hours

        return binding.root
    }

}