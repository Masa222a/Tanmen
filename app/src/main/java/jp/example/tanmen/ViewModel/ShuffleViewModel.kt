package jp.example.tanmen.ViewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.Model.Entity.Shop
import jp.example.tanmen.databinding.FragmentShuffleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShuffleViewModel : ViewModel() {
    private lateinit var binding: FragmentShuffleBinding

    val shopPhoto = MutableLiveData<String>()
    val shopName = MutableLiveData<String>()
    val shopAddress = MutableLiveData<String>()

    init {
        shopPhoto.value = ""
        shopName.value = ""
        shopAddress.value = ""
    }

    fun reloadData() {
        var data: MutableList<Shop>
        ShopService.instance.fetchUrl(ShopService.UrlCreate.Distance.fiveHundred) {
            data = it
            if (data.isNullOrEmpty()) {
                GlobalScope.launch(Dispatchers.Main) {
                    binding.nameLabel.visibility = View.GONE
                    binding.addressLabel.visibility = View.GONE
                    
                }
            }
        }
    }
}