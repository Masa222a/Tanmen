package jp.example.tanmen.View.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import jp.example.tanmen.Model.Entity.Shop
import jp.example.tanmen.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    @SuppressLint("FragmentBackPressedCallback")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val args = arguments?.getSerializable("shopDetail") as Shop
        if (args != null) {
            Picasso.get().load(args.image).resize(300, 300).into(binding.shopPhoto)
            binding.shopName.text = args.name
            binding.shopAddress.text = args.address
            binding.shopBusinessHours.text = args.hours
        }
        Log.d("detailshop", "$args")

        requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@DetailFragment)
                    .commit()
            }
        })

        return binding.root
    }

}