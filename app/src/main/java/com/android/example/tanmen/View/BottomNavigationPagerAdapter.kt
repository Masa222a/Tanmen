package com.android.example.tanmen.View

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.example.tanmen.Controller.Fragment.HomeFragment
import com.android.example.tanmen.Controller.Fragment.MainFragment
import com.android.example.tanmen.Controller.Fragment.ShuffleFragment

class BottomNavigationPagerAdapter(fm: MainFragment) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HomeFragment()
            1 -> ShuffleFragment()
            else -> HomeFragment()
        }
    }
}