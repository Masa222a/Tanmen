package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.R
import com.android.example.tanmen.View.BottomNavigationPagerAdapter
import com.android.example.tanmen.View.ShopListAdapter
import com.android.example.tanmen.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()

        private const val REQUEST_KEY = "request_key"
    }

    interface ShopDataList {
        var shop: MutableList<Shop>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = BottomNavigationPagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false

        binding.bottomNavigation.setOnItemSelectedListener {
            val currentItem = getCurrentItem(it.itemId)
            binding.viewPager.setCurrentItem(currentItem, true)
            return@setOnItemSelectedListener true
        }

        binding.fab.setOnClickListener {
            openBottomSheet()
        }

        childFragmentManager.setFragmentResultListener(
            REQUEST_KEY,
            viewLifecycleOwner
        ) { _, result: Bundle ->
            val shop = result.getSerializable(SearchBottomSheetDialogFragment.KEY_CLICK) as MutableList<Shop>

            abstract class Shop(shop: MutableList<Shop>) : ShopDataList
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