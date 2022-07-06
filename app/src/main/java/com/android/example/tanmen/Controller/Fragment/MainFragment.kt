package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.example.tanmen.API.ShopService
import com.android.example.tanmen.R
import com.android.example.tanmen.View.BottomNavigationPagerAdapter
import com.android.example.tanmen.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = BottomNavigationPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener {
            val currentItem = getCurrentItem(it.itemId)
            if (currentItem == 1) {
                binding.viewPager.setCurrentItem(currentItem, true)
                lifecycleScope.launch {
                    val data = ShopService().searchTask(ShopService.Distance.fiveHundred)
                    val index = Random.nextInt(data.size)
                    val randomData = data[index]
                    Bundle().putSerializable("randomData", randomData)
                }
                return@setOnItemSelectedListener true
            } else {
                binding.viewPager.setCurrentItem(currentItem, true)
                return@setOnItemSelectedListener true
            }
        }

        binding.fab.setOnClickListener {
            val dialog = SearchBottomSheetDialogFragment()
            dialog.show(childFragmentManager, dialog.tag)
        }

        return binding.root
    }

    private fun getCurrentItem(itemId: Int): Int {
        return when (itemId) {
            R.id.nav_Home -> 0
            R.id.nav_Shuffle -> 1
            else -> 0
        }
    }

//    private fun getRandomShopData() {
//        lifecycleScope.launch {
//            val data = ShopService().searchTask(ShopService.Distance.fiveHundred)
//            val index = Random.nextInt(data.size)
//            val randomData = data[index]
//            Log.d("randomIndex", "${index}")
//            Log.d("randomData", "${randomData}")
//            setFragmentResult(
//                REQ_KEY,
//                createArgments(randomData)
//            )
//        }
//    }
}