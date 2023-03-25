package jp.example.tanmen.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.Model.Entity.Shop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShuffleViewModel : ViewModel() {
    var data = MutableLiveData<Shop>()

    fun getData() {
        ShopService.instance.fetchUrl(ShopService.UrlCreate.Distance.fiveHundred) {
            if(it.isEmpty()) {
                data.postValue(null)
            } else {
                val index = (0 until it.size).random()
                viewModelScope.launch(Dispatchers.Main) {
                    data.postValue(Shop(it[index].image, it[index].name, it[index].address, it[index].hours))
                    Log.d("確認　shuffleViewModel","データを取得しました。")
                }
            }
        }
    }
}