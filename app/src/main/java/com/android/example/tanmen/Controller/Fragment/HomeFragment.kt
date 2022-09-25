package com.android.example.tanmen.Controller.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.R
import com.android.example.tanmen.View.ShopListAdapter
import com.android.example.tanmen.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var adapter: ShopListAdapter? = null
    private var shopList = mutableListOf<Shop>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val recyclerView = binding.shopList
        val layoutManager = LinearLayoutManager(activity)
        adapter = ShopListAdapter(shopList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter?.setOnShopCellClickListener(
            object : ShopListAdapter.OnShopCellClickListener {
                override fun onItemClick(shop: Shop) {
                    val fragment = DetailFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("shopDetail", shop)
                    fragment.arguments = bundle
                    Log.d("bundle", "$bundle")
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, DetailFragment())
                        .addToBackStack(null)
                        .commit()
                }
            }
        )

        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeShopList(shopLists: MutableList<Shop>) {
//        adapter?.shopList = shopLists
//        adapter?.notifyDataSetChanged()
        GlobalScope.launch(Dispatchers.Main) {
            adapter?.shopList = shopLists
            adapter?.notifyDataSetChanged()
        }
    }

    private fun emptyTaskListDialog() {
        AlertDialog.Builder(requireActivity())
            .setMessage("該当する店舗が見つかりませんでした。\n" +
                    "検索条件を変更してください。")
            .setPositiveButton("はい") { _, _ -> }
            .show()
    }
}
