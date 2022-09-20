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
        const val REQ_KEY: String = "shop"
        private const val ARG_SHOP: String = "shop"

        fun createArgments(shop: MutableList<Shop>): Bundle {
            return bundleOf(ARG_SHOP to shop)
        }
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

        setFragmentResultListener(REQ_KEY) { _, bundle ->
            val shopList = bundle.getSerializable(ARG_SHOP)
            val fragment = childFragmentManager.fragments as HomeFragment
            bundle.putSerializable("shopList", shopList)
            fragment.arguments = bundle
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
        val dialog = SearchBottomSheetDialogFragment()
        dialog.show(childFragmentManager, dialog.tag)
    }
}