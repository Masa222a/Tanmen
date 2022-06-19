package com.android.example.tanmen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.example.tanmen.databinding.FragmentMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        val fab = activity?.findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener { view ->
//            Snackbar.make(view, "Fabを押しました！", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_Sheet))
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shopList = mutableListOf<Shop>(
            Shop(R.drawable.ramen_dining_24px, "ラーメン1"),
            Shop(R.drawable.ramen_dining_24px, "ラーメン2"),
            Shop(R.drawable.ramen_dining_24px, "ラーメン3")
        )
        val recyclerView = binding.shopList
        val layoutManager = LinearLayoutManager(recyclerView.context)
        val adapter = ShopListAdapter(shopList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}