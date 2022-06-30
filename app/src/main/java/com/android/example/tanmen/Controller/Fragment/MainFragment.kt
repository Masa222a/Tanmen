package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.R
import com.android.example.tanmen.View.ShopListAdapter
import com.android.example.tanmen.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    var adapter: ShopListAdapter? = null

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
        setFragmentResultListener(REQ_KEY) { _, bundle ->
            val shopLists = bundle.getSerializable(ARG_SHOP) as MutableList<Shop>

            val recyclerView = binding.shopList
            val layoutManager = LinearLayoutManager(recyclerView.context)
            adapter = ShopListAdapter(shopLists)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

        }

        adapter?.setOnShopCellClickListener(
            object : ShopListAdapter.OnShopCellClickListener {
                override fun onItemClick(shop: Shop) {
                    setFragmentResult("shopDetail", bundleOf(
                        "shopImage" to shop.image.resize(300, 300),
                        "shopName" to shop.name,
                        "shopAddress" to shop.address,
                        "shopHours" to shop.hours
                    ))

                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.viewPager, DetailFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        )
        return binding.root
    }

}