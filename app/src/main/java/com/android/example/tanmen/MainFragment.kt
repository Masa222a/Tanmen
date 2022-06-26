package com.android.example.tanmen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tanmen.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
//        setFragmentResultListener("Key1") { requestKey, bundle ->
//            val img = bundle.getString("img")
//            val name = bundle.getString("name")
//            Log.d("img", "${img}")
//            Log.d("name", "${name}")
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (i in 0..4) {
            setFragmentResultListener("Key${i}") { requestKey, bundle ->
                val img = bundle.getString("img")
                val name = bundle.getString("name")
                val address = bundle.getString("address")
                val hours = bundle.getString("open")
                Log.d("img", "${img}")
                Log.d("name", "${name}")
                Log.d("address", "${address}")
                Log.d("hours", "${hours}")

            val shopList = mutableListOf<Shop>(
                Shop(img, name.toString(), address, hours),
                Shop(img, name.toString(),address, hours),
                Shop(img, name.toString(), address, hours)
            )
            val recyclerView = binding.shopList
            val layoutManager = LinearLayoutManager(recyclerView.context)
            val adapter = ShopListAdapter(shopList)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

        }
        }

    }
}