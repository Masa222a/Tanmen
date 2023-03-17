package jp.example.tanmen.viewModel

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.Model.Entity.Shop

class ShuffleViewModel : ViewModel() {

    var shopPhoto = MutableLiveData<String>()
    var shopName = MutableLiveData<String>()
    var shopAddress = MutableLiveData<String>()
    private var data = mutableListOf<Shop>()

    init {
        shopPhoto.value = ""
        shopName.value = ""
        shopAddress.value = ""
    }

    fun getData(): MutableList<Shop> {
        ShopService.instance.fetchUrl(ShopService.UrlCreate.Distance.fiveHundred) {
            data = it
        }
        return data
    }

    fun changeContent(shopData: MutableList<Shop>) {
        val index = (0..shopData.size).random()
        val randomData = shopData[index]
        shopPhoto.value = randomData.image
        shopName.value = randomData.name
        shopAddress.value = randomData.address
    }

    fun setImageUrl(view: ImageView, imgPath: String) {
        Picasso.get().load(imgPath).resize(72, 72).into(view)
    }
}