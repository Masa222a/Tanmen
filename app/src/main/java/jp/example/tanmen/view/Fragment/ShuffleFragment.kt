package jp.example.tanmen.view.Fragment

import android.annotation.SuppressLint
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
import androidx.viewpager2.widget.ViewPager2
import com.squareup.picasso.Picasso
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.R
import jp.example.tanmen.databinding.FragmentShuffleBinding
import jp.example.tanmen.viewModel.ShuffleViewModel
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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.shuffleViewModel = viewModel

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onResume() {
        super.onResume()

        val progressDialog = ProgressDialog(activity)
            progressDialog.apply {
                setTitle("検索中です")
                setProgressStyle(ProgressDialog.STYLE_SPINNER)
                show()
            }
            if (ShopService.instance.location != null) {
                viewModel.getData()
                    Log.d("確認フラグメント", "データ取得後")
                viewModel.data.observe(viewLifecycleOwner) {
                    Log.d("確認フラグメント", "オブザーブ内")
                    if (it == null) {
                        Log.d("確認フラグメント", "オブザーブ内null")
                        GlobalScope.launch(Dispatchers.Main) {
                            progressDialog.dismiss()
                            binding.nameLabel.visibility = View.GONE
                            binding.addressLabel.visibility = View.GONE
                            AlertDialog.Builder(requireActivity())
                                .setMessage("該当する店舗が見つかりませんでした。\n検索条件を変更してください。")
                                .setPositiveButton("はい", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, which: Int) {
                                        val mainFragment = this@ShuffleFragment.parentFragment as MainFragment
                                        val viewPager = activity?.findViewById(R.id.viewPager) as ViewPager2
                                        viewPager.currentItem = 0
                                        mainFragment.openBottomSheet()
                                    }
                                })
                                .show()

                            Log.d("ShuffleFragment", "店のdataが見つかりませんでした。")
                        }
                    } else {
                        GlobalScope.launch(Dispatchers.Main) {
                            binding.nameLabel.visibility = View.VISIBLE
                            binding.addressLabel.visibility = View.VISIBLE
                            progressDialog.dismiss()
                            Picasso.get().load(it.image).resize(72, 72).into(binding.shopPhoto)
                            binding.shopName.text = it.name
                            binding.shopAddress.text = it.address
                        }

                        Log.d("ShuffleFragment", "${ShopService.instance.location}")
                    }
                }
            } else {
                progressDialog.dismiss()
                Log.d("ShuffleFragmentLocation", "locationがnullです。")
            }
    }
}