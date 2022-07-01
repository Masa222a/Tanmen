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
import com.android.example.tanmen.Controller.Fragment.MainFragment.Companion.REQ_KEY
import com.android.example.tanmen.Controller.Fragment.MainFragment.Companion.createArgments
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.R
import com.android.example.tanmen.databinding.FragmentSearchBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class SearchBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchBottomSheetDialogBinding
    //a3ec860c685e1821
    private val apiKey = "a3ec860c685e1821"
    //https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=a3ec860c685e1821&lat=34.67&lng=135.52&range=3
    private val mainUrl = "https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key="

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                val btnId = binding.toggleButton.checkedButtonId
                val url = getCheckedButton(btnId)
                Log.d("clickedSearchURL", "${url}")
                val shopData = ShopService().searchTask(url)

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



    private fun getCheckedButton(btnId: Int): String {
        val ramenUrl = when (btnId) {
            R.id.button1 -> {
                //500m
                return "$mainUrl$apiKey&lat=34.67&lng=135.52&range=2&format=json"
            }
            R.id.button2 -> {
                //1000m
                return "$mainUrl$apiKey&lat=34.67&lng=135.52&range=3&format=json"

            }
            R.id.button3 -> {
                //2000m
                return "$mainUrl$apiKey&lat=34.67&lng=135.52&range=4&format=json"
            }
            else -> {
                Toast.makeText(activity, "選択してください", Toast.LENGTH_SHORT)
                Log.d("toggle選択なし", "選択されていません")
            }
        }

        return ramenUrl.toString()
        Log.d("ramenUrl", "$ramenUrl")
    }
}
