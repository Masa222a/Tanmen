package com.android.example.tanmen.API

import android.location.Location
import android.util.Log
import com.android.example.tanmen.Model.Shop
import com.squareup.picasso.Picasso
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.net.URL
import java.util.concurrent.TimeUnit

class ShopService private constructor(){
    companion object {
        var instance: ShopService = ShopService()
    }

    var location: Location? = null

//    suspend fun searchTask(distance: UrlCreate.Distance?): MutableList<Shop> {
//        val result = ramenBackgroundTask(distance)
//        return ramenJsonTask(result)
//    }
//
//    private suspend fun ramenBackgroundTask(ramenUrl: UrlCreate.Distance?): String {
//        val response = withContext(Dispatchers.IO) {
//            var httpResult = ""
//
//            try {
//                val urlObj = URL(ramenUrl?.let { UrlCreate(it, location).url })
//                getRequestUrl(urlObj)
//                val br = BufferedReader(InputStreamReader(getRequestUrl(urlObj)))
//                httpResult = br.readText()
//            } catch (e: IOException) {
//                e.printStackTrace()
//                Log.e("エラー⓵","IOException")
//            } catch (e: JSONException) {
//                e.printStackTrace()
//                Log.e("エラー②", "JSONException")
//            }
//            return@withContext httpResult
//        }
//        return response
//    }
    fun fetchUrl(ramenUrl: UrlCreate.Distance?, request: HTTPResponse) {
        val url = URL(ramenUrl?.let { UrlCreate(it, location).url })
        search(url) {
            when(request) {
                is HTTPResponse.JsonSuccess -> {
                    val dataList = request.json.getJSONObject("results").getJSONArray("shop")
                    val shopData: MutableList<Shop> = mutableListOf()
                    for (i in 0 until dataList.length()) {
                        val imageUrl = dataList.getJSONObject(i).getString("logo_image")
                        val shopImage = Picasso.get().load(imageUrl).resize(72, 72)
                        val shopName = dataList.getJSONObject(i).getString("name")
                        val shopAddress = dataList.getJSONObject(i).getString("address")
                        val shopHours = dataList.getJSONObject(i).getString("open")
                        val shopResult = Shop(shopImage, shopName, shopAddress, shopHours)
                        shopData.add(shopResult)
                    }
                }
                is HTTPResponse.Failure -> {

                }
            }
        }
    }

    sealed class HTTPResponse {
        data class JsonSuccess(val json: JSONObject) : HTTPResponse()
        data class Failure(val errorMessage: String) : HTTPResponse()
    }

    fun search(url: URL, handler: (HTTPResponse) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler(HTTPResponse.Failure(e.localizedMessage))
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: String? = response.body?.string()
                try {
                    val json = JSONObject(responseBody)
                    handler(HTTPResponse.JsonSuccess(json))
                } catch (e: Exception) {
                    handler(HTTPResponse.Failure(e.localizedMessage))
                }
            }
        })
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

    class UrlCreate(private val range: Distance, private val location: Location?) {
        enum class Distance {
            fiveHundred,
            oneThousand,
            twoThousand;
        }
        fun parseDistance(distance: Distance?): Int? {
            return when (distance) {
                Distance.fiveHundred -> 2
                Distance.oneThousand -> 3
                Distance.twoThousand -> 4
                else -> null
            }
        }
        //a3ec860c685e1821
        private val apiKey = "a3ec860c685e1821"
        //https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=a3ec860c685e1821&lat=34.67&lng=135.52&range=3
        private val mainUrl = "https://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key="
        val url: String
            get() {
                Log.d("UrlCreate", "${range}")
                return if (location != null) {
                    "${mainUrl}${apiKey}&lat=${location.latitude}&lng=${location.longitude}&range=${parseDistance(range)}&genre=G013&format=json"
                } else {
                    ""
                }
            }
    }
}