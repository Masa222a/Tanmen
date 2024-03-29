package jp.re.tanmen.view.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jp.re.tanmen.R
import jp.re.tanmen.adapter.ShopListAdapter
import jp.re.tanmen.databinding.FragmentHomeBinding
import jp.re.tanmen.model.Entity.Shop
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var adapter: ShopListAdapter? = null
    private val shopList = mutableListOf<Shop>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val recyclerView = binding.shopList
        val layoutManager = LinearLayoutManager(activity)
        adapter = ShopListAdapter(shopList)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        adapter?.setOnShopCellClickListener(
            object : ShopListAdapter.OnShopCellClickListener {
                override fun onItemClick(shop: Shop) {
                    val fragment = DetailFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("shopDetail", shop)
                    fragment.arguments = bundle
                    Timber.d("$bundle")
                    parentFragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        )

        return binding.root
    }

    @DelicateCoroutinesApi
    @SuppressLint("NotifyDataSetChanged")
    fun changeShopList(shopLists: MutableList<Shop>) {
        GlobalScope.launch(Dispatchers.Main) {
            binding.apply {
                homeMessage.visibility = View.GONE
                homeImage.visibility = View.GONE
            }
            adapter?.shopList = shopLists
            adapter?.notifyDataSetChanged()
        }
    }

}
