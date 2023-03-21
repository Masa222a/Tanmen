package jp.example.tanmen.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.Model.Entity.Shop

class ShuffleViewModel : ViewModel() {
    var data: MutableLiveData<Shop>? = null

    fun getData() {
        ShopService.instance.fetchUrl(ShopService.UrlCreate.Distance.fiveHundred) {
            val index = (0..it.size).random()
            data = MutableLiveData(Shop(it[index].image, it[index].name, it[index].address, it[index].hours))
        }
    }
}