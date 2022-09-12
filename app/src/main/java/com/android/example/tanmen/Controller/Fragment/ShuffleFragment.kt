package com.android.example.tanmen.Controller.Fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.android.example.tanmen.API.ShopService
import com.android.example.tanmen.Model.Shop
import com.android.example.tanmen.R
import com.android.example.tanmen.databinding.FragmentShuffleBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import kotlin.random.Random

class ShuffleFragment : Fragment() {
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

        val progressDialog = ProgressDialog(activity)
        lifecycleScope.launch {
            progressDialog.apply {
                setTitle("検索中です")
                setProgressStyle(ProgressDialog.STYLE_SPINNER)
                show()
            }
            if (ShopService.instance.location != null) {
                var data: MutableList<Shop>
                ShopService.instance.fetchUrl(ShopService.UrlCreate.Distance.fiveHundred) {
                    data = it
                    if (data.isNotEmpty()) {
                        binding.nameLabel.visibility = View.VISIBLE
                        binding.addressLabel.visibility = View.VISIBLE
                        progressDialog.dismiss()
                        val index = Random.nextInt(data.size)
                        val randomData = data[index]
                        changeContent(randomData)
                        Log.d("ShuffleFragment", "${ShopService.instance.location}")
                    } else {
                        binding.nameLabel.visibility = View.GONE
                        binding.addressLabel.visibility = View.GONE
                        progressDialog.dismiss()
                        AlertDialog.Builder(requireActivity())
                            .setMessage("該当する店舗が見つかりませんでした。\n検索条件を変更してください。")
                            .setPositiveButton("はい", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    val mainFragment = this@ShuffleFragment.parentFragment as MainFragment
                                    mainFragment.openBottomSheet()
                                    val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager) as ViewPager2
                                    val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
                                    viewPager.currentItem -= 1
                                }
                            })
                            .show()

                        Log.d("ShuffleFragment", "店のdataが見つかりませんでした。")
                    }
                }
            } else {
                Log.d("ShuffleFragmentLocation", "locationがnullです。")
            }
        }
    }

    private fun changeContent(shopData: Shop) {
        shopData.image.into(binding.shopPhoto)
        binding.shopName.text = shopData.name
        binding.shopAddress.text = shopData.address
    }
}