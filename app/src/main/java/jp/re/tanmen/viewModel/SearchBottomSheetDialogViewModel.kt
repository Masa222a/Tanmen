package jp.re.tanmen.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.re.tanmen.model.API.ShopService
import jp.re.tanmen.model.Entity.Shop

class SearchBottomSheetDialogViewModel : ViewModel() {
    var shopListLiveData: MutableLiveData<MutableList<Shop>> = MutableLiveData()

    fun getShopList(distance: ShopService.UrlCreate.Distance) {
        ShopService.instance.fetchUrl(distance) {
            if (it.isEmpty()) {
                shopListLiveData.postValue(null)
            } else {
                shopListLiveData.postValue(it)
            }
        }
    }
}