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
    ): View {
        binding = FragmentSearchBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                if (ShopService.instance.location != null) {
                    val btnId = binding.toggleButton.checkedButtonId
                    if (btnId != -1) {
                        val distance = getCheckedButton(btnId)
                        ShopService.instance.fetchUrl(distance) {
                            Log.d("shopData", "$it")

                            setFragmentResult(
                                REQ_KEY,
                                createArgments(it)
                            )
                        }
                    }else {
                        Toast.makeText(activity, "距離を選択してください", Toast.LENGTH_SHORT).show()
                        Log.d("toggle選択なし", "選択されていません")
                    }
                } else {
                    Log.d("SearchBottomSheetDialogFragmentLocation", "locationがnullです。")
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun getCheckedButton(btnId: Int): ShopService.UrlCreate.Distance? {
        when (btnId) {
            R.id.button1 -> {
                //500m
                return ShopService.UrlCreate.Distance.fiveHundred
            }
            R.id.button2 -> {
                //1000m
                return ShopService.UrlCreate.Distance.oneThousand

            }
            R.id.button3 -> {
                //2000m
                return ShopService.UrlCreate.Distance.twoThousand
            }
        }
        return null
    }
}
