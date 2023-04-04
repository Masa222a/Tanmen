package jp.example.tanmen.view.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import jp.example.tanmen.Model.Entity.Shop
import jp.example.tanmen.R
import jp.example.tanmen.Adapter.BottomNavigationPagerAdapter
import jp.example.tanmen.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    companion object {
        private const val REQUEST_KEY = "request_key"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.let {
            it.viewPager.adapter = BottomNavigationPagerAdapter(this)
            it.viewPager.isUserInputEnabled = false

            it.bottomNavigation.setOnItemSelectedListener {
                val currentItem = getCurrentItem(it.itemId)
                binding.viewPager.setCurrentItem(currentItem, true)
                return@setOnItemSelectedListener true
            }

            it.fab.setOnClickListener {
                openBottomSheet()
            }
        }

        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner
        ) { _, result: Bundle ->
            val shop = result.getSerializable(SearchBottomSheetDialogFragment.KEY_CLICK) as MutableList<Shop>
            val fragment = childFragmentManager.fragments.first { it is HomeFragment } as HomeFragment
            fragment.changeShopList(shop)
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

    fun openBottomSheet() {
        val dialog = SearchBottomSheetDialogFragment.newInstance(
            requestKey = REQUEST_KEY
        )
        dialog.show(childFragmentManager, dialog.tag)
    }
}