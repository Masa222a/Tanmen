package com.android.example.tanmen

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private var range = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.searchButton.setOnClickListener {
            getCheckedButton().toString()
            Log.d("clickedSearch", "${getCheckedButton()}")
//            searchTask(url)
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

    private fun ramenJsonTask(result: String) {
        val jsonObj = JSONObject(result).getJSONObject("results").getJSONArray("shop")
        val shop = jsonObj.getJSONObject(0)
        val shopName = shop.getString("name")
        Log.d("店名", "$shopName")
    }

    private suspend fun ramenBackgroundTask(ramenUrl: String): String {
        val response = withContext(Dispatchers.IO) {
            var httpResult = ""

            try {
                val urlObj = URL(ramenUrl)
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                httpResult = br.readText()
            } catch (e:IOException) {
                e.printStackTrace()
            } catch (e:JSONException) {
                e.printStackTrace()
            }
            return@withContext httpResult
        }
        return response
    }

    private fun getCheckedButton() {
        when(binding.toggleButton.checkedButtonId) {
            R.id.button1 -> {
                //500m
                val ramenUrl = "$mainUrl$apiKey&lat=34.67&lng=135.52&range=2&format=json"
                Log.d("URL", "$ramenUrl")
            }
            R.id.button2 -> {
                //1000m
                val ramenUrl = "$mainUrl$apiKey&lat=34.67&lng=135.52&range=3&format=json"
                Log.d("URL", "$ramenUrl")

            }
            R.id.button3 -> {
                //2000m
                val ramenUrl = "$mainUrl$apiKey&lat=34.67&lng=135.52&range=4&format=json"
                Log.d("URL", "$ramenUrl")
            }
        }
    }
}
