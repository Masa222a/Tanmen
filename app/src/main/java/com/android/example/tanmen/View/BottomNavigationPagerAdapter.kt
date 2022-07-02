package com.android.example.tanmen.View

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.example.tanmen.Controller.Fragment.MainFragment
import com.android.example.tanmen.Controller.Fragment.ShuffleFragment

class BottomNavigationPagerAdapter(fm : FragmentActivity) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MainFragment()
            else -> ShuffleFragment()
        }
    }
}