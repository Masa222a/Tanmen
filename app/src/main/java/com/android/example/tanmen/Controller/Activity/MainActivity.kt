package com.android.example.tanmen.Controller.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.example.tanmen.Controller.Fragment.SearchBottomSheetDialogFragment
import com.android.example.tanmen.View.BottomNavigationPagerAdapter
import com.android.example.tanmen.R
import com.android.example.tanmen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = BottomNavigationPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener {
            val currentItem = getCurrentItem(it.itemId)
            binding.viewPager.setCurrentItem(currentItem, true)
            return@setOnItemSelectedListener true
        }

        binding.fab.setOnClickListener {
            val dialog = SearchBottomSheetDialogFragment()
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    private fun getCurrentItem(itemId: Int): Int {
        return when (itemId) {
            R.id.nav_Main -> 0
            R.id.nav_Shuffle -> 1
            else -> 0
        }
    }
}