package com.android.example.tanmen.API

import android.util.Log
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.android.example.tanmen.Controller.Fragment.MainFragment
import com.android.example.tanmen.Model.Shop
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

class ShopService {
    suspend fun searchTask(ramenUrl: String): MutableList<Shop> {
        val result = ramenBackgroundTask(ramenUrl)
        return ramenJsonTask(result)
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
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("エラー⓵","IOException")
            } catch (e: JSONException) {
                e.printStackTrace()
                Log.e("エラー②", "JSONException")
            }
            return@withContext httpResult
        }
        return response
    }

    private fun ramenJsonTask(result: String): MutableList<Shop> {
        val jsonObj = JSONObject(result).getJSONObject("results").getJSONArray("shop")
        val shopData: MutableList<Shop> = mutableListOf()
        for (i in 0 until jsonObj.length()) {
            val imageUrl = jsonObj.getJSONObject(i).getString("logo_image")
            val shopImage = Picasso.get().load(imageUrl).resize(72, 72)
            val shopName = jsonObj.getJSONObject(i).getString("name")
            val shopAddress = jsonObj.getJSONObject(i).getString("address")
            val shopHours = jsonObj.getJSONObject(i).getString("open")
            val shopResult = Shop(shopImage, shopName, shopAddress, shopHours)
            shopData.add(shopResult)
        }
        return shopData
    }
}