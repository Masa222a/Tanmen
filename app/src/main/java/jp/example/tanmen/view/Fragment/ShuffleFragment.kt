package jp.example.tanmen.view.Fragment

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
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
import timber.log.Timber.d

class ShuffleFragment : Fragment() {
    private lateinit var binding: FragmentShuffleBinding
    private val viewModel: ShuffleViewModel by viewModels()
    var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShuffleBinding.inflate(inflater, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            shuffleViewModel = viewModel
        }
        this.progressDialog = ProgressDialog(activity)

        progressDialog?.apply {
            setTitle(getString(R.string.loading))
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            show()
        }

        checkedLocation()
        setupObserve()

        return binding.root
    }
    
    override fun onResume() {
        super.onResume()

        if (ShopService.instance.location != null) {
            viewModel.getData()
        } else {
            viewModel.getLocation()
        }
    }

    private fun checkedLocation() {
        viewModel.location.observe(viewLifecycleOwner) {
            if (it == null) {
                progressDialog?.dismiss()
                val dialog = AlertDialog.Builder(requireActivity())
                    dialog.apply {
                        setMessage(getString(R.string.location_false))
                        setPositiveButton(getString(R.string.yes)) {_, _ ->
                            val mainFragment = this@ShuffleFragment.parentFragment as MainFragment
                            val viewPager = activity?.findViewById(R.id.viewPager) as ViewPager2
                            viewPager.currentItem = 0
                            mainFragment.openBottomSheet()
                        }
                        show()
                    }
            } else {
                progressDialog?.dismiss()
                viewModel.getData()
            }
        }
    }

    private fun setupObserve() {
        viewModel.data.observe(viewLifecycleOwner) {
            if (it == null) {
                d("オブザーブ内null")
                GlobalScope.launch(Dispatchers.Main) {
                    progressDialog?.dismiss()
                    binding.apply {
                        shopPhoto.visibility = View.GONE
                        nameLabel.visibility = View.GONE
                        shopName.visibility = View.GONE
                        addressLabel.visibility = View.GONE
                        shopAddress.visibility = View.GONE
                    }
                    AlertDialog.Builder(requireActivity())
                        .setMessage(getString(R.string.no_search_result))
                        .setPositiveButton(getString(R.string.yes)) { _, _ ->
                            val mainFragment = this@ShuffleFragment.parentFragment as MainFragment
                            val viewPager = activity?.findViewById(R.id.viewPager) as ViewPager2
                            viewPager.currentItem = 0
                            mainFragment.openBottomSheet()
                        }
                        .show()
                    d("店のdataが見つかりませんでした")
                }
            } else {
                d("data observe")
                GlobalScope.launch(Dispatchers.Main) {
                    binding.apply {
                        shopPhoto.visibility = View.VISIBLE
                        nameLabel.visibility = View.VISIBLE
                        shopName.visibility = View.VISIBLE
                        addressLabel.visibility = View.VISIBLE
                        shopAddress.visibility = View.VISIBLE
                        Picasso.get().load(it.image).resize(72, 72).into(shopPhoto)
                        shopName.text = it.name
                        shopAddress.text = it.address
                    }
                }
                progressDialog?.dismiss()
            }
        }
    }
}
