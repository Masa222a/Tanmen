package com.android.example.tanmen.Controller.Fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.android.example.tanmen.API.ShopService
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.databinding.FragmentShuffleBinding
import kotlinx.coroutines.launch
import java.lang.ClassCastException
import kotlin.random.Random

class ShuffleFragment : Fragment() {
    private lateinit var listener: CallbackListener

    interface CallbackListener {
        fun openBottomSheet()
    }
    private lateinit var binding: FragmentShuffleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShuffleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            if (ShopService.instance.location != null) {
                val data = ShopService.instance.searchTask(ShopService.UrlCreate.Distance.fiveHundred)
                if (data.isNotEmpty()) {
                    val index = Random.nextInt(data.size)
                    val randomData = data[index]
                    changeContent(randomData)
                    Log.d("ShuffleFragment", "${ShopService.instance.location}")
                } else {
                    AlertDialog.Builder(requireActivity())
                        .setMessage("該当する店舗が見つかりませんでした。\n検索条件を変更してください。")
                        .setPositiveButton("はい", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                val mainFragment = this@ShuffleFragment.parentFragment
                                if (mainFragment != null) {
                                    listener.openBottomSheet()
                                }

                            }
                        })
                        .show()
                    Log.d("ShuffleFragment", "店のdataが見つかりませんでした。")
                }
            } else {
                Log.d("ShuffleFragmentLocation", "locationがnullです。")
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val mainFragment: MainFragment = parentFragment as MainFragment
            listener = mainFragment
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() + "must implement NoticeDialogListener"))
        }
    }

    private fun changeContent(shopData: Shop) {
        shopData.image.into(binding.shopPhoto)
        binding.shopName.text = shopData.name
        binding.shopAddress.text = shopData.address
    }
}