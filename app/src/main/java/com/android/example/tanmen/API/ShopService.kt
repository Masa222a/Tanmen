package com.android.example.tanmen.API

import android.location.Location
import android.util.Log
import com.android.example.tanmen.Model.Shop
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

    fun fetchUrl(ramenUrl: UrlCreate.Distance?, callback: (MutableList<Shop>) -> Unit) {
        val url = URL(ramenUrl?.let { UrlCreate(it, location).url })
        search(url) {
            when(it) {
                is HTTPResponse.JsonSuccess -> {
                    val dataList = it.json.getJSONObject("results").getJSONArray("shop")
                    val shopData: MutableList<Shop> = mutableListOf()
                    for (i in 0 until dataList.length()) {
                        val imageUrl = dataList.getJSONObject(i).getString("logo_image")
                        val shopName = dataList.getJSONObject(i).getString("name")
                        val shopAddress = dataList.getJSONObject(i).getString("address")
                        val shopHours = dataList.getJSONObject(i).getString("open")
                        val shopResult = Shop(imageUrl, shopName, shopAddress, shopHours)
                        shopData.add(shopResult)
                    }
                    callback(shopData)
                }
                is HTTPResponse.Failure -> {
                    callback(mutableListOf())
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