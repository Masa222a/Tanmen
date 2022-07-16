package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.android.example.tanmen.API.ShopService
import com.android.example.tanmen.Controller.Activity.MainActivity
import com.android.example.tanmen.Controller.Fragment.HomeFragment.Companion.REQ_KEY
import com.android.example.tanmen.Controller.Fragment.HomeFragment.Companion.createArgments
import com.android.example.tanmen.R
import com.android.example.tanmen.databinding.FragmentSearchBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class SearchBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBottomSheetDialogBinding.inflate(inflater, container, false)

        val activity = activity as? MainActivity
        val location = activity?.currentLocation
        Log.d("BottomSheetlocation", "${location}")

        binding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                val btnId = binding.toggleButton.checkedButtonId
                val distance = getCheckedButton(btnId)
                val shopData = ShopService(location!!).searchTask(distance)

                setFragmentResult(
                    REQ_KEY,
                    createArgments(shopData)
                )
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun getCheckedButton(btnId: Int): ShopService.Distance? {
        when (btnId) {
            R.id.button1 -> {
                //500m
                return ShopService.Distance.fiveHundred
            }
            R.id.button2 -> {
                //1000m
                return ShopService.Distance.oneThousand

            }
            R.id.button3 -> {
                //2000m
                return ShopService.Distance.twoThousand
            }
            else -> {
                Toast.makeText(activity, "選択してください", Toast.LENGTH_SHORT)
                Log.d("toggle選択なし", "選択されていません")
            }
        }
        return null
    }
}
