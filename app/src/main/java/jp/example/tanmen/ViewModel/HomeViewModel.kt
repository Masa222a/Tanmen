package jp.example.tanmen.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.example.tanmen.Model.Entity.Shop
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    var shopListLiveData: MutableLiveData<List<Shop>> = MutableLiveData()

    private fun loadShopList() {
        viewModelScope.launch {

        }
    }
}