package com.android.example.tanmen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.android.example.tanmen.databinding.FragmentSearchBottomSheetDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
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
            val btnId = binding.toggleButton.checkedButtonId
            val url = getCheckedButton(btnId)
            Log.d("clickedSearchURL", "${url}")
            searchTask(url)
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    private fun searchTask(ramenUrl: String) {
        lifecycleScope.launch {
            val result = ramenBackgroundTask(ramenUrl)
            ramenJsonTask(result)
        }
    }

    private suspend fun ramenBackgroundTask(ramenUrl: String): String {
        val response = withContext(Dispatchers.IO) {
            var httpResult = ""

            try {
                val urlObj = URL(ramenUrl)
                Log.d("ramenUrl", "$ramenUrl")
                Log.d("urlObj", "$urlObj")
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                httpResult = br.readText()
            } catch (e:IOException) {
                e.printStackTrace()
                Log.e("エラー⓵","IOException")
            } catch (e:JSONException) {
                e.printStackTrace()
                Log.e("エラー②", "JSONException")
            }
            return@withContext httpResult
        }
        return response
    }

    private fun ramenJsonTask(result: String) {
        val jsonObj = JSONObject(result).getJSONObject("results").getJSONArray("shop")
        val shop = jsonObj.getJSONObject(0)
        val shopName = shop.getString("name")
        val shopAddress = shop.getString("address")
        val shopImage = shop.getString("logo_image")
        setFragmentResult("Key1", bundleOf(
            "img" to shopImage,
            "name" to shopName,
        ))
        Log.d("店名", "$shopName")
        Log.d("住所", "$shopAddress")
        Log.d("画像", "$shopImage")
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
