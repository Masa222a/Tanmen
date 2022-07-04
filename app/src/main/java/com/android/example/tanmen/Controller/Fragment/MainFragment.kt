package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.example.tanmen.R
import com.android.example.tanmen.View.BottomNavigationPagerAdapter
import com.android.example.tanmen.databinding.ActivityMainBinding
import com.android.example.tanmen.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = BottomNavigationPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false

        val bottomNavigation = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation?.setOnItemSelectedListener {
            val currentItem = getCurrentItem(it.itemId)
            binding.viewPager.setCurrentItem(currentItem, true)
            return@setOnItemSelectedListener true
        }

        return binding.root
    }

    private fun getCurrentItem(itemId: Int): Int {
        return when (itemId) {
            R.id.homeFragment -> 0
            R.id.shuffleFragment -> 1
            else -> 0
        }
    }

}