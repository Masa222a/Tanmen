package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.example.tanmen.API.ShopService
import com.android.example.tanmen.Controller.Activity.MainActivity
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.databinding.FragmentShuffleBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

class ShuffleFragment : Fragment() {
    private lateinit var binding: FragmentShuffleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShuffleBinding.inflate(inflater, container, false)
        val activity = activity as? MainActivity
        val location = activity?.currentLocation

        lifecycleScope.launch() {
            val data = ShopService(location!!).searchTask(ShopService.Distance.fiveHundred)
            val index = Random.nextInt(data.size)
            val randomData = data[index]
            changeContent(randomData)
        }

        return binding.root
    }

    private fun changeContent(shopData: Shop) {
        shopData.image.into(binding.shopPhoto)
        binding.shopName.text = shopData.name
        binding.shopAddress.text = shopData.address
    }
}