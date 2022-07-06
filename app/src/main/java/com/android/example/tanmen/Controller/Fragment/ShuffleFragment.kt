package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.databinding.FragmentShuffleBinding

class ShuffleFragment : Fragment() {
    private lateinit var binding: FragmentShuffleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShuffleBinding.inflate(inflater, container, false)
        val args = requireArguments().getSerializable("randomData") as Shop
        changeContent(args as Shop)
        Log.d("argsShop", "$args")

        return binding.root
    }

    private fun changeContent(shopData: Shop) {
        shopData.image.into(binding.shopPhoto)
        binding.shopName.text = shopData.name
        binding.shopAddress.text = shopData.address
    }
}