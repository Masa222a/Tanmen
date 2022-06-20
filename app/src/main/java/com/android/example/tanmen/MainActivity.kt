package com.android.example.tanmen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
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
        binding.fab.doOnLayout {
            initSheetBehavior(binding)
        }

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Fabを押しました！", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    private fun initSheetBehavior(binding: ActivityMainBinding) {

    }

    private fun getCurrentItem(itemId: Int): Int {
        return when (itemId) {
            R.id.nav_Main -> 0
            R.id.nav_Shuffle -> 1
            else -> 0
        }
    }
}