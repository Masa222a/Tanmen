package jp.re.tanmen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import jp.re.tanmen.view.Fragment.HomeFragment
import jp.re.tanmen.view.Fragment.MainFragment
import jp.re.tanmen.view.Fragment.ShuffleFragment

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