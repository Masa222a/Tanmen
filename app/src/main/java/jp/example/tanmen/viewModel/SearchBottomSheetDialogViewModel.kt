package jp.example.tanmen.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.Model.Entity.Shop

class SearchBottomSheetDialogViewModel : ViewModel() {
    var shopListLiveData: MutableLiveData<MutableList<Shop>> = MutableLiveData()

    fun getShopList(distance: ShopService.UrlCreate.Distance) {
        ShopService.instance.fetchUrl(distance) {
            shopListLiveData.postValue(it)
        }
    }
}