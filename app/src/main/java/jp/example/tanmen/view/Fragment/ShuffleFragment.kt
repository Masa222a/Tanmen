package jp.example.tanmen.view.Fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.Model.Entity.Shop
import jp.example.tanmen.R
import jp.example.tanmen.viewModel.ShuffleViewModel
import jp.example.tanmen.databinding.FragmentShuffleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShuffleFragment : Fragment() {
    private lateinit var binding: FragmentShuffleBinding
    private val viewModel: ShuffleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShuffleBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.shuffleViewModel = viewModel

        viewModel.shopPhoto.observe(viewLifecycleOwner, Observer {
            viewModel.setImageUrl(binding.shopPhoto, it)
        })

        viewModel.shopName.observe(viewLifecycleOwner, Observer {
            binding.shopName.text = it
        })

        viewModel.shopAddress.observe(viewLifecycleOwner, Observer {
            binding.shopAddress.text = it
        })

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
                var data = mutableListOf<Shop>()
                data = viewModel.getData()
                if (data.isNullOrEmpty()) {
                    GlobalScope.launch(Dispatchers.Main) {
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
                } else {
                    progressDialog.dismiss()
                    viewModel.changeContent(data)
                    Log.d("ShuffleFragment", "${ShopService.instance.location}")
                }
            } else {
                Log.d("ShuffleFragmentLocation", "locationがnullです。")
            }
        }
    }
}