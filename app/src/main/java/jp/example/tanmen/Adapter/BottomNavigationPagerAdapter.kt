package jp.example.tanmen.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import jp.example.tanmen.view.Fragment.HomeFragment
import jp.example.tanmen.view.Fragment.MainFragment
import jp.example.tanmen.view.Fragment.ShuffleFragment

class BottomNavigationPagerAdapter(fm: MainFragment) : FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                ShuffleFragment()
            }
            else -> {
                HomeFragment()
            }
        }
    }
}