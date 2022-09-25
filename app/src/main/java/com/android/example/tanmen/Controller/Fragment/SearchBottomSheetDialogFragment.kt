package com.android.example.tanmen.Controller.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.android.example.tanmen.API.ShopService
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.R
import com.android.example.tanmen.databinding.FragmentSearchBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class SearchBottomSheetDialogFragment : BottomSheetDialogFragment() {
    interface SearchBottomSheetDialogFragmentCallBackListener {
        fun callback(shops: List<Shop>)
    }

    var listener: SearchBottomSheetDialogFragmentCallBackListener? = null

    var mainFragment: MainFragment? = null

    private lateinit var binding: FragmentSearchBottomSheetDialogBinding

    private val _requestKey: String
        get() = requireArguments().getString(KEY_REQUEST, "")

    companion object {
        fun newInstance(
            requestKey: String
        ) = SearchBottomSheetDialogFragment().apply {
            arguments = bundleOf(KEY_REQUEST to requestKey)
        }

        const val KEY_CLICK = "shopData"
        private const val KEY_REQUEST = "key_request"
    }

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

                            listener?.callback(it)
//                            val bundle = bundleOf(KEY_CLICK to it)
//                            setFragmentResult(_requestKey, bundle)

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
