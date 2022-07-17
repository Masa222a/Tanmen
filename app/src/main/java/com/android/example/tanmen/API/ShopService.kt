package com.android.example.tanmen.API

import android.location.Location
import android.util.Log
import com.android.example.tanmen.Model.Shop
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

class ShopService(val location: Location) {

    suspend fun searchTask(distance: UrlCreate.Distance?): MutableList<Shop> {
        val result = ramenBackgroundTask(distance)
        return ramenJsonTask(result)
    }

    private suspend fun ramenBackgroundTask(ramenUrl: UrlCreate.Distance?): String {
        val response = withContext(Dispatchers.IO) {
            var httpResult = ""

            try {
                val urlObj = URL(ramenUrl?.let { UrlCreate(it, location).url })
                Log.d("locationShopService", "${urlObj}")
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

    class UrlCreate(private val range: Distance, private val location: Location) {
        enum class Distance(private val range: Int) {
            fiveHundred(2),
            oneThousand(3),
            twoThousand(4);
        }
        //a3ec860c685e1821
        private val apiKey = "a3ec860c685e1821"
        //https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=a3ec860c685e1821&lat=34.67&lng=135.52&range=3
        private val mainUrl = "https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key="
        val url: String
            get() {
                return  "${mainUrl}${apiKey}&lat=${location.latitude}&lng=${location.longitude}&range=${range}&genre=G013&format=json"
            }
    }
}