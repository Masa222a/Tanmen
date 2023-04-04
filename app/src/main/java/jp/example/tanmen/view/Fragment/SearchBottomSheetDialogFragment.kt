package jp.example.tanmen.view.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.example.tanmen.Model.API.ShopService
import jp.example.tanmen.R
import jp.example.tanmen.databinding.FragmentSearchBottomSheetDialogBinding
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentSearchBottomSheetDialogBinding

    private val _requestKey: String
        get() = requireArguments().getString(KEY_REQUEST, "")

    companion object {
        fun newInstance(
            requestKey: String
        ) = SearchBottomSheetDialogFragment().apply {
            arguments = bundleOf(KEY_REQUEST to requestKey)
        }

        const val KEY_CLICK = "shopData"
        private const val KEY_REQUEST = "key_request"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBottomSheetDialogBinding.inflate(inflater, container, false)
        binding.apply {
            searchButton.setOnClickListener {
                lifecycleScope.launch {
                    if (ShopService.instance.location != null) {
                        val btnId = binding.toggleButton.checkedButtonId
                        if (btnId != -1) {
                            val distance = getCheckedButton(btnId)
                            ShopService.instance.fetchUrl(distance) {
                                Timber.d("$it")

                                val bundle = bundleOf(KEY_CLICK to it)
                                setFragmentResult(_requestKey, bundle)

                            }
                        }else {
                            Toast.makeText(activity, getString(R.string.please_select_distance), Toast.LENGTH_SHORT).show()
                            Timber.d("距離が選択されていません")
                        }
                    } else {
                        Timber.d("locationがnullです")
                    }
                }
            }

            cancelButton.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }

    private fun getCheckedButton(btnId: Int): ShopService.UrlCreate.Distance? {
        when (btnId) {
            R.id.five_hundred_button -> {
                //500m
                return ShopService.UrlCreate.Distance.fiveHundred
            }
            R.id.one_thousand_button -> {
                //1000m
                return ShopService.UrlCreate.Distance.oneThousand

            }
            R.id.two_thousand_button -> {
                //2000m
                return ShopService.UrlCreate.Distance.twoThousand
            }
        }
        return null
    }
}
